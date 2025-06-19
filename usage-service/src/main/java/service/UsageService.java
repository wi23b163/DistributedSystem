package service;

import com.google.gson.Gson;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;

public class UsageService {

    private static final String QUEUE_NAME = "energy_queue";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/energy";
    private static final String DB_USER = "disysuser";
    private static final String DB_PASSWORD = "disyspw";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                com.rabbitmq.client.Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                Connection db = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        ) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages in '" + QUEUE_NAME + "'...");

            Gson gson = new Gson();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String messageJson = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received: " + messageJson);

                try {
                    Message msg = gson.fromJson(messageJson, Message.class);
                    processMessage(db, msg);
                } catch (Exception e) {
                    System.err.println(" [!] Error processing message: " + e.getMessage());
                    e.printStackTrace();
                }
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
            new CountDownLatch(1).await(); // block forever
        }
    }

    static class Message {
        String type;
        String association;
        double kWh;
        String datetime;
    }

    private static void processMessage(Connection db, Message msg) throws SQLException {
        boolean isProducer = "PRODUCER".equalsIgnoreCase(msg.type);
        double produced = isProducer ? msg.kWh : 0.0;
        double used = isProducer ? 0.0 : msg.kWh;

        LocalDateTime timestamp = LocalDateTime.parse(msg.datetime);
        LocalDateTime hour = timestamp.truncatedTo(ChronoUnit.HOURS);

        String selectSql = "SELECT community_produced, community_used, grid_used FROM usage_hourly WHERE hour = ?";
        double totalProduced = 0.0;
        double totalUsed = 0.0;
        double totalGridUsed = 0.0;

        try (PreparedStatement selectStmt = db.prepareStatement(selectSql)) {
            selectStmt.setTimestamp(1, Timestamp.valueOf(hour));
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    totalProduced = rs.getDouble("community_produced");
                    totalUsed = rs.getDouble("community_used");
                    totalGridUsed = rs.getDouble("grid_used");
                }
            }
        }

        double newProduced = totalProduced + produced;
        double newUsed = totalUsed;
        double newGridUsed = totalGridUsed;

        if (isProducer) {

        } else {
            double availableCommunity = totalProduced - totalUsed;
            double usedFromCommunity = Math.min(used, Math.max(0.0, availableCommunity));
            double usedFromGrid = used - usedFromCommunity;

            newUsed += usedFromCommunity;
            newGridUsed += usedFromGrid;
        }

        String upsert = """
            
                INSERT INTO usage_hourly (hour, community_produced, community_used, grid_used)
            VALUES (?, ?, ?, ?)
            ON CONFLICT (hour) DO UPDATE SET
                community_produced = EXCLUDED.community_produced,
                community_used = EXCLUDED.community_used,
                grid_used = EXCLUDED.grid_used;
            """;

        try (PreparedStatement stmt = db.prepareStatement(upsert)) {
            stmt.setTimestamp(1, Timestamp.valueOf(hour));
            stmt.setDouble(2, newProduced);
            stmt.setDouble(3, newUsed);
            stmt.setDouble(4, newGridUsed);
            stmt.executeUpdate();

            System.out.printf("Updated %s: produced=%.3f, used=%.3f, grid=%.3f%n",
                    hour, newProduced, newUsed, newGridUsed);
        }
    }
}
