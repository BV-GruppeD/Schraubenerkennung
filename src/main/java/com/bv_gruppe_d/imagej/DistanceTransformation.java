package com.bv_gruppe_d.imagej;

import ij.ImagePlus;
import ij.process.BinaryProcessor;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import javafx.beans.binding.When;

import java.util.Arrays;

public class DistanceTransformation {

    private static final int WHITE = 1, BLACK = 0;
    private ImageProcessor imageProcessor;
    private double[] m;
    private int imageWidth,imageHeight;
    private byte[][] distanceMap;

    public DistanceTransformation(ImageProcessor ip, int norm) {

        imageProcessor = ip;
        double[] _m = {1, 0};
        if (norm == 1) {
            m[2] = 2;
        } else if (norm == 2) {
            m[2] = Math.sqrt(2);
        } else {
            throw new IllegalArgumentException("selected standard not available. Please select 1 for Manhatten distanc" +
                    " an 2 for euclidean distance");
        }
        m = _m;
        imageWidth = imageProcessor.getWidth();
        imageHeight = imageProcessor.getHeight();
        distanceMap = new byte[imageWidth][imageHeight];
        Arrays.fill(distanceMap, 0);
        calculateDistanceMap();
    }

    private void calculateDistanceMap() {

        byte distance = 1;
        boolean isEdge = false;
        boolean pixelChanged = false;

        while (!pixelChanged) {
            pixelChanged = false;
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    if (imageProcessor.get(x, y) == BLACK) {
                        isEdge = checkEdge(x,y);
                    }
                    if (isEdge) {
                        imageProcessor.putPixel(x,y,WHITE);
                        distanceMap[x][y] = distance;
                        pixelChanged = true;
                    }

                }
            }
            distance++;
        }
    }

    private boolean checkEdge(int x, int y) {
        if (imageProcessor.get(x,y-1) == WHITE || imageProcessor.get(x-1, y) == WHITE ||
        imageProcessor.get(x+1, y) == WHITE || imageProcessor.get(x, y+1) == WHITE) {
            return true;
        }
        return false;
    }

    public byte[][] getDistanceMap() {
        return distanceMap;
    }

    public ImageProcessor createBinaryImage() {
        // TODO: Create Binary Image
        // Lösung aus dem Buch anders als in der Vorlesung zumindest so wie ich es verstehe
        // 8 Bit Grauwertbild erzeugen noch nicht fertig
        //ByteProcessor byteProcessor = new ByteProcessor(imageWidth, imageHeight, distanceMap);

        ImageProcessor ip;
        ip = imageProcessor;
        int[] temp = (int[]) ip.getPixels();

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                temp[y+x] = distanceMap[x][y];
            }
        }

        ByteProcessor bp = ip.convertToByteProcessor(false);
        ImagePlus imagePlus = new ImagePlus("Distanc_Transform",bp);
        return imagePlus.getProcessor();
    }


}
