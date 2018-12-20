package com.bv_gruppe_d.imagej;

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Schrauben_Erkennung implements PlugInFilter {

	private static final String[] choices = { "Kantenerkennung", "Schraube finden", "Schraube finden ohne Closing", 
												"Distanztransformation","Kantenerkennung ohne Closing"};

	@Override
	public void run(ImageProcessor ip) {

		GenericDialog dialog = createChoicesDialog(choices);

		runSelectedFunction(dialog,ip);
	}

	private GenericDialog createChoicesDialog(String[] choices) {
		GenericDialog dialog = new GenericDialog("Wähle eine Filtermethode");
		dialog.addChoice("Filtermethode:", choices, "");
		dialog.showDialog();
		return dialog;
	}

	private void runSelectedFunction(GenericDialog dialog, ImageProcessor ip) {
		Run r;
		
		switch (dialog.getNextChoice()) {
		case "Kantenerkennung":
			r = new Run(6);
			r.runEdgeDetection(ip);
			return;

		case "Schraube finden":
			r = new Run(6);
			r.runFindScrew(ip);
			return;

		case "Distanztransformation":
			r = new Run(6);
			
			return;
			
		case "Kantenerkennung ohne Closing":
			r = new Run(1);
			r.runEdgeDetection(ip);
			return;

		default:
			
		case "Schraube finden ohne Closing":
			r = new Run(1);
			r.runFindScrew(ip);
			return;
		}		
	}

	@Override
	public int setup(String arg0, ImagePlus img) {
		return DOES_8G;
	}
}
