package com.tolstovenator.brewcalc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tolstovenator.brewcalc.repository.Hop;
import com.tolstovenator.brewcalc.repository.HopRepository;
import com.tolstovenator.brewcalc.repository.IngredientService;
import com.tolstovenator.brewcalc.repository.Sugar;
import com.tolstovenator.brewcalc.repository.Water;
import com.tolstovenator.brewcalc.repository.WaterRepository;
import com.tolstovenator.brewcalc.repository.Yeast;
import com.tolstovenator.brewcalc.repository.Yeast.YeastKey;
import com.tolstovenator.brewcalc.repository.YeastRepository;
import com.tolstovenator.brewcalc.repository.Sugar.SugarKey;
import com.tolstovenator.brewcalc.repository.SugarRepository;
import com.tolstovenator.brewcalc.ui.HopListAdapter;
import com.tolstovenator.brewcalc.ui.SugarListAdapter;
import com.tolstovenator.brewcalc.ui.WaterListAdapter;
import com.tolstovenator.brewcalc.ui.YeastListAdapter;
import com.tolstovenator.brewcalc.ui.ingredients.IngredientType;

/**
 * A fragment representing a single Ingredient detail screen. This fragment is
 * either contained in a {@link IngredientListActivity} in two-pane mode (on
 * tablets) or a {@link IngredientDetailActivity} on handsets.
 */
public class IngredientDetailFragment extends ListFragment {
	
	
	private IngredientService ingredientService;
	boolean mBound = false;
	
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	
	public static final String SELECTION_ID = "selection_id";
	
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private IngredientType ingredientType;
	
	private int mActivatedPosition = ListView.INVALID_POSITION;
	
	private int scrollY = -1;
	private int scrollPosition = -1;
	
	private final static DetailSelectionCallback dummyCallback = new DetailSelectionCallback() {
		
		@Override
		public void onHopSelected(Hop hop) {
		}

		@Override
		public void onSugarSelected(Sugar sugar) {
		}

		@Override
		public void onYeastSelected(Yeast yeast) {
			
		}
		
		@Override
		public void onWaterSelected(Water water) {
			
		}
		
	};

	public static final String SCROLL_Y = "SCROLL_Y";

	public static final String SCROLL_POSITION = "SCROLL_POSITION";

	
	private DetailSelectionCallback callback = dummyCallback;

	private HopRepository hopRepository;
	private SugarRepository sugarRepository;
	private YeastRepository yeastRepository;
	private WaterRepository waterRepository;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IngredientDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Activity parent = getActivity();
		
		Intent intent = new Intent(getActivity(), IngredientService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		
		if (getArguments().containsKey(SCROLL_Y)) {
			scrollY = getArguments().getInt(SCROLL_Y);
		}
		if (getArguments().containsKey(SCROLL_POSITION)) {
			scrollPosition = getArguments().getInt(SCROLL_POSITION);
		}
	}
	
	

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
	}

	private void initView() {
		hopRepository = ingredientService.getHopRepository();
		sugarRepository = ingredientService.getSugarRepository();
		yeastRepository = ingredientService.getYeastRepository();
		waterRepository = ingredientService.getWaterRepository();
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			ingredientType = IngredientType.values()[getArguments().getInt(ARG_ITEM_ID)];
			switch (ingredientType) {
			case HOPS:
				
				setListAdapter(new HopListAdapter(getActivity(),
						hopRepository.getValues()));
				break;
			case SUGARS:
				setListAdapter(new SugarListAdapter(getActivity(), sugarRepository.getValues()));
				break;	
			case YEAST:
				setListAdapter(new YeastListAdapter(getActivity(), yeastRepository.getValues()));
				break;
			case WATER:
				setListAdapter(new WaterListAdapter(getActivity(), waterRepository.getValues()));
				default:
		
			}
			if (getArguments().containsKey(SELECTION_ID)) {
				switch (ingredientType) {
				case HOPS:
					mActivatedPosition = hopRepository.getValues().indexOf(hopRepository.getValueByKey(getArguments().getString(SELECTION_ID)));
					break;
				case SUGARS:
					mActivatedPosition = sugarRepository.getValues().indexOf(sugarRepository.getValueByKey(SugarKey.fromString(getArguments().getString(SELECTION_ID))));
					break;
				case YEAST:
					mActivatedPosition = yeastRepository.getValues().indexOf(yeastRepository.getValueByKey(YeastKey.fromString(getArguments().getString(SELECTION_ID))));
					break;
				case WATER:
					mActivatedPosition = waterRepository.getValues().indexOf(waterRepository.getValueByKey(getArguments().getString(SELECTION_ID)));
					break;
				default:
					break;
				}
				
			}
		}
		
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			setActivatedPosition(mActivatedPosition);
		}
		if (scrollY != -1) {
			getListView().setSelectionFromTop(scrollPosition, scrollY);
		}
	}
	
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof DetailSelectionCallback)) {
			throw new RuntimeException("Activity should be of DetailSelectionCallback instance");
		}
		callback = ((DetailSelectionCallback)activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		callback = dummyCallback;
	}

	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			setActivatedPosition(mActivatedPosition);
		}
		if (scrollY != -1) {
			getListView().setSelectionFromTop(scrollPosition, scrollY);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
		//outState.putInt(key, value)
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		if (ingredientType != null) {
			switch (ingredientType) {
			case HOPS:
				callback.onHopSelected(hopRepository.getValues().get(position));
				break;
			case SUGARS:
				callback.onSugarSelected(sugarRepository.getValues().get(position));
				break;
			case YEAST:
				callback.onYeastSelected(yeastRepository.getValues().get(position));
				break;
			case WATER:
				callback.onWaterSelected(waterRepository.getValues().get(position));
			default:
				break;
			}
			
		}
	}
	
	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
	
	public interface DetailSelectionCallback {
		public void onHopSelected(Hop hop);

		public void onSugarSelected(Sugar sugar);
		
		public void onYeastSelected(Yeast yeast);
		
		public void onWaterSelected(Water water);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
        	IngredientService.LocalBinder binder = (IngredientService.LocalBinder) service;
            ingredientService = binder.getService();
            mBound = true;
            initView();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            ingredientService = null;
            hopRepository = null;
            waterRepository = null;
            yeastRepository = null;
            sugarRepository = null;
        }
    };
	
}
