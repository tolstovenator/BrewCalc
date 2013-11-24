package com.tolstovenator.brewcalc.preferences;

import com.tolstovenator.brewcalc.preferences.Settings.ExtractPotential;
import com.tolstovenator.brewcalc.repository.Sugar;

import android.app.Activity;

public class UnitsConverter {
	
	public static double convertFromExtractPoints(double potentialInPoints, Activity activity) {
		ExtractPotential potential = Settings.getExtractPotential(activity);
		switch (potential) {
		case SG:
			return potentialInPoints / 1000. + 1;
		case POINTS:
			return potentialInPoints;
		case PLATO:
			double sg = potentialInPoints / 1000. + 1;
			return sgToPlato(sg);
		case IOB_HWE:
			return 386 * potentialInPoints / Sugar.MAXIMUM_POTENTIAL;
		case PERCENT_YIELD:
			return potentialInPoints / Sugar.MAXIMUM_POTENTIAL * 100;
		default:
			return potentialInPoints;
		}
	}
	
	public static double converToExtractPoints(double potential, Activity activity) {
		ExtractPotential potentialType = Settings.getExtractPotential(activity);
		switch (potentialType) {
		case SG:
			return (potential - 1) * 1000;
		case POINTS:
			return potential;
		case PLATO:
			double sg = platoToSg(potential);
			return (sg - 1) * 1000;
		case IOB_HWE:
			return Math.min(potential, 386) * Sugar.MAXIMUM_POTENTIAL / 386;
		case PERCENT_YIELD:
			return Math.min(potential, 100) * Sugar.MAXIMUM_POTENTIAL / 100; 
		default:
			return potential;
		}
	}
	
	public static double platoToSg(double plato) {
		return 1 + (plato / (258.6 - ((plato / 258.2) * 227.1)));
	}
	
	public static double sgToPlato(double sg) {
		return (-1 * 616.868) + (1111.14 * sg) - (630.272 * sg * sg) + (135.997 * sg * sg * sg);
	}

	public static double fromCtoF(double tempC) {
		return tempC * 9 / 5 + 32;
	}
	
	public static double fromFtoC(double tempF) {
		return (tempF - 32) * 5 / 9;
	}
	
}
