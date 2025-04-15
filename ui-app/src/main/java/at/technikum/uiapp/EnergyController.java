package at.technikum.uiapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EnergyController {

    @FXML
    private Label communityUsedLabel, gridPortionLabel, producedLabel, usedLabel, gridUsedLabel;

    @FXML
    private DatePicker startDate, endDate;

    @FXML
    private Button refreshButton, showDataButton;

    @FXML
    protected void onRefreshClick() {
        System.out.println("Refreshing data...");
        communityUsedLabel.setText("78.54%");
        gridPortionLabel.setText("7.23%");
    }

    @FXML
    protected void onShowDataClick() {
        String start = startDate.getValue() != null ? startDate.getValue().toString() : "Start not set";
        String end = endDate.getValue() != null ? endDate.getValue().toString() : "End not set";


        //hardcoded
        System.out.println("Showing data from " + start + " to " + end);
        producedLabel.setText("143.024 kWh");
        usedLabel.setText("130.101 kWh");
        gridUsedLabel.setText("14.75 kWh");
    }
}

