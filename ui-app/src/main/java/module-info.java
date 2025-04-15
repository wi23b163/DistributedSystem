module at.technikum.uiapp {
    requires java.net.http;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens at.technikum.uiapp to javafx.fxml;
    exports at.technikum.uiapp;
}
