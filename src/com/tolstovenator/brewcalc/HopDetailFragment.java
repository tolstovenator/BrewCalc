package com.tolstovenator.brewcalc;

import com.tolstovenator.brewcalc.repository.Hop;
import com.tolstovenator.brewcalc.repository.HopRepository;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HopDetailFragment extends Fragment {
	
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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HopDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            selectedItem = getArguments().getString(ARG_ITEM_ID);
        }
        hopRepository = ((IngredientListActivity)getActivity()).getHopRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hop_detail, container, false);
        if (selectedItem != null) {
        	Hop hop = hopRepository.getHopByName(selectedItem);
        	((EditText)rootView.findViewById(R.id.hop_name)).setText(hop.getName());
        	((EditText)rootView.findViewById(R.id.origin)).setText(hop.getOrigin());
        	((EditText)rootView.findViewById(R.id.hopsNote)).setText(hop.getDescription());
        	((EditText)rootView.findViewById(R.id.alpha_text)).setText(Double.toString(hop.getAlpha()));
        	((EditText)rootView.findViewById(R.id.beta_text)).setText(Double.toString(hop.getBeta()));
        	((EditText)rootView.findViewById(R.id.myrcene_text)).setText(Double.toString(hop.getMyrcene()));
        	((EditText)rootView.findViewById(R.id.humulene_text)).setText(Double.toString(hop.getHumulene()));
        	((EditText)rootView.findViewById(R.id.cohumulone_text)).setText(Double.toString(hop.getCohumulone()));
        	((EditText)rootView.findViewById(R.id.caryophyllene_text)).setText(Double.toString(hop.getCaryophyllene()));
        	((EditText)rootView.findViewById(R.id.storage_factor)).setText(Double.toString(hop.getStorageFactor()));
        	RadioButton hopFormRadioButton;
        	switch (hop.getHopForm()) {
			case WHOLE:
				hopFormRadioButton = (RadioButton) rootView.findViewById(R.id.whole);
				break;
			case PLUG:
				hopFormRadioButton = (RadioButton) rootView.findViewById(R.id.plug);
				break;
			default:
				hopFormRadioButton = (RadioButton) rootView.findViewById(R.id.pellet);
			}
        	hopFormRadioButton.setChecked(true);
        	RadioButton hopTypeRadioButton;
        	switch (hop.getHopUsage()) {
			case BITTER:
				hopTypeRadioButton = (RadioButton) rootView.findViewById(R.id.bittering);
				break;
			case AROMA:
				hopTypeRadioButton = (RadioButton) rootView.findViewById(R.id.aroma);
				break;
			default:
				hopTypeRadioButton = (RadioButton) rootView.findViewById(R.id.dual);
			}
        	hopTypeRadioButton.setChecked(true);
        	
        }
        return rootView;
    }
    
    

}
