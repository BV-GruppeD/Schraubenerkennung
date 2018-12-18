package com.bv_gruppe_d.imagej;

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Schrauben_Erkennung implements PlugInFilter {

	private static final String[] choices = { "Kantenerkennung", "Schraube finden", "Distanztransformation"};

	@Override
	public void run(ImageProcessor ip) {
		GenericDialog dialog = createChoicesDialog(choices);

		runSelectedFunction(dialog);
	}

	private GenericDialog createChoicesDialog(String[] choices) {
		GenericDialog dialog = new GenericDialog("Wähle eine Filtermethode");
		dialog.addChoice("Filtermethode:", choices, "");
		dialog.showDialog();
		return dialog;
	}

	private void runSelectedFunction(GenericDialog dialog) {
		switch (dialog.getNextChoice()) {
		case "Kantenerkennung":
			
			break;

		case "Schraube finden":
			
			break;

		case "Distanztransformation":
			
			break;

		default:
			break;
		}
	}

	@Override
	public int setup(String arg0, ImagePlus img) {
		return DOES_8G;
	}
}
