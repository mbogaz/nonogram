package com.codefirst.nonogram_fx.dto;

import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class NavigationCell {
    private Color color;
    private int count;
}
