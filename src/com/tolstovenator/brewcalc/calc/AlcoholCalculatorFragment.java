package com.tolstovenator.brewcalc.calc;


import com.tolstovenator.brewcalc.R;


public class AlcoholCalculatorFragment extends AbstractCalculatorFragment {
	
	private AlcCalculator alcCalculator = new AlcCalculator();
	
	public int getLayoutId() {
		return R.layout.fragment_alcohol_calculator;
	}

	public String[] getSpinNames() {
		return new String[]{"ogGravityText", "fgGravityText", "platoGravityText", "platoFgGravityText"};
	}

	public String[] getEditNames() {
		return new String[]{"abvText", "abwText", "adfText", "rdfText", "residualSgText", "residualPlatoText"};
	}
	
	public Object getCalculator() {
		return alcCalculator; 
	}

	@Override
	public String[] getTextViews() {
		return new String[0];
	}
	
}
