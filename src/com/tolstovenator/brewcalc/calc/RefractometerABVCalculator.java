package com.tolstovenator.brewcalc.calc;

import com.tolstovenator.brewcalc.preferences.UnitsConverter;

public class RefractometerABVCalculator {

	private double waterCalibration;
	private double brixCorrectionFactor;
	
	private double brixReading;
	private double sgReading;
	private double platoReading;
	
	
	private double brixCorrected;
	
	private double og;
	private double ogPlato;
	
	private double abw;
	private double abv;
	
	private double adf;
	private double rdf;
	
	private double residualSg;
	private double residualPlato;
	
	public RefractometerABVCalculator (double waterCalibration, double brixCorrectionFactor) {
		this.waterCalibration = waterCalibration;
		this.brixCorrectionFactor = brixCorrectionFactor;
		
		brixReading = 6;
		platoReading = 5;
		recalcAll();
	}

	private void recalcAll() {
		sgReading = UnitsConverter.platoToSg(platoReading);
		brixCorrected = brixReading - waterCalibration;
		abv = (277.8851 - 277.4 * sgReading + 0.9956 * brixCorrected + 0.00523 * brixCorrected * brixCorrected + 0.000013 * brixCorrected * brixCorrected * brixCorrected) * (sgReading/0.79);
		abw = 0.794 * abv / sgReading;
		
	}
	
	
}
