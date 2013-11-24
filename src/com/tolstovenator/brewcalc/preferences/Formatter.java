package com.tolstovenator.brewcalc.preferences;

import android.app.Activity;

import com.tolstovenator.brewcalc.preferences.Settings.ExtractPotential;
import com.tolstovenator.brewcalc.util.Rounder;

public class Formatter {
	
	public static String formatDoubleValue(Double value) {
		return String.valueOf(Rounder.round(value, 2));
	}
	
	public static String formatDoubleValueExtract(Double value, Activity activity) {
		int precision = Settings.getExtractPotential(activity) == ExtractPotential.SG ? 3 : 2;
		return String.valueOf(Rounder.round(value, precision));
	}

}
