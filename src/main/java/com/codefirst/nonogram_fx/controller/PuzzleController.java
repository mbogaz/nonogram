package com.codefirst.nonogram_fx.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import static com.codefirst.nonogram_fx.controller.HelloController.createdPixeliseContent;

public class PuzzleController {
    @FXML protected GridPane rootPane;

    public void paintPuzzle() {
        rootPane.getChildren().clear();
        GridPane boardPane = new GridPane();
        boardPane.setStyle("--fxbackground-color: blue");
        for (int i = 0; i < createdPixeliseContent.length; i++) {
            for (int j = 0; j < createdPixeliseContent[i].length; j++) {
                Rectangle colorCell = new Rectangle();
                colorCell.setFill(createdPixeliseContent[i][j]);
                colorCell.widthProperty().bind(rootPane.widthProperty().multiply(0.8).divide(createdPixeliseContent.length));
                colorCell.heightProperty().bind(rootPane.heightProperty().multiply(0.8).divide(createdPixeliseContent[i].length));
                boardPane.add(colorCell, i, j);
            }
        }
        rootPane.add(boardPane, 1, 1);
    }
}
