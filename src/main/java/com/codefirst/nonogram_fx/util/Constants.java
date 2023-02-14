package com.codefirst.nonogram_fx.util;

import com.dajudge.colordiff.RgbColor;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Constants {
    public static Color[][] createdPixeliseContent;
    public static int SELECTED_WIDTH = 0, SELECTED_HEIGHT = 0;

    public static final Color[] selectedColors = {Color.RED, Color.PINK, Color.ORANGE, Color.PURPLE, Color.BLUE, Color.BROWN, Color.GRAY, Color.GREEN, Color.YELLOW, Color.WHITE, Color.BLACK, Color.LIGHTSALMON};
    public static final List<RgbColor> palette = Arrays.stream(selectedColors).map(PixelArtUtil::toRgbColor).collect(Collectors.toList());

    public static final Font NAVIGATION_FONT = new Font(9);
}
