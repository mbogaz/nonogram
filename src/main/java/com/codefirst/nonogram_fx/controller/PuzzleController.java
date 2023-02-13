package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.dto.NavigationCell;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static com.codefirst.nonogram_fx.controller.HelloController.createdPixeliseContent;

public class PuzzleController {
    @FXML protected GridPane rootPane;
    @FXML protected Label infoLabel;
    @FXML protected Rectangle selectedColorView;

    Color selectedColor = Color.BLACK;
    VBox navigationList = new VBox();

    int remainingCells;

    public void paintPuzzle() {
        selectedColorView.widthProperty().bind(rootPane.widthProperty().multiply(20).divide(100));
        selectedColorView.heightProperty().bind(rootPane.heightProperty().multiply(20).divide(100));
        remainingCells = createdPixeliseContent.length * createdPixeliseContent[0].length;
        infoLabel.setText(remainingCells + " left");

        GridPane boardPane = new GridPane();
        boardPane.setGridLinesVisible(true);
        for (int i = 0; i < createdPixeliseContent.length; i++) {
            for (int j = 0; j < createdPixeliseContent[i].length; j++) {
                Rectangle colorCell = new Rectangle();
                colorCell.setFill(Color.TRANSPARENT);
                colorCell.widthProperty().bind(rootPane.widthProperty().multiply(0.8).divide(createdPixeliseContent.length).subtract(0.6));
                colorCell.heightProperty().bind(rootPane.heightProperty().multiply(0.8).divide(createdPixeliseContent[i].length).subtract(0.5));

                int finalI = i;
                int finalJ = j;
                colorCell.setOnMouseClicked(event -> puzzleCellClickAction(colorCell, finalI, finalJ));
                boardPane.add(colorCell, i, j);
            }
        }
        rootPane.add(boardPane, 1, 1);
        paintNavigations();
    }

    private void paintNavigations() {
        //System.out.println(createdPixeliseContent.length); //width
        //System.out.println(createdPixeliseContent[0].length); //height

        infoLabel.setStyle("--fxbackground-color: " + selectedColor);

        for (int i = 0; i < createdPixeliseContent[0].length; i++) {
            List<NavigationCell> navigationRow = new ArrayList<>();
            int sameColorCount = 0;
            Color prevColor = null;
            for (int j = 0; j < createdPixeliseContent.length; j++) {
                Color cellColor = createdPixeliseContent[j][i];
                if(cellColor.equals(prevColor) || prevColor == null) {
                    sameColorCount++;
                } else {
                    NavigationCell navigationCell = new NavigationCell();
                    navigationCell.setColor(prevColor);
                    navigationCell.setCount(sameColorCount);
                    navigationRow.add(navigationCell);
                    sameColorCount = 1;
                }
                prevColor = cellColor;
            }
            if(sameColorCount > 0) {
                NavigationCell navigationCell = new NavigationCell();
                navigationCell.setColor(prevColor);
                navigationCell.setCount(sameColorCount);
                navigationRow.add(navigationCell);
            }
            addNavigationRowToView(i, navigationRow);
        }

        rootPane.add(navigationList, 0, 1);
    }

    private void addNavigationRowToView(int rowIndex, List<NavigationCell> cells) {
        HBox navigationRow = new HBox();
        for (NavigationCell cell : cells) {
            if (cell.getColor().equals(Color.WHITE)) {
                continue;
            }

            Rectangle navigationCell = new Rectangle();
            navigationCell.setFill(cell.getColor());
            navigationCell.setWidth(25);
            navigationCell.heightProperty().bind(rootPane.heightProperty().multiply(0.8).divide(createdPixeliseContent[0].length).subtract(0.5));

            final Text text = new Text(String.valueOf(cell.getCount()));
            if (cell.getColor().equals(Color.BLACK)) {
                text.setFill(Color.WHITE);
            } else {
                text.setFill(Color.BLACK);
            }
            final StackPane stack = new StackPane();
            stack.setOnMouseClicked(event -> navigationCellClickAction(cell.getColor()));
            stack.getChildren().addAll(navigationCell, text);
            navigationRow.getChildren().add(stack);
            //navigationList.add(navigationCell, i, rowIndex);
        }
        navigationList.getChildren().add(navigationRow);
    }

    private void navigationCellClickAction(Color color) {
        System.out.println("hello world");
        selectedColor = color;
        selectedColorView.setFill(color);
        if(color.equals(Color.BLACK)) {
            infoLabel.setTextFill(Color.WHITE);
        } else {
            infoLabel.setTextFill(Color.BLACK);
        }
    }
    private void puzzleCellClickAction(Rectangle rectangle, int i, int j)  {
        if(!rectangle.getFill().equals(Color.TRANSPARENT)) {
            System.out.println("return");
            return;
        }
        if(createdPixeliseContent[i][j].equals(selectedColor)) {
            rectangle.setFill(selectedColor);
            remainingCells--;
            infoLabel.setText(remainingCells + " left");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Ready");
            alert.setHeaderText("Please pixelise a picture first");

            alert.showAndWait();
        }
    }
}
