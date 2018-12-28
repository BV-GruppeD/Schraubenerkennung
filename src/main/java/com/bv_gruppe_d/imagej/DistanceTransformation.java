package com.bv_gruppe_d.imagej;
import ij.process.ImageProcessor;
import javafx.beans.binding.When;

import java.util.concurrent.TimeUnit;

public class DistanceTransformation {

    private static final int WHITE = 255;
    private static ImageProcessor imageProcessor;
    private static int imageWidth,imageHeight;
    private static int[][] image;

    public static void createBinaryImage(ImageProcessor _imageProcessor) {
        imageProcessor = _imageProcessor;
        byte distance = 1;
        boolean pixelChanged;
        imageWidth = imageProcessor.getWidth();
        imageHeight = imageProcessor.getHeight();
        initializeImage();

        do {
            pixelChanged = false;
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    if (imageProcessor.get(x, y) == WHITE && checkEdge(x,y)) {
                        imageProcessor.set(x,y,distance);
                        pixelChanged = true;
                        /*System.out.println("x: " + x + " y: " + y + " isedge distance: " + distance);
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
            }
            System.out.println("Distance: " + distance);
            initializeImage();
            distance+=10;
        } while (pixelChanged);
    }

    private static void initializeImage() {
        image = new int[imageWidth][imageHeight];
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                image[x][y] = imageProcessor.get(x,y);
            }
        }
    }

    private static boolean checkEdge(int x, int y) {
        if (image[x][y-1] != WHITE || image[x-1][y] != WHITE ||
                image[x+1][y] != WHITE || image[x][y+1] != WHITE) {
            return true;
        } else {
            return false;
        }
    }
}
