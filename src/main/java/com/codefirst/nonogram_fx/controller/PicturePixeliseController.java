package com.codefirst.nonogram_fx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-0.99);

            Image image = new Image(file.toURI().toString(), selectedWidth, selectedHeight, false, false);
            readColors(image);

            imageView.setEffect(colorAdjust);
            imageView.setImage(image);
        }

    }

    private void readColors(Image  img) {
        final PixelReader pr = img.getPixelReader();
        final Map<Color, Long> colCount = new HashMap<>();

        for(int x = 0; x < img.getWidth(); x++) {
            for(int y = 0; y < img.getHeight(); y++) {
                final Color col = pr.getColor(x, y);
                if(colCount.containsKey(col)) {
                    colCount.put(col, colCount.get(col) + 1);
                } else {
                    colCount.put(col, 1L);
                }
            }
        }

        int total =  0;
        for (Color color : colCount.keySet()) {
            int colorCount = Math.toIntExact(colCount.get(color));
            total += colorCount;
            System.out.println(color + " : " + colorCount);
        }
        System.out.println("total pixels : " + total);

    }
}
