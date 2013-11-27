package com.tolstovenator.brewcalc.calc;

import java.util.HashMap;
import java.util.Map;

import com.tolstovenator.brewcalc.R;
import com.tolstovenator.brewcalc.ui.SpinBox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public abstract class AbstractCalculatorFragment extends Fragment implements View.OnFocusChangeListener{

	private Map<String, EditText> editViews = new HashMap<String, EditText>();
	private Map<String, SpinBox> spinBoxes = new HashMap<String, SpinBox>();
	protected Map<String, TextView> textViews = new HashMap<String, TextView>();
	private boolean repainting = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	}

	protected void initControls(View rootView) {
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
			for (String string : getTextViews()) {
				TextView textView = (TextView)rootView.findViewById((R.id.class.getField(string).getInt(null)));
				textViews.put(string, textView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void repaintValues() {
		repainting = true;
		for (String editProp: editViews.keySet()) {
			EditText editText = editViews.get(editProp);
			editText.setText(BeanUtils.getProperty(getCalculator(), editProp));
		}
		for (String editProp: spinBoxes.keySet()) {
			SpinBox editText = spinBoxes.get(editProp);
			editText.setCurrentValue(Double.valueOf(BeanUtils.getProperty(getCalculator(), editProp)));
		}
		repainting = false;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (repainting) return;
		for (String prop : spinBoxes.keySet()) {
			if (v == spinBoxes.get(prop)) {
				BeanUtils.setProperty(getCalculator(), prop, ((SpinBox)v).getCurrentValue());
				repaintValues();
				return;
			}
		}
		
	}
	
	public abstract String[] getSpinNames();
	public abstract String[] getEditNames();
	public abstract String[] getTextViews();
	public abstract Object getCalculator();
	abstract public int getLayoutId();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View rootView = inflater.inflate(getLayoutId(), container, false);
	    initControls(rootView);
	    repaintValues();
	    return rootView;
	}

}
