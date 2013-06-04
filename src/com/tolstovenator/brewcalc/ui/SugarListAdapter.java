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

import com.tolstovenator.brewcalc.repository.Sugar;

public class SugarListAdapter extends ArrayAdapter<Sugar>{
	
	private LayoutInflater inflater=null;
	private Activity activity;
	
	public SugarListAdapter(Activity activity, ArrayList<Sugar> sugars) {
		super(activity, android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, sugars);
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
        Sugar sugar = getItem(position);
        name.setText(sugar.getName());
        origin.setText(sugar.getOrigin());
        alpha.setText(activity.getString(sugar.getSugarType().getNameId()));
        image.setImageResource(R.drawable.content_new);
        return vi;
	}

}
