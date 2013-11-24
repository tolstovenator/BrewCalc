package com.tolstovenator.brewcalc.calc;

import com.tolstovenator.brewcalc.preferences.UnitsConverter;

public class HydrometerCorrectionCalculator {

	private double calibrationTempF;
	private double calibrationTempC;
	private double currentSg;
	private double currentPlato;
	private double currentTempF;
	private double currentTempC;
	
	private double correctedSg;
	private double correctedPlato;
	
	public HydrometerCorrectionCalculator(double calibrationTempC) {
		this.calibrationTempC = calibrationTempC;
		currentTempC = calibrationTempC;
		currentPlato = 11;
		recalcAll();
	}

	private void recalcAll() {
		calibrationTempF = UnitsConverter.fromCtoF(calibrationTempC);
		currentSg = UnitsConverter.platoToSg(currentPlato);
		currentTempF = UnitsConverter.fromCtoF(currentTempC);
		double tr = currentTempF;
		double tc = calibrationTempF;
		correctedSg = currentSg * ((1.00130346 - 0.000134722124 * tr + 0.00000204052596 * tr * tr - 0.00000000232820948 * tr * tr * tr) / 
				(1.00130346 - 0.000134722124 * tc + 0.00000204052596 * tc * tc - 0.00000000232820948 * tc * tc * tc));
		correctedPlato = UnitsConverter.sgToPlato(correctedSg);
	}
	
	

	public void setCurrentSg(double currentSg) {
		currentPlato = UnitsConverter.sgToPlato(currentSg);
		recalcAll();
	}

	public void setCurrentPlato(double currentPlato) {
		this.currentPlato = currentPlato;
		recalcAll();
	}

	public void setCurrentTempF(double currentTempF) {
		currentTempC = UnitsConverter.fromFtoC(currentTempF);
		recalcAll();
	}

	public void setCurrentTempC(double currentTempC) {
		this.currentTempC = currentTempC;
		recalcAll();
	}

	public double getCalibrationTempF() {
		return calibrationTempF;
	}

	public double getCalibrationTempC() {
		return calibrationTempC;
	}

	public double getCurrentSg() {
		return currentSg;
	}

	public double getCurrentPlato() {
		return currentPlato;
	}

	public double getCurrentTempF() {
		return currentTempF;
	}

	public double getCurrentTempC() {
		return currentTempC;
	}

	public double getCorrectedSg() {
		return correctedSg;
	}

	public double getCorrectedPlato() {
		return correctedPlato;
	}
	
	
	
	
	
}
