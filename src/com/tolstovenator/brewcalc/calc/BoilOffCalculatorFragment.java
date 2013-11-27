package com.tolstovenator.brewcalc.calc;

import android.R.bool;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tolstovenator.brewcalc.R;
import com.tolstovenator.brewcalc.preferences.Settings;

public class BoilOffCalculatorFragment extends AbstractCalculatorFragment {
	
	private BoilOffCalculator calculator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		calculator = new BoilOffCalculator(Settings.getDefaultBatch(getActivity()));
	}

	@Override
	public String[] getSpinNames() {
		return new String[]{"startWaterText", "startSGText", "startPlatoText", "boilLengthText", "amountEvapouratedText", "evapourationRatePercentText", "evapourationRateAmountText"};
	}

	@Override
	public String[] getEditNames() {
		return new String[] {"volumeAfterBoilText", "finalSGText", "finalPlatoText"};
	}

	@Override
	public Object getCalculator() {
		return calculator;
	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_boiloff_calculator_large;
	}

	@Override
	public String[] getTextViews() {
		return new String[]{"volumeAfterBoilName", "evapourationRateAmountName", "amountEvapouratedName", "startWaterName"};
	}
	
	@Override
	protected void initControls(View rootView) {
		super.initControls(rootView);
		String fluidUnits = Settings.getFluidUnits(getActivity());
		for (String string : textViews.keySet()) {
			TextView tv = textViews.get(string);
			tv.setText(tv.getText() + fluidUnits);
		}
	}

}
