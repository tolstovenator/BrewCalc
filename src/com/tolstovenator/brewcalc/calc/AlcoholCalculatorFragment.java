package com.tolstovenator.brewcalc.calc;

import java.util.HashMap;
import java.util.Map;

import com.tolstovenator.brewcalc.R;
import com.tolstovenator.brewcalc.ui.SpinBox;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AlcoholCalculatorFragment extends Fragment implements View.OnFocusChangeListener {
	
	private Map<String, EditText> editViews = new HashMap<String, EditText>();
	private Map<String, SpinBox> spinBoxes = new HashMap<String, SpinBox>();
	private AlcCalculator alcCalculator = new AlcCalculator();
	
	private boolean repainting = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alcohol_calculator, container, false);
        initControls(rootView);
        return rootView;
	}

	private void initControls(View rootView) {
		String[] editNames = getEditNames();
		String[] spinNames = getSpinNames();
		try {
			for (String string : editNames) {
				EditText editView = (EditText)rootView.findViewById((R.id.class.getField(string).getInt(null)));
				editView.setOnFocusChangeListener(this);
				editViews.put(string, editView);
			}
			for (String string : spinNames) {
				SpinBox spinBox = (SpinBox)rootView.findViewById((R.id.class.getField(string).getInt(null)));
				spinBox.setOnFocusChangeListener(this);
				spinBoxes.put(string, spinBox);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		repaintValues();
	}
	
	public void repaintValues() {
		repainting = true;
		for (String editProp: editViews.keySet()) {
			EditText editText = editViews.get(editProp);
			editText.setText(BeanUtils.getProperty(alcCalculator, editProp));
		}
		for (String editProp: spinBoxes.keySet()) {
			SpinBox editText = spinBoxes.get(editProp);
			editText.setCurrentValue(Double.valueOf(BeanUtils.getProperty(alcCalculator, editProp)));
		}
		repainting = false;
	}

	private String[] getSpinNames() {
		return new String[]{"ogGravityText", "fgGravityText", "platoGravityText", "platoFgGravityText"};
	}

	private String[] getEditNames() {
		return new String[]{"abvText", "abwText", "adfText", "rdfText", "residualSgText", "residualPlatoText"};
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (repainting) return;
		for (String prop : spinBoxes.keySet()) {
			if (v == spinBoxes.get(prop)) {
				BeanUtils.setProperty(alcCalculator, prop, ((SpinBox)v).getCurrentValue());
				repaintValues();
				return;
			}
		}
		
	}
	
}
