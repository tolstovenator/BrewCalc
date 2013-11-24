package com.tolstovenator.brewcalc.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tolstovenator.brewcalc.R;
import com.tolstovenator.brewcalc.calc.Calculator;
import com.tolstovenator.brewcalc.ui.ingredients.IngredientType;

public class CalculatorListAdapter extends ArrayAdapter<Calculator> {
	
	private LayoutInflater inflater=null;
	private Activity activity;
	
	public CalculatorListAdapter(Activity activity) {
		super(activity, android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, Calculator.values());
		this.activity = activity;
		this.inflater = activity.getLayoutInflater();
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.ingredient_list_row, null);
        }
        TextView name = (TextView) vi.findViewById(R.id.ingredient_title);
        ImageView image = (ImageView) vi.findViewById(R.id.ingredient_image);
        Calculator calculator = Calculator.values()[position];
        name.setText(activity.getString(calculator.getResourceId()));
        image.setImageResource(calculator.getImageId());
        return vi;
	}

}
