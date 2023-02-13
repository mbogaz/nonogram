package com.codefirst.nonogram_fx.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.dajudge.colordiff.*;

import static com.codefirst.nonogram_fx.controller.HelloController.createdPixeliseContent;

public class PixelArtUtil {
    private static final Color[] selectedColors = {Color.RED, Color.PINK, Color.ORANGE, Color.PURPLE, Color.BLUE, Color.BROWN, Color.GRAY, Color.GREEN, Color.YELLOW, Color.WHITE, Color.BLACK, Color.LIGHTSALMON};
    private static final List<RgbColor> palette = Arrays.stream(selectedColors).map(PixelArtUtil::toRgbColor).collect(Collectors.toList());

    public static Image pixelArtImage(Image orgImage) {
        WritableImage writableImage = new WritableImage(orgImage.getPixelReader(), (int) orgImage.getWidth(), (int) orgImage.getHeight());
        createdPixeliseContent = new Color[(int) orgImage.getWidth()][(int) orgImage.getHeight()];

        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = writableImage.getPixelReader();
        for (int i = 0; i < writableImage.getHeight(); i++) {
            for (int j = 0; j < writableImage.getWidth(); j++) {
                Color c = pixelReader.getColor(j, i);
                Color newColor = getNearestColor(c);
                pixelWriter.setColor(j, i, newColor);
                createdPixeliseContent[j][i] = newColor;
            }
        }

        return writableImage;
    }

    private static Color getNearestColor(Color orgColor) {
        return toColor(ColorDiff.closest(toRgbColor(orgColor), palette));
    }


    private static Color toColor(RgbColor rgbColor) {
        return Arrays.stream(selectedColors).filter(color -> toRgbColor(color).equals(rgbColor)).findFirst().orElse(null);
    }
    private static RgbColor toRgbColor(Color color) {
        return new RgbColor(color.getRed(), color.getGreen(), color.getBlue());
    }
}
