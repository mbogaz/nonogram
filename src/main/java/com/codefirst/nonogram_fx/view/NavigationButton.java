package com.codefirst.nonogram_fx.view;

import com.codefirst.nonogram_fx.dto.NavigationCell;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.codefirst.nonogram_fx.util.Constants.*;

public class NavigationButton extends StackPane {
    Rectangle navigationCell;

    Text text;
    boolean isVisible = true;

    public NavigationButton(NavigationCell cell, GridPane rootPane, int maxRowNavs, Rectangle selectedColorView, Label infoLabel, boolean isColumn) {
        super();

        this.navigationCell = new Rectangle();
        navigationCell.setFill(cell.getColor());
        if(isColumn) {
            navigationCell.widthProperty().bind(rootPane.widthProperty().multiply(80).divide(100).divide(SELECTED_WIDTH).subtract(0.6));
            navigationCell.heightProperty().bind(rootPane.heightProperty().multiply(20).divide(100).divide(maxRowNavs));
        } else {
            navigationCell.widthProperty().bind(rootPane.widthProperty().multiply(20).divide(100).divide(maxRowNavs));
            navigationCell.heightProperty().bind(rootPane.heightProperty().multiply(80).divide(100).divide(SELECTED_HEIGHT).subtract(0.6));
        }

        this.text = new Text(String.valueOf(cell.getCount()));
        text.setFont(NAVIGATION_FONT);
        if (cell.getColor().equals(Color.BLACK)) {
            text.setFill(Color.WHITE);
        } else {
            text.setFill(Color.BLACK);
        }

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                if(this.isVisible) {
                    this.text.setVisible(false);
                    this.isVisible = false;
                } else {
                    this.text.setVisible(true);
                    this.isVisible = true;
                }
            } else {
                navigationCellClickAction(cell.getColor(), selectedColorView, infoLabel);
            }
        });
        getChildren().addAll(navigationCell, text);
    }

    private void navigationCellClickAction(Color color, Rectangle selectedColorView, Label infoLabel) {
        com.codefirst.nonogram_fx.controller.PuzzleController.SELECTED_COLOR = color;
        selectedColorView.setFill(color);
        if (color.equals(Color.BLACK)) {
            infoLabel.setTextFill(Color.WHITE);
        } else {
            infoLabel.setTextFill(Color.BLACK);
        }
    }
}
