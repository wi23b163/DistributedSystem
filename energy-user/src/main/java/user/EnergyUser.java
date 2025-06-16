package user;

import com.rabbitmq.client.*;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.Random;

public class EnergyUser {
    private static final String QUEUE_NAME = "energy_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            Gson gson = new Gson();
            Random random = new Random();

            while (true) {
                int hour = LocalDateTime.now().getHour();
                double kWh;

                if ((hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 21)) {
                    kWh = 0.005 + random.nextDouble() * 0.008; // Spitzenzeit
                } else {
                    kWh = 0.001 + random.nextDouble() * 0.004; // Normalzeit
                }

                EnergyMessage message = new EnergyMessage(
                        "USER", "COMMUNITY1", kWh, LocalDateTime.now().toString()
                );

                String json = gson.toJson(message);
                channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
                System.out.println("Sent: " + json);
                Thread.sleep(5000);
            }

        }
    }

    static class EnergyMessage {
        String type, association, datetime;
        double kWh;

        public EnergyMessage(String type, String association, double kWh, String datetime) {
            this.type = type;
            this.association = association;
            this.kWh = kWh;
            this.datetime = datetime;
        }
    }
}
