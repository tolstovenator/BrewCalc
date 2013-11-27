package com.tolstovenator.brewcalc.calc;

import com.tolstovenator.brewcalc.preferences.UnitsConverter;
import com.tolstovenator.brewcalc.util.Rounder;

public class BoilOffCalculator {

	
	private double startWater;
	private double startSG;
	private double startPlato;
	private double boilLength;
	private double amountEvapourated;
	private double evapourationRatePercent;
	private double evapourationRateAmount;
	
	private double volumeAfterBoil;
	private double finalSG;
	private double finalPlato;
	
	public BoilOffCalculator(double defaultBatchSize) {
		startWater = defaultBatchSize;
		startPlato = 12;
		boilLength = 60;
		evapourationRatePercent = 10;
		recalcAll();
	}

	private void recalcAll() {
		startSG = Rounder.round(UnitsConverter.platoToSg(startPlato), 3);
		amountEvapourated = startWater * (boilLength / 60) * evapourationRatePercent / 100;
		evapourationRateAmount = startWater - startWater * evapourationRatePercent / 100;
		volumeAfterBoil = startWater - amountEvapourated;
		finalPlato = startPlato * startWater / volumeAfterBoil;
		amountEvapourated = Rounder.round(amountEvapourated, 2);
		evapourationRateAmount = Rounder.round(evapourationRateAmount, 2);
		volumeAfterBoil = Rounder.round(volumeAfterBoil, 2);
		finalPlato = Rounder.round(finalPlato, 2);
		finalSG = Rounder.round(UnitsConverter.platoToSg(finalPlato), 3);
	}

	public double getStartWater() {
		return startWater;
	}

	public double getStartSG() {
		return startSG;
	}

	public double getStartPlato() {
		return startPlato;
	}

	public double getBoilLength() {
		return boilLength;
	}

	public double getAmountEvapourated() {
		return amountEvapourated;
	}

	public double getEvapourationRatePercent() {
		return evapourationRatePercent;
	}

	public double getEvapourationRateAmount() {
		return evapourationRateAmount;
	}

	public double getVolumeAfterBoil() {
		return volumeAfterBoil;
	}

	public double getFinalSG() {
		return finalSG;
	}

	public double getFinalPlato() {
		return finalPlato;
	}
	
	
	
}
