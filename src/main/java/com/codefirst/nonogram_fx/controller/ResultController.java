package com.codefirst.nonogram_fx.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import static com.codefirst.nonogram_fx.util.Constants.ORG_IMAGE;

public class ResultController {
    @FXML protected ImageView resultImageView;

    public void init() {
        if (ORG_IMAGE.getWidth() < 300) {
            resultImageView.fitWidthProperty().bind(ORG_IMAGE.widthProperty().multiply(2));
            resultImageView.fitHeightProperty().bind(ORG_IMAGE.heightProperty().multiply(2));
        }
        resultImageView.setImage(ORG_IMAGE);
    }
}
