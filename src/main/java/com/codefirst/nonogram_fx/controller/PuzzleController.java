package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.dto.NavigationBlock;
import com.codefirst.nonogram_fx.dto.NavigationCell;
import com.codefirst.nonogram_fx.view.NavigationButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codefirst.nonogram_fx.util.Constants.*;
import static com.codefirst.nonogram_fx.util.NotificationUtil.errorAlert;

public class PuzzleController {
    @FXML
    protected GridPane rootPane;
    @FXML
    protected Label infoLabel;
    @FXML
    protected Rectangle selectedColorView;

    public static Color SELECTED_COLOR = Color.BLACK;
    VBox navigationRows = new VBox();
    HBox navigationColumns = new HBox();

    int remainingCells;

    public void init() {
        selectedColorView.widthProperty().bind(rootPane.widthProperty().multiply(20).divide(100));
        selectedColorView.heightProperty().bind(rootPane.heightProperty().multiply(20).divide(100));
        remainingCells = createdPixeliseContent.length * createdPixeliseContent[0].length;
        infoLabel.setText(remainingCells + " left");
        infoLabel.setStyle("--fxbackground-color: " + SELECTED_COLOR);

        paintPuzzle();
        paintNavigationRows();
        paintNavigationColumns();
    }

    public void paintPuzzle() {
        GridPane boardPane = new GridPane();
        boardPane.setGridLinesVisible(true);
        for (int i = 0; i < createdPixeliseContent.length; i++) {
            for (int j = 0; j < createdPixeliseContent[i].length; j++) {
                createColorCell(boardPane, i, j);
            }
        }
        rootPane.add(boardPane, 1, 1);
    }

    private void createColorCell(GridPane boardPane, int i, int j) {
        Rectangle colorCell = new Rectangle();
        colorCell.setFill(Color.TRANSPARENT);
        colorCell.widthProperty().bind(rootPane.widthProperty().multiply(80).divide(100).divide(SELECTED_WIDTH).subtract(0.5));
        colorCell.heightProperty().bind(rootPane.heightProperty().multiply(80).divide(100).divide(SELECTED_HEIGHT).subtract(0.5));

        colorCell.setOnMouseClicked(event -> {
            puzzleCellClickAction(event, colorCell, i, j);
        });

        boardPane.add(colorCell, i, j);
    }

    private void paintNavigationColumns() {
        List<NavigationBlock> navigationBlocks = new ArrayList<>();
        for (int i = 0; i < createdPixeliseContent.length; i++) {
            List<NavigationCell> navigationRow = new ArrayList<>();
            int sameColorCount = 0;
            Color prevColor = null;
            for (int j = 0; j < createdPixeliseContent[i].length; j++) {
                Color cellColor = createdPixeliseContent[i][j];
                if (cellColor.equals(prevColor) || prevColor == null) {
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
            if (sameColorCount > 0) {
                NavigationCell navigationCell = new NavigationCell();
                navigationCell.setColor(prevColor);
                navigationCell.setCount(sameColorCount);
                navigationRow.add(navigationCell);
            }
            navigationBlocks.add(new NavigationBlock(navigationRow));
            //addNavigationColumnToView(navigationRow);
        }

        rootPane.add(navigationColumns, 1, 0);
        int maxRowNavs = navigationBlocks.stream().mapToInt(NavigationBlock::getBlockNavsCount).max().getAsInt();
        navigationBlocks.forEach(val -> addNavigationColumnToView(val.getCells(), maxRowNavs - 1));
    }

    private void paintNavigationRows() {
        List<NavigationBlock> navigationBlocks = new ArrayList<>();
        for (int i = 0; i < createdPixeliseContent[0].length; i++) {
            List<NavigationCell> navigationRow = new ArrayList<>();
            int sameColorCount = 0;
            Color prevColor = null;
            for (Color[] colors : createdPixeliseContent) {
                Color cellColor = colors[i];
                if (cellColor.equals(prevColor) || prevColor == null) {
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
            if (sameColorCount > 0) {
                NavigationCell navigationCell = new NavigationCell();
                navigationCell.setColor(prevColor);
                navigationCell.setCount(sameColorCount);
                navigationRow.add(navigationCell);
            }
            //addNavigationRowToView(navigationRow, maxRowNavs);
            navigationBlocks.add(new NavigationBlock(navigationRow));
        }

        int maxRowNavs = navigationBlocks.stream().mapToInt(NavigationBlock::getBlockNavsCount).max().getAsInt();
        navigationBlocks.forEach(val -> addNavigationRowToView(val.getCells(), maxRowNavs - 1));

        rootPane.add(navigationRows, 0, 1);
    }

    private void addNavigationRowToView(List<NavigationCell> cells, int maxRowNavs) {
        cells = cells.stream().filter(c -> !c.getColor().equals(Color.WHITE)).collect(Collectors.toList());

        HBox navigationRow = new HBox();
        navigationRow.setSpacing(1);
        for (NavigationCell cell : cells) {
            NavigationButton navigationButton = new NavigationButton(cell, rootPane, maxRowNavs, selectedColorView, infoLabel, false);
            navigationRow.getChildren().add(navigationButton);
        }
        navigationRows.getChildren().add(navigationRow);
    }

    private void addNavigationColumnToView(List<NavigationCell> cells, int maxRowNavs) {
        cells = cells.stream().filter(c -> !c.getColor().equals(Color.WHITE)).collect(Collectors.toList());

        VBox navigationColumn = new VBox();
        navigationColumn.setSpacing(1);
        for (NavigationCell cell : cells) {
            NavigationButton navigationButton = new NavigationButton(cell, rootPane, maxRowNavs, selectedColorView, infoLabel, true);
            navigationColumn.getChildren().add(navigationButton);
        }
        navigationColumns.getChildren().add(navigationColumn);
    }

    private void puzzleCellClickAction(MouseEvent event, Rectangle rectangle, int i, int j) {
        cellSelectionAction(rectangle, i, j, event.getButton().equals(MouseButton.SECONDARY));
    }

    private void cellSelectionAction(Rectangle rectangle, int i, int j, boolean isSecondary) {
        if (!rectangle.getFill().equals(Color.TRANSPARENT)) {
            return;
        }
        if (isSecondary) {
            if (createdPixeliseContent[i][j].equals(Color.WHITE)) {
                rectangle.setFill(Color.IVORY);
                remainingCells--;
                infoLabel.setText(remainingCells + " left");
            } else {
                errorAlert("Wrong", "This tile has a color");
            }
        } else {
            if (createdPixeliseContent[i][j].equals(SELECTED_COLOR)) {
                rectangle.setFill(SELECTED_COLOR);
                remainingCells--;
                infoLabel.setText(remainingCells + " left");
            } else {
                errorAlert("Wrong", "This tile is not this color");
            }
        }
    }
}
