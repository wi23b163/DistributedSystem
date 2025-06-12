package at.technikum.uiapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.technikum.uiapp.EnergyHistorical;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

public class ApiController {

    private static final String BASE_URL = "http://localhost:8080/energy";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public ApiController() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    public EnergyCurrent getCurrentEnergyData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/current"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), EnergyCurrent.class);
    }

    public List<EnergyHistorical> getHistoricalEnergyData(LocalDate start, LocalDate end)
            throws IOException, InterruptedException {

        String url = BASE_URL + "/historical?start=" + start.atStartOfDay() + "&end=" + end.atTime(23, 59, 59);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("RESPONSE: " + response.body());


        if (response.statusCode() != 200) {
            throw new RuntimeException("API Fehler: " + response.statusCode() + "\n" + response.body());
        }

        return objectMapper.readValue(response.body(), new TypeReference<List<EnergyHistorical>>() {});

    }

}
