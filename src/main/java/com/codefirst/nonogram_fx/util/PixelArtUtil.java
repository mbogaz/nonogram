package com.codefirst.nonogram_fx.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Arrays;

import com.dajudge.colordiff.*;

import static com.codefirst.nonogram_fx.util.Constants.*;

public class PixelArtUtil {


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


    public static Color toColor(RgbColor rgbColor) {
        return Arrays.stream(selectedColors).filter(color -> toRgbColor(color).equals(rgbColor)).findFirst().orElse(null);
    }
    public static RgbColor toRgbColor(Color color) {
        return new RgbColor(color.getRed(), color.getGreen(), color.getBlue());
    }
}
