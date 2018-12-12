package com.bv_gruppe_d.imagej;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Schrauben_Erkennung implements PlugInFilter {
	
	@Override
	public void run(ImageProcessor ip) {
		
	}

	@Override
	public int setup(String arg0, ImagePlus img) {
		return DOES_8G;
	}

}
