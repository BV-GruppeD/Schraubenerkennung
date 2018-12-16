package com.bv_gruppe_d.imagej;

import com.bv_gruppe_d.imagej.DilateAndErode.StructureElement;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Schrauben_Erkennung implements PlugInFilter {

	@Override
	public void run(ImageProcessor ip) {
		DilateAndErode.color2gray(ip);
		DilateAndErode.binarize(ip, 128);
		StructureElement se = new StructureElement(new boolean[][] {
			new boolean[]{false, true, false},
			new boolean[]{true, true, true},
			new boolean[]{false, true, false},
		}, 1, 1);
		for (int i = 0; i < 1; ++i) {
			DilateAndErode.dilate(ip.duplicate(), ip, se);
		}
	}

	@Override
	public int setup(String arg0, ImagePlus img) {
		return DOES_ALL;
	}
}
