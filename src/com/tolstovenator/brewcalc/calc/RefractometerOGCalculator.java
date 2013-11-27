package com.tolstovenator.brewcalc.calc;

import com.tolstovenator.brewcalc.preferences.UnitsConverter;

public class RefractometerOGCalculator {
	
	private double waterCalibration;
	private double brixCorrectionFactor;
	
	private double brixReading;
	private double brixCorrected;
	private double sg;
	private double plato;
	
	public RefractometerOGCalculator (double waterCalibration, double brixCorrectionFactor) {
		this.waterCalibration = waterCalibration;
		this.brixCorrectionFactor = brixCorrectionFactor;
		
		brixReading = 5;
		
		recalcAll();
	}

	private void recalcAll() {
		brixCorrected = brixReading - waterCalibration;
		plato = brixCorrected / brixCorrectionFactor;
		sg = UnitsConverter.platoToSg(plato);
	}
	
	public void setBrixReading(double brixReading) {
		this.brixReading = brixReading;
		recalcAll();
	}

	public double getWaterCalibration() {
		return waterCalibration;
	}

	public double getBrixCorrectionFactor() {
		return brixCorrectionFactor;
	}

	public double getBrixReading() {
		return brixReading;
	}

	public double getBrixCorrected() {
		return brixCorrected;
	}

	public double getSg() {
		return sg;
	}

	public double getPlato() {
		return plato;
	}

	
	
	
	

}
