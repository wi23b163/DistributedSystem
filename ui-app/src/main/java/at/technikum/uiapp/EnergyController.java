package at.technikum.uiapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            if (startDate.getValue() == null || endDate.getValue() == null) {
                producedLabel.setText("Start/End fehlt");
                return;
            }

            String start = startDate.getValue().atStartOfDay().toString();
            String end = endDate.getValue().atTime(23, 59).toString();

            EnergyHistorical data = apiController.getHistoricalEnergyData(start, end);

            producedLabel.setText(String.format("Community produced %.2f kWh", data.getProduced()));
            usedLabel.setText(String.format("Community used %.2f kWh", data.getUsed()));
            gridUsedLabel.setText(String.format("Grid used %.2f kWh", data.getGridUsed()));
        } catch (Exception e) {
            producedLabel.setText("Fehler");
            usedLabel.setText("Fehler");
            gridUsedLabel.setText("Fehler");
            e.printStackTrace();
        }
    }
}

