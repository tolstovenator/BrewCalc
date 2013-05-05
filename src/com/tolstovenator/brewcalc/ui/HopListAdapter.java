package com.tolstovenator.brewcalc.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tolstovenator.brewcalc.R;
import com.tolstovenator.brewcalc.repository.Hop;
import com.tolstovenator.brewcalc.ui.ingredients.IngredientType;

public class HopListAdapter extends ArrayAdapter<Hop>{

	private LayoutInflater inflater=null;
	private Activity activity;
	
	public HopListAdapter(Activity activity, ArrayList<Hop> hops) {
		super(activity, android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, hops);
		this.activity = activity;
		this.inflater = activity.getLayoutInflater();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null) {
            vi = inflater.inflate(R.layout.hop_list_row, null);
        }
        TextView name = (TextView) vi.findViewById(R.id.hop_title);
        TextView origin = (TextView) vi.findViewById(R.id.hop_origin);
        TextView alpha = (TextView) vi.findViewById(R.id.hop_alpha);
        ImageView image = (ImageView) vi.findViewById(R.id.hop_image);
        Hop hop = getItem(position);
        name.setText(hop.getName());
        origin.setText(hop.getOrigin());
        alpha.setText(String.valueOf(hop.getAlpha()));
        image.setImageResource(R.drawable.content_new);
        return vi;
	}
}
