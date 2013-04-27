package com.tolstovenator.brewcalc.ui.ingredients;

import com.tolstovenator.brewcalc.R;

public enum IngredientType {
	
	HOPS(R.string.hops),
	SUGARS(R.string.sugars),
	YEAST(R.string.yeast),
	WATER(R.string.water);
	
	
	IngredientType(int nameId) {
		this.nameId = nameId;
	}
	
	public int getNameId() {
		return nameId;
	}
	
	int nameId;
	

}
