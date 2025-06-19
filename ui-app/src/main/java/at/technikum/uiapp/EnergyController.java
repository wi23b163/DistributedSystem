package at.technikum.uiapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.time.LocalDate;
import java.util.List;

public class EnergyController {

    @FXML
    private Label communityUsedLabel, gridPortionLabel;

    @FXML
    private DatePicker startDate, endDate;

    @FXML
    private Label producedLabel, usedLabel, gridUsedLabel;

    private final ApiController apiController = new ApiController();

    @FXML
    protected void onRefreshClick() {
        try {
            EnergyCurrent current = apiController.getCurrentEnergyData();
            communityUsedLabel.setText(String.format("%.2f %%", current.getCommunityPool()));
            gridPortionLabel.setText(String.format("%.2f %%", current.getGridPortion()));
        } catch (Exception e) {
            communityUsedLabel.setText("Fehler");
            gridPortionLabel.setText("Fehler");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onShowDataClick() {
        try {
            LocalDate startVal = startDate.getValue();
            LocalDate endVal = endDate.getValue();

            if (startVal == null || endVal == null || startVal.isAfter(endVal)) {
                producedLabel.setText("Ungültiges Datum");
                usedLabel.setText("");
                gridUsedLabel.setText("");
                return;
            }

            // Liste von Daten laden
            List<EnergyHistorical> dataList = apiController.getHistoricalEnergyData(startVal, endVal);

            if (dataList == null || dataList.isEmpty()) {
                producedLabel.setText("Keine Daten gefunden");
                usedLabel.setText("");
                gridUsedLabel.setText("");
                return;
            }

            // Summieren
            double totalProduced = 0;
            double totalUsed = 0;
            double totalGridUsed = 0;

            for (EnergyHistorical data : dataList) {
                totalProduced += data.getProduced();
                totalUsed += data.getUsed();
                totalGridUsed += data.getGridUsed();
            }

            // Labels anzeigen
            producedLabel.setText(String.format("Community produced %.2f kWh", totalProduced));
            usedLabel.setText(String.format("Community used %.2f kWh", totalUsed));
            gridUsedLabel.setText(String.format("Grid used %.2f kWh", totalGridUsed));

        } catch (Exception e) {
            producedLabel.setText("Fehler");
            usedLabel.setText("Fehler");
            gridUsedLabel.setText("Fehler");
            e.printStackTrace();
        }
    }



}

