package com.bv_gruppe_d.imagej;

import ij.process.ImageProcessor;

public class DilateAndErode {
	private static final int WHITE = 0xFFFFFF, BLACK = 0x000000;

	public enum Type {
		ERODE, DILATE
	}

	public static class StructureElement {
		private final boolean[][] mask;
		private final int anchorX, anchorY;
		private final int w, h;

		public StructureElement(boolean[][] mask, int anchorX, int anchorY) {
			this.mask = mask;
			this.anchorX = anchorX;
			this.anchorY = anchorY;
			w = mask.length;
			h = mask[0].length;

			if (anchorX < 0 || anchorX >= getWidth()) {
				throw new RuntimeException("anchorX out of bounds");
			}
			if (anchorY < 0 || anchorY >= getHeight()) {
				throw new RuntimeException("anchorY out of bounds");
			}
		}

		public boolean get(int x, int y) {
			// TODO Do we need to mirror the mask? (wg faltung)
			return mask[h - (y + 1)][w - (x + 1)];
		}

		public int getWidth() {
			return w;
		}

		public int getHeight() {
			return h;
		}
	}

	public static void dilate(ImageProcessor input, ImageProcessor output, StructureElement structureElement) {
		applyOperation(input, output, structureElement, Type.DILATE);
	}

	public static void erode(ImageProcessor input, ImageProcessor output, StructureElement structureElement) {
		applyOperation(input, output, structureElement, Type.ERODE);
	}

	private static void applyOperation(ImageProcessor input, ImageProcessor output, StructureElement structureElement,
			Type type) {
		int thresholdInclusive = -1;// just so java does not complain
		if (type == Type.ERODE) {
			int sum = 0;
			for (int x = 0; x < structureElement.getWidth(); x++) {
				for (int y = 0; y < structureElement.getHeight(); y++) {
					if (structureElement.get(x, y)) {
						sum++;
					}
				}
				thresholdInclusive = sum;// All pixels must be set
			}
		} else if (type == Type.DILATE) {
			thresholdInclusive = 1;// Only 1 pixel needs to be set
		} else {
			throw new RuntimeException("Unknown type: " + type);
		}

		int maxX = input.getWidth() - structureElement.getWidth();// off by 1?
		int maxY = input.getHeight() - structureElement.getHeight();// off by 1?

		// TODO what to do with the border pixels?
		// This just sets them to black
		for (int x = 0; x < input.getWidth(); x++) {
			for (int y = 0; y < input.getHeight(); y++) {
				output.set(x, y, BLACK);
			}
		}

		for (int x = 0; x < maxX; x++) {
			for (int y = 0; y < maxY; y++) {
				int sum = 0;
				for (int i = 0; i < structureElement.getWidth(); i++) {
					for (int j = 0; j < structureElement.getHeight(); j++) {
						boolean set = (input.getPixel(x + i, y + j) > 127) && structureElement.get(i, j);
						if (set) {
							sum++;
						}
					}
				}
				int newValue = (sum >= thresholdInclusive) ? WHITE : BLACK;
				output.putPixel(x + structureElement.anchorX, y + structureElement.anchorY, newValue);
			}
		}
	}

	public static void binarize(ImageProcessor ip, int thresholdIncl) {
		for (int x = 0; x < ip.getWidth(); x++) {
			for (int y = 0; y < ip.getHeight(); y++) {
				int value = ip.get(x, y) >= thresholdIncl ? WHITE : BLACK;
				ip.set(x, y, value);
			}
		}
	}

	public static void color2gray(ImageProcessor ip) {
		for (int x = 0; x < ip.getWidth(); x++) {
			for (int y = 0; y < ip.getHeight(); y++) {
				int rgb = ip.get(x, y);
				int intensity = (((rgb >> 16) & 0xff) + ((rgb >> 8) & 0xff) + (rgb & 0xff)) / 3;
				ip.set(x, y, intensity);
			}
		}
	}
}