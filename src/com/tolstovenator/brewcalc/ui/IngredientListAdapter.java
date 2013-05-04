package com.tolstovenator.brewcalc.ui;

import com.tolstovenator.brewcalc.R;
import com.tolstovenator.brewcalc.ui.ingredients.IngredientType;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IngredientListAdapter extends ArrayAdapter<IngredientType> {
	
	private LayoutInflater inflater=null;
	private Activity activity;
	
	public IngredientListAdapter(Activity activity) {
		super(activity, android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, IngredientType.values());
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
        IngredientType ingredientType = IngredientType.values()[position];
        name.setText(activity.getString(ingredientType.getNameId()));
        image.setImageResource(ingredientType.getImageId());
        return vi;
	}

}
