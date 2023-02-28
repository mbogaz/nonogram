package com.codefirst.nonogram_fx.util;

import com.dajudge.colordiff.RgbColor;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Constants {

    public static Image ORG_IMAGE;
    public static Color[][] CREATED_PIXELISE_CONTENT;
    public static int SELECTED_WIDTH = 20, SELECTED_HEIGHT = 20;

    public static final Color[] SELECTABLE_COLORS = {Color.RED, Color.PINK, Color.ORANGE, Color.PURPLE, Color.BLUE, Color.BROWN, Color.GRAY, Color.GREEN, Color.YELLOW, Color.WHITE, Color.BLACK, Color.LIGHTSALMON};
    public static final List<RgbColor> PALETTE = Arrays.stream(SELECTABLE_COLORS).map(PixelArtUtil::toRgbColor).collect(Collectors.toList());

    public static final Font NAVIGATION_FONT = new Font(9);
}
