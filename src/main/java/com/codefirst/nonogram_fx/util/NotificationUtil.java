package com.codefirst.nonogram_fx.util;

import javafx.scene.control.Alert;

public class NotificationUtil {

    public static void errorAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(text);

        alert.showAndWait();
    }
}
