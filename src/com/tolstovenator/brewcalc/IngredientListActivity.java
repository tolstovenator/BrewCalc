package com.tolstovenator.brewcalc;

import com.tolstovenator.brewcalc.preferences.SettingsActivity;
import com.tolstovenator.brewcalc.repository.Hop;
import com.tolstovenator.brewcalc.repository.HopRepository;
import com.tolstovenator.brewcalc.repository.IngredientService;
import com.tolstovenator.brewcalc.repository.Sugar;
import com.tolstovenator.brewcalc.ui.ingredients.IngredientType;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * An activity representing a list of Ingredients. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link IngredientDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link IngredientListFragment} and the item details (if present) is a
 * {@link IngredientDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link IngredientListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class IngredientListActivity extends AbstractActionBarActivity implements
		IngredientListFragment.Callbacks, IngredientDetailFragment.DetailSelectionCallback, RepositoryActivity {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private boolean clickViewActvated;
	private boolean detailedView;
	private IngredientService ingredientService;
	boolean mBound = false;
	private IngredientDetailFragment fragment;
	private IngredientListFragment listFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredient_list);
		
		if (findViewById(R.id.ingredient_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
			listFragment = new IngredientListFragment();
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.ingredient_list_container, listFragment);
			fragmentTransaction.commit();

		}
		createCommonMenu();
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, IngredientService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
	
	

	/**
	 * Callback method from {@link IngredientListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(IngredientType id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putInt(IngredientDetailFragment.ARG_ITEM_ID, id.ordinal());
			fragment = new IngredientDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.ingredient_detail_container, fragment)
					.commit();
			

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					IngredientDetailActivity.class);
			detailIntent.putExtra(IngredientDetailFragment.ARG_ITEM_ID, getString(IngredientType.values()[id.ordinal()].getNameId()));
			startActivity(detailIntent);
		}
	}
	
	
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case android.R.id.home:
	            if (mTwoPane) {
	            	detailedView = false;
	            	hopDetailFragment = null;
	            	listFragment = new IngredientListFragment();
	    			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
	    			fragmentTransaction.replace(R.id.ingredient_list_container, listFragment);
	    			Bundle arguments = new Bundle();
					arguments.putInt(IngredientDetailFragment.ARG_ITEM_ID, IngredientType.HOPS.ordinal());
					
					fragment = new IngredientDetailFragment();
					fragment.setArguments(arguments);
					fragmentTransaction.replace(R.id.ingredient_detail_container, fragment);
	    			fragmentTransaction.commit();
	    			ActionBar actionBar = getActionBar();
	    			actionBar.setDisplayHomeAsUpEnabled(false);
	    			clickViewActvated = false;
	            	return true;
	            }
	        case R.id.settings_menu:
	        	Intent settingsIntent = new Intent(this, SettingsActivity.class);
	        	startActivity(settingsIntent);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return true;
	}

	@Override
	public void onHopSelected(Hop hop) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(HopDetailFragment.ARG_ITEM_ID, hop.getName());
			if (hopDetailFragment == null) {
				hopDetailFragment = new HopDetailFragment();
				hopDetailFragment.setArguments(arguments);
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.ingredient_detail_container, hopDetailFragment);
				if (!detailedView) {
					arguments = new Bundle();
					arguments.putInt(IngredientDetailFragment.ARG_ITEM_ID, IngredientType.HOPS.ordinal());
					arguments.putString(IngredientDetailFragment.SELECTION_ID, hop.getName());
					if (this.fragment != null) {
						ListView currentView = this.fragment.getListView();
						int position = currentView.getFirstVisiblePosition();
						int scroll = currentView.getChildAt(0).getTop();
						arguments.putInt(IngredientDetailFragment.SCROLL_Y, scroll);
						arguments.putInt(IngredientDetailFragment.SCROLL_POSITION, position);
					}
					fragment = new IngredientDetailFragment();
					fragment.setArguments(arguments);
					fragmentTransaction.replace(R.id.ingredient_list_container, fragment);
					detailedView = true;
				}
				fragmentTransaction.commit();
			} else {
				hopDetailFragment.setHop(hop);
			}
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		} else {
			//TODO: implement
		}
		
	}

	public HopRepository getHopRepository() {
		if (!mBound)
			throw new RuntimeException("Ingredient service is not bound");
		return ingredientService.getHopRepository();
		
	}
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
        	IngredientService.LocalBinder binder = (IngredientService.LocalBinder) service;
            ingredientService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
	private HopDetailFragment hopDetailFragment;
	private SugarDetailFragment sugarDetailFragment;

	@Override
	public void onSugarSelected(Sugar sugar) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(SugarDetailFragment.ARG_ITEM_ID, sugar.getSugarKey().toString());
			if (sugarDetailFragment == null) {
				sugarDetailFragment = new SugarDetailFragment();
				sugarDetailFragment.setArguments(arguments);
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.ingredient_detail_container, sugarDetailFragment);
				if (!detailedView) {
					arguments = new Bundle();
					arguments.putInt(IngredientDetailFragment.ARG_ITEM_ID, IngredientType.SUGARS.ordinal());
					arguments.putString(IngredientDetailFragment.SELECTION_ID, sugar.getSugarKey().toString());
					if (this.fragment != null) {
						ListView currentView = this.fragment.getListView();
						int position = currentView.getFirstVisiblePosition();
						int scroll = currentView.getChildAt(0).getTop();
						arguments.putInt(IngredientDetailFragment.SCROLL_Y, scroll);
						arguments.putInt(IngredientDetailFragment.SCROLL_POSITION, position);
					}
					fragment = new IngredientDetailFragment();
					fragment.setArguments(arguments);
					fragmentTransaction.replace(R.id.ingredient_list_container, fragment);
					detailedView = true;
				}
				fragmentTransaction.commit();
			} else {
				sugarDetailFragment.setSugar(sugar);
			}
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		} else {
			//TODO: implement
		}
		
	}
	
}
