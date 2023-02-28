package com.codefirst.nonogram_fx.util;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.Arrays;

import com.dajudge.colordiff.*;

import static com.codefirst.nonogram_fx.util.Constants.*;

public class PixelArtUtil {


    public static Image pixelArtAndSaveImage(Image orgImage) {
        WritableImage writableImage = new WritableImage(orgImage.getPixelReader(), (int) orgImage.getWidth(), (int) orgImage.getHeight());
        CREATED_PIXELISE_CONTENT = new Color[(int) orgImage.getWidth()][(int) orgImage.getHeight()];

        PixelWriter pixelWriter = writableImage.getPixelWriter();
        PixelReader pixelReader = writableImage.getPixelReader();
        for (int i = 0; i < writableImage.getHeight(); i++) {
            for (int j = 0; j < writableImage.getWidth(); j++) {
                Color c = pixelReader.getColor(j, i);
                Color newColor = getNearestColor(c);
                pixelWriter.setColor(j, i, newColor);
                CREATED_PIXELISE_CONTENT[j][i] = newColor;
            }
        }

        return writableImage;
    }

    public static Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(null, null);
    }

    private static Color getNearestColor(Color orgColor) {
        return toColor(ColorDiff.closest(toRgbColor(orgColor), PALETTE));
    }


    public static Color toColor(RgbColor rgbColor) {
        return Arrays.stream(SELECTABLE_COLORS).filter(color -> toRgbColor(color).equals(rgbColor)).findFirst().orElse(null);
    }
    public static RgbColor toRgbColor(Color color) {
        return new RgbColor(color.getRed(), color.getGreen(), color.getBlue());
    }
}
