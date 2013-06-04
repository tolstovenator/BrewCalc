package com.tolstovenator.brewcalc;


import com.tolstovenator.brewcalc.repository.Hop;
import com.tolstovenator.brewcalc.repository.IngredientService;
import com.tolstovenator.brewcalc.repository.Sugar;
import com.tolstovenator.brewcalc.repository.Sugar.SugarKey;
import com.tolstovenator.brewcalc.repository.Sugar.SugarType;
import com.tolstovenator.brewcalc.repository.SugarRepository;
import com.tolstovenator.brewcalc.util.Rounder;

import android.support.v4.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SugarDetailFragment extends Fragment implements OnFocusChangeListener, OnCheckedChangeListener, android.widget.CompoundButton.OnCheckedChangeListener {
	
	private IngredientService ingredientService;
	boolean mBound = false;
	
	
	/**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private Sugar sugar;
    private Sugar editSugar;
    
    private SugarKey selectedItem;
    private SugarRepository sugarRepository;
    
    private Menu menu;
    private boolean changes = false;
    
	private boolean resetForm = true;
	
	private EditText name;
	private EditText origin;
	private EditText supplier;
	private EditText description;
	private EditText potential;
	private EditText moisture;
	private EditText colour;
	private EditText coarseFineDiff;
	private EditText fineDry;
	private EditText fineAsIs;
	private EditText coarseDry;
	private EditText coarseAsIs;
	private EditText protein;
	private EditText nitrogen;
	private EditText maxInBatch;
	private EditText diastaticPower;
	private EditText hopsIbu;
	private EditText pH;
	private CheckBox mushMash;
	private RadioGroup typeGroup;
	private RadioButton grainButton;
	private RadioButton sugarButton;
	private RadioButton extractButton;
	private RadioButton adjunctButton;
	
	private EditText[] allEdits; 
	
	private EditText[][] disabledFields;
	
	public SugarDetailFragment() {
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            selectedItem = SugarKey.fromString(getArguments().getString(ARG_ITEM_ID));
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
        View rootView = inflater.inflate(R.layout.fragment_malt_detail, container, false);
        name = (EditText) rootView.findViewById(R.id.sugarName);
        origin = (EditText) rootView.findViewById(R.id.origin);
        description = (EditText) rootView.findViewById(R.id.description);
        supplier = (EditText) rootView.findViewById(R.id.supplier);
        potential = (EditText) rootView.findViewById(R.id.gravityPotentialText);
        colour = (EditText) rootView.findViewById(R.id.colourText);
        moisture = (EditText) rootView.findViewById(R.id.moistureText);
        coarseFineDiff = (EditText) rootView.findViewById(R.id.coarseFineDiffText);
        fineDry = (EditText) rootView.findViewById(R.id.fineDryText);
        fineAsIs = (EditText) rootView.findViewById(R.id.fineAsIsText);
        coarseDry = (EditText) rootView.findViewById(R.id.coarseDryText);
        coarseAsIs = (EditText) rootView.findViewById(R.id.coarseAsIsText);
        maxInBatch = (EditText) rootView.findViewById(R.id.maxInBatchText);
        protein = (EditText) rootView.findViewById(R.id.proteinText);
        nitrogen = (EditText) rootView.findViewById(R.id.nitrogenText);
        diastaticPower = (EditText) rootView.findViewById(R.id.diastaticText);
        hopsIbu = (EditText) rootView.findViewById(R.id.hopsIbuText);
        pH = (EditText) rootView.findViewById(R.id.pHText);
        typeGroup = (RadioGroup) rootView.findViewById(R.id.sugarTypeGroup);
        typeGroup.setOnCheckedChangeListener(this);
        grainButton = (RadioButton) rootView.findViewById(R.id.grain);
        extractButton = (RadioButton) rootView.findViewById(R.id.extract);
        sugarButton = (RadioButton) rootView.findViewById(R.id.sugar);
        adjunctButton = (RadioButton) rootView.findViewById(R.id.adjunct);
        mushMash = (CheckBox) rootView.findViewById(R.id.mustMash);
        mushMash.setOnCheckedChangeListener(this);
        allEdits = new EditText[] {
    			name, origin, supplier, description, potential, moisture, colour, coarseFineDiff, fineDry, fineAsIs, coarseDry,
    			coarseAsIs, protein, nitrogen, maxInBatch, diastaticPower, hopsIbu, pH
    	};
        disabledFields = new EditText[][] {
    			{hopsIbu, pH},
    			{moisture, coarseFineDiff, fineAsIs, coarseDry, coarseAsIs, protein, diastaticPower},
    			{moisture, coarseFineDiff, fineAsIs, coarseDry, coarseAsIs, protein, diastaticPower, hopsIbu},
    			{hopsIbu, pH}
    	};
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

	public void setSugar(Sugar sugar) {
		this.sugar = sugar;
		this.editSugar = new Sugar(sugar);
		resetForm(sugar);
		resetSaveMenu();

	}
	
	private void resetSaveMenu() {
		changes = false;
		
		
		if (menu != null) {
			MenuItem menuItem = menu.findItem(R.id.save_menu);
			menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			menuItem.setEnabled(false);
		}
	}
	
	private void resetForm(Sugar sugar) {
		selectedItem = sugar.getSugarKey();
		resetForm = true;
		for (EditText f : allEdits) {
			f.setEnabled(true);
		}
		name.setText(sugar.getName());
		origin.setText(sugar.getOrigin());
		description.setText(sugar.getDescription());
		supplier.setText(sugar.getSupplier());
		potential.setText(formatDoubleValue(sugar.getPotential()));
		moisture.setText(formatDoubleValue(sugar.getMoisture()));
		colour.setText(formatDoubleValue((double)sugar.getColour()));
		coarseFineDiff.setText(formatDoubleValue(sugar.getCoarseFineDiff()));
		fineDry.setText(formatDoubleValue(sugar.getFgDry()));
		fineAsIs.setText(formatDoubleValue(sugar.getFgAsIs()));
		coarseDry.setText(formatDoubleValue(sugar.getCgDry()));
		coarseAsIs.setText(formatDoubleValue(sugar.getCgAsIs()));
		protein.setText(formatDoubleValue(sugar.getProtein()));
		nitrogen.setText(formatDoubleValue(sugar.getTsn()));
		maxInBatch.setText(formatDoubleValue(sugar.getMaxInBatch()));
		diastaticPower.setText(formatDoubleValue(sugar.getDiastaticPower()));
		mushMash.setChecked(sugar.isMustMash());
		hopsIbu.setText(formatDoubleValue(sugar.getHop()));
		pH.setText(formatDoubleValue(sugar.getpH()));
		switch (sugar.getSugarType()) {
		case SUGAR:
			sugarButton.setChecked(true);
			break;
		case GRAIN:
			grainButton.setChecked(true);
			break;
		case ADJUNCT:
			adjunctButton.setChecked(true);
			break;
		case EXTRACT:
			extractButton.setChecked(true);
			break;
		default:
			break;
		}
		disableFields(sugar.getSugarType());
		resetForm = false;
	}

	private void disableFields(SugarType type) {
		EditText[] fields = disabledFields[type.ordinal()];
		for (EditText f : fields) {
			disableEditText(f);
		}
	}
	
	private void disableEditText(EditText editText) {
		editText.setText("N/A");
		editText.setEnabled(false);
		
	}

	public String formatDoubleValue(Double value) {
		return String.valueOf(Rounder.round(value, 2));
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
        	IngredientService.LocalBinder binder = (IngredientService.LocalBinder) service;
            ingredientService = binder.getService();
            mBound = true;
            sugarRepository = ingredientService.getSugarRepository();
            if (selectedItem != null) {
            	Sugar sugar = sugarRepository.getSugarByName(selectedItem);
            	setSugar(sugar);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            ingredientService = null;
            sugarRepository = null;
        }
    };


	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		//TODO: validate
		if (!hasFocus) {
			if (v == fineDry) {
				editSugar.setFgDry(readDouble(v));
				resetForm(editSugar);
			} else if (v == name) {
				editSugar.setName(readString(v));
			} else if (v == description) {
				editSugar.setDescription(readString(v));
			} else if (v == supplier) {
				editSugar.setSupplier(readString(v));
			} else if (v == origin) {
				editSugar.setOrigin(readString(v));
			} else if (v == name) {
				editSugar.setName(readString(v));
			} else if (v == colour) {
				editSugar.setColour(readDouble(v).intValue());
			} else if (v == potential) {
				editSugar.setPotential(readDouble(v));
				resetForm(editSugar);
			} else if (v == moisture) {
				editSugar.setMoisture(readDouble(v));
				resetForm(editSugar);
			} else if (v == coarseFineDiff) {
				editSugar.setCoarseFineDiff(readDouble(v));
				resetForm(editSugar);
			} else if (v == fineAsIs) {
				editSugar.setFgAsIs(readDouble(v));
				resetForm(editSugar);
			} else if (v == coarseDry) {
				editSugar.setCgDry(readDouble(v));
				resetForm(editSugar);
			} else if (v == coarseAsIs) {
				editSugar.setCgAsIs(readDouble(v));
				resetForm(editSugar);
			} else if (v == maxInBatch) {
				editSugar.setMaxInBatch(readDouble(v));
				resetForm(editSugar);
			} else if (v == protein) {
				editSugar.setProtein(readDouble(v));
				resetForm(editSugar);
			} else if (v == pH) {
				editSugar.setpH(readDouble(v));
				resetForm(editSugar);
			} else if (v == nitrogen) {
				editSugar.setTsn(readDouble(v));
				resetForm(editSugar);
			} else if (v == hopsIbu) {
				editSugar.setHop(readDouble(v));
				resetForm(editSugar);
			} else if (v == diastaticPower) {
				editSugar.setDiastaticPower(readDouble(v));
				resetForm(editSugar);
			} 
			if (!sugar.equals(editSugar)) {
				markChanged();
			}
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.save_menu) {
			if (selectedItem.getName() == null || selectedItem.getName().isEmpty()) {
				sugarRepository.add(editSugar);
			} else {
				sugarRepository.update(selectedItem, editSugar);
			}
			sugar = new Sugar(editSugar);
			selectedItem = editSugar.getSugarKey();
			resetSaveMenu();
		} else if (item.getItemId() == R.id.add_menu) {
			//TODO: check if edit is in progress
			if (getActivity() instanceof HopDetailsActivity) {
				getActivity().getActionBar().setTitle(R.string.add_hop);
			}
			setSugar(new Sugar());
		}
		return true;
	}

	private void markChanged() {
		changes = true;
		MenuItem menuItem = menu.findItem(R.id.save_menu);
		menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		menuItem.setEnabled(true);
	}

	private Double readDouble(View v) {
		return Double.valueOf(((EditText)v).getText().toString().trim());
	}
	
	private String readString(View v) {
		return ((EditText)v).getText().toString().trim();
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.sugar) {
			editSugar.setSugarType(SugarType.SUGAR);
		} else if (checkedId == R.id.grain) {
			editSugar.setSugarType(SugarType.GRAIN);
		}
		
		resetForm(editSugar);
		markChanged();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		editSugar.setMustMash(isChecked);
		resetForm(editSugar);
		markChanged();
	}

}
