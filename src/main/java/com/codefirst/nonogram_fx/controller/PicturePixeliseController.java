package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.util.PixelArtUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class PicturePixeliseController {

    @FXML protected ImageView imageView;

    @FXML protected TextField widthInput;
    @FXML protected TextField heightInput;

    private int selectedWidth, selectedHeight;

    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");

    @FXML
    protected void selectPictureButtonClick() {

        try {
            selectedWidth = Integer.parseInt(widthInput.getText());
            selectedHeight = Integer.parseInt(heightInput.getText());
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong input");
            alert.setHeaderText("Please enter decimal width and height");

            alert.showAndWait();
            return;
        }
        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {

            Image orgImage = new Image(file.toURI().toString(), selectedWidth, selectedHeight, false, false);
            Image image = PixelArtUtil.pixelArtImage(orgImage);

            imageView.setImage(image);
        }

    }

}
