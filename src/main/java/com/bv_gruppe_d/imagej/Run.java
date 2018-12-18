package com.bv_gruppe_d.imagej;

import com.bv_gruppe_d.imagej.DilateAndErode.StructureElement;

import ij.process.ImageProcessor;

/**
 * Das Bild das wir haben hat den Vordergrund schwarz
 * und den Hintergrund weiss, deshalb sind opening/closing
 * und dilate/erode vertauscht ODER das bild muss vorher negiert werden
 * @author user
 *
 */
public class Run{
	private final StructureElement edgeDetection;
	private final StructureElement closeHoles;
	
	public Run(int closeSize) {
		edgeDetection = new StructureElement(new boolean[][] {
			new boolean[]{true, true, true},
			new boolean[]{true, true, true},
			new boolean[]{true, true, true},
		}, 1, 1);

		boolean[][] closeMask = new boolean[closeSize][closeSize];
		for (int a = 0; a < closeMask.length; ++a) {
			boolean[] array = closeMask[a];
			for (int b = 0; b < array.length; ++b) {
				array[b] = true;
			}
		}
		closeHoles = new StructureElement(closeMask, closeSize/2, closeSize/2);
	}
	
	public void runEdgeDetection(ImageProcessor ip) {
		DilateAndErode.invert(ip);
		DilateAndErode.close(ip, closeHoles);
		ImageProcessor other = ip.duplicate();
		DilateAndErode.dilate(ip, other, edgeDetection);
		DilateAndErode.xor(other, ip);
	}
	
	public void runFindScrew(ImageProcessor ip) {
		DilateAndErode.open(ip, closeHoles);//remove stray white pixels
	}
}