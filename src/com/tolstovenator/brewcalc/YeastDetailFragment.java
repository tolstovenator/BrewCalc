package com.tolstovenator.brewcalc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.tolstovenator.brewcalc.preferences.Formatter;
import com.tolstovenator.brewcalc.repository.IngredientService;
import com.tolstovenator.brewcalc.repository.Yeast;
import com.tolstovenator.brewcalc.repository.Sugar.SugarType;
import com.tolstovenator.brewcalc.repository.Yeast.Flocculation;
import com.tolstovenator.brewcalc.repository.Yeast.YeastKey;
import com.tolstovenator.brewcalc.repository.Yeast.YeastMedium;
import com.tolstovenator.brewcalc.repository.Yeast.YeastType;
import com.tolstovenator.brewcalc.repository.YeastRepository;
import com.tolstovenator.brewcalc.util.Rounder;

public class YeastDetailFragment extends AbstractDetailsFragment implements OnItemSelectedListener {

	private Yeast yeast;
    private Yeast editYeast;
    
    private YeastKey selectedItem;
    private YeastRepository yeastRepository;
    
    private EditText name;
	private EditText lab;
	private EditText catalogId;
	private RadioGroup typeGroup;
	private RadioButton ale;
	private RadioButton lager;
	private RadioButton wine;
	private RadioButton champagne;
	private RadioGroup formGroup;
	private RadioButton dry;
	private RadioButton liquid;
	private RadioButton agar;
	private RadioGroup flocculationGroup;
	private RadioButton low;
	private RadioButton medium;
	private RadioButton high;
	private RadioButton veryHigh;
	private CheckBox useStarter;
	private CheckBox addToSecondary;
	private EditText minAttenuation;
	private EditText maxAttenuation;
	private EditText minTemperature;
	private EditText maxTemperature;
	private EditText notes;
	private EditText flavours;
	private Spinner maxReuse;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            selectedItem = YeastKey.fromString(getArguments().getString(ARG_ITEM_ID));
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
        View rootView = inflater.inflate(R.layout.fragment_yeast_detail, container, false);
        name = (EditText) rootView.findViewById(R.id.yeastName);
        lab = (EditText) rootView.findViewById(R.id.laboratory);
        catalogId = (EditText) rootView.findViewById(R.id.catalogId);
        minAttenuation = (EditText) rootView.findViewById(R.id.minAttenuationText);
        maxAttenuation = (EditText) rootView.findViewById(R.id.maxAttenuationText);
        minTemperature = (EditText) rootView.findViewById(R.id.minTemperatureText);
        maxTemperature = (EditText) rootView.findViewById(R.id.maxTemperatureText);
        flavours = (EditText) rootView.findViewById(R.id.flavour);
        notes = (EditText) rootView.findViewById(R.id.notes);
        typeGroup = (RadioGroup) rootView.findViewById(R.id.yeastTypeGroup);
        typeGroup.setOnCheckedChangeListener(this);
    	ale = (RadioButton) rootView.findViewById(R.id.ale);
    	lager = (RadioButton) rootView.findViewById(R.id.lager);
    	wine = (RadioButton) rootView.findViewById(R.id.wine);
    	champagne = (RadioButton) rootView.findViewById(R.id.champagne);
    	formGroup = (RadioGroup) rootView.findViewById(R.id.yeastFormGroup);
    	formGroup.setOnCheckedChangeListener(this);
    	dry = (RadioButton) rootView.findViewById(R.id.dry);
    	liquid = (RadioButton) rootView.findViewById(R.id.liquid);
    	agar = (RadioButton) rootView.findViewById(R.id.agar);
    	flocculationGroup = (RadioGroup) rootView.findViewById(R.id.yeastFlocculationGroup);
    	flocculationGroup.setOnCheckedChangeListener(this);
    	low = (RadioButton) rootView.findViewById(R.id.low);
    	medium = (RadioButton) rootView.findViewById(R.id.medium);
    	high = (RadioButton) rootView.findViewById(R.id.high);
    	veryHigh = (RadioButton) rootView.findViewById(R.id.veryHigh);
    	useStarter = (CheckBox) rootView.findViewById(R.id.useStarter);
    	useStarter.setOnCheckedChangeListener(this);
    	addToSecondary = (CheckBox) rootView.findViewById(R.id.addToSecondary);
    	addToSecondary.setOnCheckedChangeListener(this);
    	maxReuse = (Spinner) rootView.findViewById(R.id.maxReuse);
    	maxReuse.setOnItemSelectedListener(this);
    	EditText[] allEdits = new EditText[]{name, lab, catalogId, minAttenuation, maxAttenuation, minTemperature, maxTemperature, flavours, notes};
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

	public void setYeast(Yeast yeast) {
		this.yeast = yeast;
		this.editYeast = new Yeast(yeast);
		resetForm(yeast);
		resetSaveMenu();

	}
	
