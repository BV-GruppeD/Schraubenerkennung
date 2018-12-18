package com.bv_gruppe_d.imagej;

import ij.process.BinaryProcessor;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

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
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    if (imageProcessor.get(x, y) == 1) {
                        isEdge = checkEdge(x,y);
                    }
                    if (isEdge) {
                        distanceMap[x][y] = distance;
                    }

                }
            }
            distance++;
        }
    }

    private boolean checkEdge(int x, int y) {
        if (imageProcessor.get(x,y-1) == 0 || imageProcessor.get(x-1, y) == 0 ||
        imageProcessor.get(x+1, y) == 0 || imageProcessor.get(x, y+1) == 0) {
            return true;
        }
        return false;
    }

    public byte[][] getDistanceMap() {
        return distanceMap;
    }

    public void createBinaryImage() {
        // TODO: Create Binary Image
        // Lösung aus dem Buch anders als in der Vorlesung zumindest so wie ich es verstehe
        // 8 Bit Grauwertbild erzeugen noch nicht fertig
        //ByteProcessor byteProcessor = new ByteProcessor(imageWidth, imageHeight, distanceMap);
    }


}
