package com.tolstovenator.brewcalc.calc;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tolstovenator.brewcalc.AbstractActionBarActivity;
import com.tolstovenator.brewcalc.IngredientDetailActivity;
import com.tolstovenator.brewcalc.IngredientDetailFragment;
import com.tolstovenator.brewcalc.R;
import com.tolstovenator.brewcalc.preferences.SettingsActivity;

public class CalculatorsListActivity extends AbstractActionBarActivity implements CalculatorsListFragment.Callbacks {
	
	private boolean mTwoPane;
	private CalculatorsListFragment listFragment;
	private Fragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculators_list);
		
		if (findViewById(R.id.calculators_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;
			listFragment = new CalculatorsListFragment();
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.calculators_list_container, listFragment);
			fragmentTransaction.commit();

		}
		createCommonMenu();
		getActionBar().setSelectedNavigationItem(1);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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
	public void onItemSelected(Calculator id) {
		try {
			if (mTwoPane) {
				// In two-pane mode, show the detail view in this activity by
				// adding or replacing the detail fragment using a
				// fragment transaction.
				Bundle arguments = new Bundle();
				fragment = (Fragment)Class.forName(id.getFragmentClassName()).newInstance();
				fragment.setArguments(arguments);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.calculators_detail_container, fragment)
						.commit();
				
	
			} else {
				// In single-pane mode, simply start the detail activity
				// for the selected item ID.
				Intent detailIntent = new Intent(this,
						Class.forName(id.getActivityClassName()));
				startActivity(detailIntent);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

}
