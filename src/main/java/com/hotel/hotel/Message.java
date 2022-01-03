package com.hotel.hotel;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class Message {
    public static void errorMessage(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error Dialog");
        alert.setContentText("Please Check Your Input Values " + error);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText("Something Wrong");
        dialogPane.setStyle("-fx-background-color:  #F20505;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; "
                + "-fx-font-weight: bold;"+"-fx-text-fill: #ffffff");

        alert.show();
    }


    public static void infoMessage(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Booking");
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText("Your Room Is Booked Successfully");
        dialogPane.setStyle("-fx-background-color: #ffffff;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; "
                + "-fx-font-weight: bold;"+"-fx-text-fill: #000000");

        alert.show();
    }
    public static void logoutMessage(){
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Are You Want Exit ?",
                ButtonType.NO,
                ButtonType.YES);
        alert.setTitle("Close The Application");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setHeaderText("Logout The System");
        dialogPane.setStyle("-fx-background-color:  #F20505;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; "
                + "-fx-font-weight: bold;"+"-fx-text-fill: #ffffff");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        }
    }
}
