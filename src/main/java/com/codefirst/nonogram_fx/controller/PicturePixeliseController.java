package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.util.PixelArtUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

import static com.codefirst.nonogram_fx.util.Constants.*;
import static com.codefirst.nonogram_fx.util.NotificationUtil.errorAlert;

public class PicturePixeliseController {

    @FXML protected ImageView imageView;

    @FXML protected TextField widthInput;
    @FXML protected TextField heightInput;

    FileChooser.ExtensionFilter fileExtensionFilter = new FileChooser.ExtensionFilter("image files", "*");

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


        fileChooser.getExtensionFilters().addAll(fileExtensionFilter);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {

            ORG_IMAGE = new Image(file.toURI().toString());
            Image orgImageScaled = PixelArtUtil.scale(ORG_IMAGE, SELECTED_WIDTH, SELECTED_HEIGHT, false);
            Image image = PixelArtUtil.pixelArtAndSaveImage(orgImageScaled);

            imageView.setImage(image);
        }

    }

}
