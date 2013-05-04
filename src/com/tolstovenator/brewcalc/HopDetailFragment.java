package com.tolstovenator.brewcalc;

import com.tolstovenator.brewcalc.repository.Hop;
import com.tolstovenator.brewcalc.repository.IngredientService;
import com.tolstovenator.brewcalc.repository.Hop.HopForm;
import com.tolstovenator.brewcalc.repository.Hop.HopUsage;
import com.tolstovenator.brewcalc.repository.HopRepository;
import com.tolstovenator.brewcalc.ui.error.ErrorDialog;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HopDetailFragment extends Fragment implements TextWatcher, OnCheckedChangeListener {
	
	private IngredientService ingredientService;
	boolean mBound = false;
	
	
	/**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private String selectedItem;
    private HopRepository hopRepository;
    private Menu menu;
    private boolean changes = false;
    
    private EditText hop_name;

	private EditText origin;

	private EditText alpha_text;

	private EditText hopsNote;

	private EditText beta_text;

	private EditText humulene_text;

	private EditText myrcene_text;

	private EditText cohumulone_text;

	private EditText caryophyllene_text;

	private EditText storage_factor;
	
	private RadioButton whole;
	private RadioButton plug;
	private RadioButton pellet;
	private RadioButton bittering;
	private RadioButton aroma;
	private RadioButton dual;

	private RadioGroup hopFormGroup;

	private RadioGroup hopTypeGroup;
	
	private boolean resetForm = true;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HopDetailFragment() {
    }

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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.hops_edit_menu, menu);
		this.menu = menu;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hop_detail, container, false);
        hop_name = (EditText)rootView.findViewById(R.id.hop_name);
        hop_name.addTextChangedListener(this);
        origin = (EditText)rootView.findViewById(R.id.origin);
        origin.addTextChangedListener(this);
        hopsNote = (EditText)rootView.findViewById(R.id.hopsNote);
        hopsNote.addTextChangedListener(this);
        alpha_text = (EditText)rootView.findViewById(R.id.alpha_text);
        alpha_text.addTextChangedListener(this);
        beta_text = (EditText)rootView.findViewById(R.id.beta_text);
        beta_text.addTextChangedListener(this);
        myrcene_text = (EditText)rootView.findViewById(R.id.myrcene_text);
        myrcene_text.addTextChangedListener(this);
        humulene_text = (EditText)rootView.findViewById(R.id.humulene_text);
        humulene_text.addTextChangedListener(this);
        cohumulone_text = (EditText)rootView.findViewById(R.id.cohumulone_text);
        cohumulone_text.addTextChangedListener(this);
        caryophyllene_text = (EditText)rootView.findViewById(R.id.caryophyllene_text);
        caryophyllene_text.addTextChangedListener(this);
        storage_factor = (EditText)rootView.findViewById(R.id.storage_factor);
        storage_factor.addTextChangedListener(this);
        whole = (RadioButton) rootView.findViewById(R.id.whole);
        plug = (RadioButton) rootView.findViewById(R.id.plug);
        pellet = (RadioButton) rootView.findViewById(R.id.pellet);
        bittering = (RadioButton) rootView.findViewById(R.id.bittering);
        aroma = (RadioButton) rootView.findViewById(R.id.aroma);
        dual = (RadioButton) rootView.findViewById(R.id.dual);
        hopFormGroup = (RadioGroup)rootView.findViewById(R.id.hopFormGroup);
        hopFormGroup.setOnCheckedChangeListener(this);
        hopTypeGroup = (RadioGroup)rootView.findViewById(R.id.hopTypeGroup);
        hopTypeGroup.setOnCheckedChangeListener(this);
        return rootView;
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	
	

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		hop_name.removeTextChangedListener(this);
		hopsNote.removeTextChangedListener(this);
		origin.removeTextChangedListener(this);
		alpha_text.removeTextChangedListener(this);
		beta_text.removeTextChangedListener(this);
		caryophyllene_text.removeTextChangedListener(this);
		humulene_text.removeTextChangedListener(this);
		cohumulone_text.removeTextChangedListener(this);
		myrcene_text.removeTextChangedListener(this);
		storage_factor.removeTextChangedListener(this);
		hop_name = null;
		hopsNote = null;
		origin = null;
		alpha_text = null;
		beta_text = null;
		storage_factor = null;
		caryophyllene_text = null;
		myrcene_text = null;
		humulene_text = null;
		cohumulone_text = null;
		whole = null;
		bittering = null;
		plug = null;
		pellet = null;
		aroma = null;
		dual = null;
	}

	public void setHop(Hop hop) {
		resetForm(hop);
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

	private void resetForm(Hop hop) {
		selectedItem = hop.getName();
		resetForm = true;
		hop_name.setText(hop.getName());
		hopsNote.setText(hop.getDescription());
		origin.setText(hop.getOrigin());
		alpha_text.setText(String.valueOf(hop.getAlpha()));
		beta_text.setText(String.valueOf(hop.getBeta()));
		myrcene_text.setText(String.valueOf(hop.getMyrcene()));
		humulene_text.setText(String.valueOf(hop.getHumulene()));
		cohumulone_text.setText(String.valueOf(hop.getCohumulone()));
		caryophyllene_text.setText(String.valueOf(hop.getCaryophyllene()));
		storage_factor.setText(String.valueOf(hop.getStorageFactor()));
		RadioButton hopFormRadioButton;
    	switch (hop.getHopForm()) {
		case WHOLE:
			hopFormRadioButton = whole;
			break;
		case PLUG:
			hopFormRadioButton = plug;
			break;
		default:
			hopFormRadioButton = pellet;
		}
    	hopFormRadioButton.setChecked(true);
    	RadioButton hopTypeRadioButton;
    	switch (hop.getHopUsage()) {
		case BITTER:
			hopTypeRadioButton = bittering;
			break;
		case AROMA:
			hopTypeRadioButton = aroma;
			break;
		default:
			hopTypeRadioButton = dual;
		}
    	hopTypeRadioButton.setChecked(true);
    	resetForm = false;
	}
	
		@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!changes && !resetForm) {
			changes = true;
			MenuItem menuItem = menu.findItem(R.id.save_menu);
			menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			menuItem.setEnabled(true);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (!changes && !resetForm) {
			changes = true;
			MenuItem menuItem = menu.findItem(R.id.save_menu);
			menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			menuItem.setEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.save_menu) {
			if (validate()) {
				Hop changedHop = new Hop();
				changedHop.setAlpha(Double.valueOf(alpha_text.getText().toString()));
				changedHop.setBeta(Double.valueOf(beta_text.getText().toString()));
				changedHop.setStorageFactor(Integer.valueOf(storage_factor.getText().toString()));
				changedHop.setMyrcene(Double.valueOf(myrcene_text.getText().toString()));
				changedHop.setCohumulone(Double.valueOf(cohumulone_text.getText().toString()));
				changedHop.setHumulene(Double.valueOf(humulene_text.getText().toString()));
				changedHop.setCaryophyllene(Double.valueOf(caryophyllene_text.getText().toString()));
				changedHop.setName(hop_name.getText().toString().trim());
				changedHop.setOrigin(origin.getText().toString().trim());
				changedHop.setDescription(hopsNote.getText().toString().trim());
				HopForm hopForm;
				if (whole.isChecked()) {
					hopForm = HopForm.WHOLE;
				} else if (plug.isChecked()) {
					hopForm = HopForm.PLUG;
				} else {
					hopForm = HopForm.PELLET;
				}
				changedHop.setHopForm(hopForm);
				HopUsage hopUsage;
				if (bittering.isChecked()) {
					hopUsage = HopUsage.BITTER;
				} else if (aroma.isChecked()) {
					hopUsage = HopUsage.AROMA;
				} else {
					hopUsage = HopUsage.DUAL;
				}
				changedHop.setHopUsage(hopUsage);
				if (selectedItem == null || selectedItem.isEmpty()) {
					hopRepository.add(changedHop);
				} else {
					hopRepository.update(selectedItem, changedHop);
				}
				selectedItem = changedHop.getName();
				resetSaveMenu();
			}
			
		} else if (item.getItemId() == R.id.add_menu) {
			//TODO: check if edit is in progress
			if (getActivity() instanceof HopDetailsActivity) {
				getActivity().getActionBar().setTitle(R.string.add_hop);
			}
			setHop(new Hop());
		}
		return true;
	}

	private boolean validate() {
		String hopName = hop_name.getText().toString().trim();
		if (!selectedItem.equals(hopName)) {
			if (hopRepository.getHopByName(hopName) != null) {
				showError(getString(R.string.hop_already_exists, hopName));
				return false;
			}
		}
		return true;
		
	}
    
    private void showError(String message) {
    	ErrorDialog dialog = new ErrorDialog();
    	dialog.setMessage(message);
    	dialog.show(getFragmentManager(), "hops");
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
        	IngredientService.LocalBinder binder = (IngredientService.LocalBinder) service;
            ingredientService = binder.getService();
            mBound = true;
            hopRepository = ingredientService.getHopRepository();
            if (selectedItem != null) {
            	Hop hop = hopRepository.getHopByName(selectedItem);
            	setHop(hop);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            ingredientService = null;
            hopRepository = null;
        }
    };

}
