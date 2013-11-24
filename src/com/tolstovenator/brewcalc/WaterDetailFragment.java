package com.tolstovenator.brewcalc;


import com.tolstovenator.brewcalc.preferences.Formatter;
import com.tolstovenator.brewcalc.repository.IngredientService;
import com.tolstovenator.brewcalc.repository.Water;
import com.tolstovenator.brewcalc.repository.WaterRepository;
import com.tolstovenator.brewcalc.repository.Yeast;
import com.tolstovenator.brewcalc.repository.Yeast.YeastKey;
import com.tolstovenator.brewcalc.util.Rounder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

public class WaterDetailFragment extends AbstractDetailsFragment {
	
	private Water water;
    private Water editWater;
    
    private String selectedItem;
    private WaterRepository waterRepository;
    
    private EditText name;
    private EditText bestBeer;
    private EditText sulphate;
    private EditText sodium;
    private EditText magnesium;
    private EditText bicarbonade;
    private EditText calcium;
    private EditText chloride;
    private EditText ph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            selectedItem = getArguments().getString(ARG_ITEM_ID);
        }
        Intent intent = new Intent(getActivity(), IngredientService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
	
    @Override
	public void onDestroy() {
		super.onDestroy();
		if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_water_detail, container, false);
        name = (EditText) rootView.findViewById(R.id.waterName);
        bestBeer = (EditText) rootView.findViewById(R.id.bestBeer);
        calcium = (EditText) rootView.findViewById(R.id.calciumText);
        magnesium = (EditText) rootView.findViewById(R.id.magnesiumText);
        sulphate = (EditText) rootView.findViewById(R.id.sulfateText);
        sodium = (EditText) rootView.findViewById(R.id.sodiumText);
        bicarbonade = (EditText) rootView.findViewById(R.id.bicarbonadeText);
        chloride = (EditText) rootView.findViewById(R.id.chlorideText);
        ph = (EditText) rootView.findViewById(R.id.phText);
        EditText allEdits[] = new EditText[]{name, bestBeer, calcium, magnesium, sulphate, sodium, bicarbonade, chloride, ph};
    	for (int i = 0; i < allEdits.length; i++) {
			allEdits[i].setOnFocusChangeListener(this);
		}        
        return rootView;
    }
    
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.hops_edit_menu, menu);
		this.menu = menu;
	}
    
	public void setWater(Water water) {
		this.water = water;
		this.editWater = new Water(water);
		resetForm(water);
		resetSaveMenu();
	}
	
	private void resetForm(Water water) {
		selectedItem = water.getName();
		resetForm = true;
		name.setText(water.getName());
		bestBeer.setText(water.getBestBeer());
		calcium.setText(Integer.valueOf(water.getCalcium()));
		magnesium.setText(Integer.valueOf(water.getMagnesium()));
		sodium.setText(Integer.valueOf(water.getSodium()));
		sulphate.setText(Integer.valueOf(water.getSulfate()));
		bicarbonade.setText(Integer.valueOf(water.getBicarbonade()));
		chloride.setText(Integer.valueOf(water.getChloride()));
		ph.setText(Formatter.formatDoubleValue(water.getpH()));
		
		resetForm = false;
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
        	IngredientService.LocalBinder binder = (IngredientService.LocalBinder) service;
            ingredientService = binder.getService();
            mBound = true;
            waterRepository = ingredientService.getWaterRepository();
            if (selectedItem != null) {
            	Water water = waterRepository.getValueByKey(selectedItem);
            	setWater(water);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            ingredientService = null;
            waterRepository = null;
        }
    };
    
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		//TODO: validate
		if (!hasFocus) {
			if (v == name) {
				editWater.setName(readString(v));
				resetForm(editWater);
			} else if (v == bestBeer) {
				editWater.setBestBeer(readString(v));
				resetForm(editWater);
			} else if (v == calcium) {
				editWater.setCalcium(readDouble(v).intValue());
				resetForm(editWater);
			} else if (v == magnesium) {
				editWater.setMagnesium(readDouble(v).intValue());
				resetForm(editWater);
			} else if (v == sulphate) {
				editWater.setSulfate(readDouble(v).intValue());
				resetForm(editWater);
			} else if (v == sodium) {
				editWater.setSodium(readDouble(v).intValue());
				resetForm(editWater);
			} else if (v == bicarbonade) {
				editWater.setBicarbonade(readDouble(v).intValue());
				resetForm(editWater);
			} else if (v == chloride) {
				editWater.setChloride(readDouble(v).intValue());
				resetForm(editWater);
			} else if (v == ph) {
				editWater.setpH(readDouble(v).intValue());
				resetForm(editWater);
			}
		}
		if (!water.equals(editWater)) {
			markChanged();
		}
	}
	
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == R.id.save_menu) {
				if (selectedItem == null || selectedItem.isEmpty()) {
					waterRepository.add(editWater.getName(), editWater);
				} else {
					waterRepository.update(selectedItem, editWater);
				}
				water = new Water(editWater);
				selectedItem = editWater.getName();
				resetSaveMenu();
			} else if (item.getItemId() == R.id.add_menu) {
				//TODO: check if edit is in progress
				if (getActivity() instanceof WaterDetailActivity) {
					getActivity().getActionBar().setTitle(R.string.add_yeast);
				}
				setWater(new Water());
			}
			return true;
		}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

}
