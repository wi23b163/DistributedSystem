package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class PercentageService {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/energy";
    private static final String DB_USER = "disysuser";
    private static final String DB_PASS = "disyspw";

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                calculateAndStorePercentage();
            }
        }, 0, 10000);
    }

    private static void calculateAndStorePercentage() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT * FROM usage_hourly ORDER BY hour DESC LIMIT 1";

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    double produced = rs.getDouble("community_produced");
                    double used = rs.getDouble("community_used");
                    double gridUsed = rs.getDouble("grid_used");
                    Timestamp hour = rs.getTimestamp("hour");

                    double depleted = 0.0;
                    if (produced <= 0) {
                        depleted = 0.0;
                    } else {
                        depleted = Math.min((used / produced) * 100.0, 100.0);
                    }


                    double gridPortion = (used == 0) ? 0 : (gridUsed / used) * 100.0;

                    String insert = """
                        INSERT INTO current_percentage (hour, community_depleted, grid_portion)
                        VALUES (?, ?, ?)
                        ON CONFLICT (hour)
                        DO UPDATE SET
                            community_depleted = EXCLUDED.community_depleted,
                            grid_portion = EXCLUDED.grid_portion
                        """;

                    try (PreparedStatement ps = conn.prepareStatement(insert)) {
                        ps.setTimestamp(1, hour);
                        ps.setDouble(2, depleted);
                        ps.setDouble(3, gridPortion);
                        ps.executeUpdate();
                        System.out.println("Updated current_percentage for hour: " + hour);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

