package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    Stage newStage = new Stage();

    public static Color[][] createdPixeliseContent;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        FXMLLoader picturePixeliseLoader = new FXMLLoader(HelloApplication.class.getResource("picture-pixelise.fxml"));
        Scene scene = new Scene(picturePixeliseLoader.load(), 1280, 720);
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    protected void onStartPuzzleClick() throws IOException {
        if(createdPixeliseContent == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Ready");
            alert.setHeaderText("Please pixelise a picture first");

            alert.showAndWait();
            return;
        }
        FXMLLoader picturePixeliseLoader = new FXMLLoader(HelloApplication.class.getResource("puzzle-view.fxml"));
        Scene scene = new Scene(picturePixeliseLoader.load(), 1280, 1280);
        ((PuzzleController)picturePixeliseLoader.getController()).paintPuzzle();
        newStage.setScene(scene);
        newStage.show();
    }
}