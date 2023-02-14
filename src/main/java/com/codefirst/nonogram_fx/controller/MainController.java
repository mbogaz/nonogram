package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.codefirst.nonogram_fx.util.Constants.*;
import static com.codefirst.nonogram_fx.util.NotificationUtil.errorAlert;

public class MainController {

    Stage newStage = new Stage();

    @FXML
    protected void onHelloButtonClick() throws IOException {
        FXMLLoader picturePixeliseLoader = new FXMLLoader(HelloApplication.class.getResource("picture-pixelise.fxml"));
        Scene scene = new Scene(picturePixeliseLoader.load(), 1280, 720);
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    protected void onStartPuzzleClick() throws IOException {
        if (createdPixeliseContent == null) {
            errorAlert("Not Ready", "Please pixelise a picture first");
            return;
        }
        FXMLLoader picturePixeliseLoader = new FXMLLoader(HelloApplication.class.getResource("puzzle-view.fxml"));
        Scene scene = new Scene(picturePixeliseLoader.load(), (SELECTED_WIDTH * 30) + 5, (SELECTED_HEIGHT * 30) + 5);
        ((PuzzleController) picturePixeliseLoader.getController()).init();
        newStage.setScene(scene);
        newStage.show();
    }
}