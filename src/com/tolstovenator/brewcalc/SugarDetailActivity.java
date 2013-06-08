package com.tolstovenator.brewcalc;

import com.tolstovenator.brewcalc.ui.ingredients.IngredientType;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class SugarDetailActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sugar_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(getIntent().getStringExtra(
				SugarDetailFragment.ARG_ITEM_ID));
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(
					SugarDetailFragment.ARG_ITEM_ID,
					getIntent().getStringExtra(
							SugarDetailFragment.ARG_ITEM_ID));
			SugarDetailFragment fragment = new SugarDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.sugar_detail_container, fragment).commit();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			Intent up = new Intent(this,
					IngredientDetailActivity.class);
			up.putExtra(IngredientDetailFragment.ARG_ITEM_ID, getString(IngredientType.SUGARS.getNameId()));
			NavUtils.navigateUpTo(this, up);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
