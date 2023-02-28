package com.codefirst.nonogram_fx.controller;

import com.codefirst.nonogram_fx.dto.NavigationCell;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codefirst.nonogram_fx.util.Constants.*;
import static com.codefirst.nonogram_fx.util.NotificationUtil.errorAlert;

public class PuzzleController {
    @FXML protected GridPane rootPane;
    @FXML protected Label infoLabel;
    @FXML protected Rectangle selectedColorView;

    Color selectedColor = Color.BLACK;
    VBox navigationRows = new VBox();
    HBox navigationColumns = new HBox();

    int remainingCells;

    public void init() {
        selectedColorView.widthProperty().bind(rootPane.widthProperty().multiply(20).divide(100));
        selectedColorView.heightProperty().bind(rootPane.heightProperty().multiply(20).divide(100));
        remainingCells = createdPixeliseContent.length * createdPixeliseContent[0].length;
        infoLabel.setText(remainingCells + " left");
        infoLabel.setStyle("--fxbackground-color: " + selectedColor);

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
            System.out.println("setOnMouseClicked");
            puzzleCellClickAction(event, colorCell, i, j);
        });
        /*colorCell.setOnMouseDragged(event -> {
            System.out.println("setOnMouseDragged");
            puzzleCellClickAction(event, colorCell, i, j);
        });
        colorCell.setOnMouseDragEntered(event -> {
            System.out.println("setOnMouseDragEntered");
            puzzleCellClickAction(event, colorCell, i, j);
        });
        colorCell.setOnMouseDragExited(event -> {
            System.out.println("setOnMouseDragExited");
            puzzleCellClickAction(event, colorCell, i, j);
        });
        colorCell.setOnMouseDragReleased(event -> {
            System.out.println("setOnMouseDragReleased");
            puzzleCellClickAction(event, colorCell, i, j);
        });
        colorCell.setOnMouseDragOver(event -> {
            System.out.println("setOnMouseDragOver");
            puzzleCellClickAction(event, colorCell, i, j);
        });
        /*colorCell.setOnMouseEntered(event -> {
            System.out.println("setOnMouseEntered");
            puzzleCellClickAction(event, colorCell, i, j);
        });*/
        /*colorCell.setOnMouseReleased(event -> {
            System.out.println("setOnMouseReleased");
            puzzleCellClickAction(event, colorCell, i, j);
        });
        colorCell.setOnDragDetected(event -> {
            System.out.println("setOnMouseReleased");
            puzzleCellClickAction(event, colorCell, i, j);
        });*/

        boardPane.add(colorCell, i, j);
    }

    private void paintNavigationColumns() {

        for (int i = 0; i < createdPixeliseContent.length; i++) {
            List<NavigationCell> navigationRow = new ArrayList<>();
            int sameColorCount = 0;
            Color prevColor = null;
            for (int j = 0; j < createdPixeliseContent[i].length; j++) {
                Color cellColor = createdPixeliseContent[i][j];
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
            addNavigationColumnToView(i, navigationRow);
        }

        rootPane.add(navigationColumns, 1, 0);
    }

    private void paintNavigationRows() {

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
            if(sameColorCount > 0) {
                NavigationCell navigationCell = new NavigationCell();
                navigationCell.setColor(prevColor);
                navigationCell.setCount(sameColorCount);
                navigationRow.add(navigationCell);
            }
            addNavigationRowToView(i, navigationRow);
        }

        rootPane.add(navigationRows, 0, 1);
    }

    private void addNavigationRowToView(int rowIndex, List<NavigationCell> cells) {
        cells = cells.stream().filter(c -> !c.getColor().equals(Color.WHITE)).collect(Collectors.toList());

        HBox navigationRow = new HBox();
        navigationRow.setSpacing(1);
        for (NavigationCell cell : cells) {
            Rectangle navigationCell = new Rectangle();
            navigationCell.setFill(cell.getColor());
            navigationCell.widthProperty().bind(rootPane.widthProperty().multiply(20).divide(100).divide(cells.size()).subtract(0.5));
            navigationCell.heightProperty().bind(rootPane.heightProperty().multiply(80).divide(100).divide(SELECTED_HEIGHT).subtract(0.6));

            final Text text = new Text(String.valueOf(cell.getCount()));
            text.setFont(NAVIGATION_FONT);
            if (cell.getColor().equals(Color.BLACK)) {
                text.setFill(Color.WHITE);
            } else {
                text.setFill(Color.BLACK);
            }
            final StackPane stack = new StackPane();
            stack.setOnMouseClicked(event -> navigationCellClickAction(cell.getColor()));
            stack.getChildren().addAll(navigationCell, text);
            navigationRow.getChildren().add(stack);
        }
        navigationRows.getChildren().add(navigationRow);
    }

    private void addNavigationColumnToView(int rowIndex, List<NavigationCell> cells) {
        cells = cells.stream().filter(c -> !c.getColor().equals(Color.WHITE)).collect(Collectors.toList());

        VBox navigationColumn = new VBox();
        navigationColumn.setSpacing(1);
        for (NavigationCell cell : cells) {
            Rectangle navigationCell = new Rectangle();
            navigationCell.setFill(cell.getColor());
            navigationCell.widthProperty().bind(rootPane.widthProperty().multiply(80).divide(100).divide(SELECTED_WIDTH).subtract(0.6));
            navigationCell.heightProperty().bind(rootPane.heightProperty().multiply(20).divide(100).divide(cells.size()).subtract(0.5));

            final Text text = new Text(String.valueOf(cell.getCount()));
            text.setFont(NAVIGATION_FONT);
            if (cell.getColor().equals(Color.BLACK)) {
                text.setFill(Color.WHITE);
            } else {
                text.setFill(Color.BLACK);
            }
            final StackPane stack = new StackPane();
            stack.setOnMouseClicked(event -> navigationCellClickAction(cell.getColor()));
            stack.getChildren().addAll(navigationCell, text);
            navigationColumn.getChildren().add(stack);
        }
        navigationColumns.getChildren().add(navigationColumn);
    }

    private void navigationCellClickAction(Color color) {
        selectedColor = color;
        selectedColorView.setFill(color);
        if(color.equals(Color.BLACK)) {
            infoLabel.setTextFill(Color.WHITE);
        } else {
            infoLabel.setTextFill(Color.BLACK);
        }
    }

    private void puzzleCellClickAction(MouseEvent event, Rectangle rectangle, int i, int j)  {
       cellSelectionAction(rectangle, i, j, event.getButton().equals(MouseButton.SECONDARY));
    }

    private void cellSelectionAction(Rectangle rectangle, int i, int j, boolean isSecondary) {
        if(!rectangle.getFill().equals(Color.TRANSPARENT)) {
            System.out.println("return");
            return;
        }
        if(isSecondary) {
            if(createdPixeliseContent[i][j].equals(Color.WHITE)) {
                rectangle.setFill(Color.IVORY);
                remainingCells--;
                infoLabel.setText(remainingCells + " left");
            } else {
                errorAlert("Wrong", "This tile has a color");
            }
        } else {
            if(createdPixeliseContent[i][j].equals(selectedColor)) {
                rectangle.setFill(selectedColor);
                remainingCells--;
                infoLabel.setText(remainingCells + " left");
            } else {
                errorAlert("Wrong", "This tile is not this color");
            }
        }
    }
}
