package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    Stage newStage = new Stage();

    @FXML
    protected void onHelloButtonClick() throws IOException {
        FXMLLoader picturePixeliseLoader = new FXMLLoader(HelloApplication.class.getResource("picture-pixelise.fxml"));
        Scene scene = new Scene(picturePixeliseLoader.load(), 1280, 720);
        newStage.setScene(scene);
        newStage.show();
    }
}