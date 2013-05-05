package com.tolstovenator.brewcalc.ui.ingredients;

import com.tolstovenator.brewcalc.R;

public enum IngredientType {
	
	HOPS(R.string.hops, R.drawable.hop_cone),
	SUGARS(R.string.sugars, R.drawable.ic_launcher),
	YEAST(R.string.yeast, R.drawable.ic_launcher),
	WATER(R.string.water, R.drawable.ic_launcher);
	
	
	IngredientType(int nameId, int imageId) {
		this.nameId = nameId;
		this.imageId = imageId;
	}
	
	public int getNameId() {
		return nameId;
	}
	
	public int getImageId() {
		return imageId;
	}
	
	int nameId;
	int imageId;
	

}
