package com.bv_gruppe_d.imagej;

import com.bv_gruppe_d.imagej.DilateAndErode.StructureElement;

import ij.process.ImageProcessor;

/**
 * Das Bild das wir haben hat den Vordergrund schwarz und den Hintergrund weiss,
 * deshalb sind opening/closing und dilate/erode vertauscht ODER das bild muss
 * vorher negiert werden
 * 
 * @author patrick
 *
 */
public class Run {
	private final StructureElement edgeDetection;
	private final StructureElement closeHoles;
	private final StructureElement justKeepTheScrew;

	public Run(int closeSize) {
		edgeDetection = createCenteredSquare(3);
		closeHoles = createCenteredSquare(closeSize);
		justKeepTheScrew = createCenteredSquare(20);
	}

	private static StructureElement createCenteredSquare(int size) {
		return new StructureElement(createSquareMask(size), size / 2, size / 2);
	}

	private static boolean[][] createSquareMask(int size) {
		boolean[][] mask = new boolean[size][size];
		for (int a = 0; a < mask.length; ++a) {
			boolean[] array = mask[a];
			for (int b = 0; b < array.length; ++b) {
				array[b] = true;
			}
		}
		return mask;
	}

	public void runEdgeDetection(ImageProcessor ip) {
		DilateAndErode.invert(ip);
		DilateAndErode.close(ip, closeHoles);
		ImageProcessor other = ip.duplicate();
		DilateAndErode.dilate(ip, other, edgeDetection);
		DilateAndErode.xor(other, ip);
	}

	public void runFindScrew(ImageProcessor ip) {
		DilateAndErode.invert(ip);
		DilateAndErode.close(ip, closeHoles);
		DilateAndErode.open(ip, justKeepTheScrew);
	}
}