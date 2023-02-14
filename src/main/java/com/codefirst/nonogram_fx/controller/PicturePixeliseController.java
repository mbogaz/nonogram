package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.util.PixelArtUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

import static com.codefirst.nonogram_fx.util.NotificationUtil.errorAlert;
import static com.codefirst.nonogram_fx.util.Constants.SELECTED_HEIGHT;
import static com.codefirst.nonogram_fx.util.Constants.SELECTED_WIDTH;

public class PicturePixeliseController {

    @FXML protected ImageView imageView;

    @FXML protected TextField widthInput;
    @FXML protected TextField heightInput;

    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");

    @FXML
    protected void selectPictureButtonClick() {

        try {
            SELECTED_WIDTH = Integer.parseInt(widthInput.getText());
            SELECTED_HEIGHT = Integer.parseInt(heightInput.getText());
        } catch (NumberFormatException exception) {
            errorAlert("Wrong input", "Please enter decimal width and height");
            return;
        }
        FileChooser fileChooser = new FileChooser();


        fileChooser.getExtensionFilters().addAll(extFilterPNG, extFilterJPG);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {

            Image orgImage = new Image(file.toURI().toString(), SELECTED_WIDTH, SELECTED_HEIGHT, false, false);
            Image image = PixelArtUtil.pixelArtImage(orgImage);

            imageView.setImage(image);
        }

    }

}