	private void resetForm(Yeast yeast) {
		selectedItem = yeast.getYeastKey();
		resetForm = true;
		name.setText(yeast.getName());
		lab.setText(yeast.getLab());
		catalogId.setText(yeast.getCatalogId());
		minAttenuation.setText(Integer.toString(yeast.getLowAttenuation()));
		maxAttenuation.setText(Integer.toString(yeast.getHighAttenuation()));
		minTemperature.setText(Formatter.formatDoubleValue(yeast.getMinTemp()));
		maxTemperature.setText(Formatter.formatDoubleValue(yeast.getMaxTemp()));
		flavours.setText(yeast.getFlavours());
		notes.setText(yeast.getNotes());
		YeastType yeastType = yeast.getYeastType();
		switch (yeastType) {
		case ALE:
			ale.setChecked(true);
			break;
		case CHAMPAGNE:
			champagne.setChecked(true);
			break;
		case LAGER:
			lager.setChecked(true);
			break;
		default:
			wine.setChecked(true);
		}
		Flocculation flocculation = yeast.getFlocculation();
		switch (flocculation) {
		case HIGH:
			high.setChecked(true);
			break;
		case VERYHIGH:
			veryHigh.setChecked(true);
			break;
		case MEDIUM:
			medium.setChecked(true);
			break;
		case LOW:
			low.setChecked(true);
			break;
		}
		YeastMedium medium = yeast.getYeastMedium();
		switch (medium) {
		case AGAR:
			agar.setChecked(true);
			break;
		case DRY:
			dry.setChecked(true);
			break;
		case LIQUID:
			liquid.setChecked(true);
		}
		useStarter.setChecked(yeast.isUseStarter());
		addToSecondary.setChecked(yeast.isAddToSecondary());
		maxReuse.setSelection(yeast.getMaxReuse());
		resetForm = false;
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
            yeastRepository = ingredientService.getYeastRepository();
            if (selectedItem != null) {
            	Yeast yeast = yeastRepository.getValueByKey(selectedItem);
            	setYeast(yeast);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            ingredientService = null;
            yeastRepository = null;
        }
    };
    
    @Override
	public void onFocusChange(View v, boolean hasFocus) {
		//TODO: validate
		if (!hasFocus) {
			if (v == name) {
				editYeast.setName(readString(v));
				resetForm(editYeast);
			} else if (v == lab) {
				editYeast.setLab(readString(v));
				resetForm(editYeast);
			} else if (v == catalogId) {
				editYeast.setCatalogId(readString(v));
				resetForm(editYeast);
			} else if (v == minAttenuation) {
				editYeast.setLowAttenuation(readDouble(v).intValue());
				resetForm(editYeast);
			} else if (v == maxAttenuation) {
				editYeast.setHighAttenuation(readDouble(v).intValue());
				resetForm(editYeast);
			} else if (v == minTemperature) {
				editYeast.setMinTemp(readDouble(v));
				resetForm(editYeast);
			} else if (v == maxTemperature) {
				editYeast.setMaxTemp(readDouble(v));
				resetForm(editYeast);
			} else if (v == flavours) {
				editYeast.setFlavours(readString(v));
				resetForm(editYeast);
			} else if (v == notes) {
				editYeast.setNotes(readString(v));
				resetForm(editYeast);
			} else if (v == maxReuse) {
				editYeast.setMaxReuse(((Spinner)v).getSelectedItemPosition() + 1);
				resetForm(editYeast);
			}
			if (!yeast.equals(editYeast)) {
				markChanged();
			}
		}
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.save_menu) {
			if (selectedItem.getCatalogId() == null || selectedItem.getCatalogId().isEmpty()) {
				yeastRepository.add(editYeast.getYeastKey(), editYeast);
			} else {
				yeastRepository.update(selectedItem, editYeast);
			}
			yeast = new Yeast(editYeast);
			selectedItem = editYeast.getYeastKey();
			resetSaveMenu();
		} else if (item.getItemId() == R.id.add_menu) {
			//TODO: check if edit is in progress
			if (getActivity() instanceof YeastDetailActivity) {
				getActivity().getActionBar().setTitle(R.string.add_yeast);
			}
			setYeast(new Yeast());
		}
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if (!resetForm) {
			if (arg0 == useStarter) {
				editYeast.setUseStarter(arg1);
			} else {
				editYeast.setAddToSecondary(arg1);
			}
			resetForm(editYeast);
			markChanged();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (!resetForm) {
			if (checkedId == R.id.agar) {
				editYeast.setYeastMedium(YeastMedium.AGAR);
			} else if (checkedId == R.id.dry) {
				editYeast.setYeastMedium(YeastMedium.DRY);
			} else if (checkedId == R.id.liquid) {
				editYeast.setYeastMedium(YeastMedium.LIQUID);
			} else if (checkedId == R.id.veryHigh) {
				editYeast.setFlocculation(Flocculation.VERYHIGH);
			} else if (checkedId == R.id.high) {
				editYeast.setFlocculation(Flocculation.HIGH);
			} else if (checkedId == R.id.medium) {
				editYeast.setFlocculation(Flocculation.MEDIUM);
			} else if (checkedId == R.id.low) {
				editYeast.setFlocculation(Flocculation.LOW);
			} else if (checkedId == R.id.ale) {
				editYeast.setYeastType(YeastType.ALE);
			} else if (checkedId == R.id.lager) {
				editYeast.setYeastType(YeastType.LAGER);
			} else if (checkedId == R.id.wine) {
				editYeast.setYeastType(YeastType.WINE);
			} else if (checkedId == R.id.champagne) {
				editYeast.setYeastType(YeastType.CHAMPAGNE);
			}
			resetForm(editYeast);
			markChanged();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (!resetForm) {
			editYeast.setMaxReuse(((Spinner)arg0).getSelectedItemPosition() + 1);
			resetForm(editYeast);
			markChanged();
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
