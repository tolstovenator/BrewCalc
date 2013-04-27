package com.tolstovenator.brewcalc;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;

public abstract class AbstractActionBarActivity extends FragmentActivity implements ActionBar.OnNavigationListener{
	
	protected void createCommonMenu() {
		final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[] {
                                getString(R.string.title_ingredients),
                                getString(R.string.title_calcs),
                                getString(R.string.title_recipies),
                        }),
                this);
	}
	
	@Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
//    	Fragment fragment;
//    	if (position == 0) {
//    		fragment = new HopListFragment();
//    		
//    	}
//    	else {
//	        fragment = new DummySectionFragment();
//	        Bundle args = new Bundle();
//	        args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//	        fragment.setArguments(args);
//	        
//    	}
//    	getSupportFragmentManager().beginTransaction()
//        .replace(R.id.container, fragment)
//        .commit();
        return true;
    }

}
