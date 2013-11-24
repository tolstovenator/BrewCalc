package com.tolstovenator.brewcalc;

import com.tolstovenator.brewcalc.repository.IngredientService;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup.OnCheckedChangeListener;

public abstract class AbstractDetailsFragment extends Fragment implements OnFocusChangeListener, OnCheckedChangeListener, android.widget.CompoundButton.OnCheckedChangeListener{
	protected IngredientService ingredientService;
	protected boolean mBound = false;
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	protected Menu menu;
	private boolean changes = false;
	protected boolean resetForm = true;

	protected Double readDouble(View v) {
		return Double.valueOf(((EditText)v).getText().toString().trim());
	}
	
	protected String readString(View v) {
		return ((EditText)v).getText().toString().trim();
	}

	protected void resetSaveMenu() {
		changes = false;
		
		
		if (menu != null) {
			MenuItem menuItem = menu.findItem(R.id.save_menu);
			menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			menuItem.setEnabled(false);
		}
	}

	protected void markChanged() {
		changes = true;
		MenuItem menuItem = menu.findItem(R.id.save_menu);
		menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		menuItem.setEnabled(true);
	}
}
