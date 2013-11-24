package com.tolstovenator.brewcalc.calc;

import com.tolstovenator.brewcalc.preferences.UnitsConverter;
import com.tolstovenator.brewcalc.util.Rounder;

public class AlcCalculator {
	
	private double ogGravity;
	private double fgGravity;
	private double platoGravity;
	private double platoFgGravity;
	private double abv;
	private double abw;
	private double adf;
	private double rdf;
	private double residualSg;
	private double residualPlato;
	
	public AlcCalculator() {
		platoGravity = 11;
		platoFgGravity = 2;
		recalcValues();
	}

	private void recalcValues() {
		ogGravity = UnitsConverter.platoToSg(platoGravity);
		fgGravity = UnitsConverter.platoToSg(platoFgGravity);
		//abv = Rounder.round((ogGravity - fgGravity) * 131, 2);
		//abw = Rounder.round(abv * 4 / 5, 2);
		abw = Rounder.round((76.08 * (ogGravity - fgGravity)) / (1.775 - ogGravity), 2);
		abv = Rounder.round(abw * fgGravity / 0.794, 2);
		adf = Rounder.round((ogGravity - fgGravity) / (ogGravity - 1) * 100, 2);
		rdf = Rounder.round((1 - (.1808*platoGravity + .8192*platoFgGravity) / platoGravity) * 100, 2);
		residualPlato = Rounder.round(platoFgGravity + .4654*(abw) - .003615*(abw)*abw / .9962, 2);
		residualSg = Rounder.round(UnitsConverter.platoToSg(residualPlato), 3);
	}
	
	

	public void setOgGravity(double ogGravity) {
		platoGravity = UnitsConverter.sgToPlato(ogGravity);
		recalcValues();
	}

	public void setFgGravity(double fgGravity) {
		platoFgGravity = UnitsConverter.sgToPlato(fgGravity);
		recalcValues();
	}

	public void setPlatoGravity(double platoGravity) {
		this.platoGravity = platoGravity;
		recalcValues();
	}

	public void setPlatoFgGravity(double platoFgGravity) {
		this.platoFgGravity = platoFgGravity;
		recalcValues();
	}

	public double getOgGravity() {
		return ogGravity;
	}

	public double getFgGravity() {
		return fgGravity;
	}

	public double getPlatoGravity() {
		return platoGravity;
	}

	public double getPlatoFgGravity() {
		return platoFgGravity;
	}

	public double getAbv() {
		return abv;
	}

	public double getAbw() {
		return abw;
	}

	public double getAdf() {
		return adf;
	}

	public double getRdf() {
		return rdf;
	}

	public double getResidualSg() {
		return residualSg;
	}

	public double getResidualPlato() {
		return residualPlato;
	}
	
	
	
}
