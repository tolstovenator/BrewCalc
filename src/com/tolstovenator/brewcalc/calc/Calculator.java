package com.tolstovenator.brewcalc.calc;

import com.tolstovenator.brewcalc.R;

public enum Calculator {
	ALCOHOL(R.string.calcAlcohol, R.drawable.ic_launcher, "AlcoholCalculator"),
	BOILOFF(R.string.calcBoiloff, R.drawable.ic_launcher, "BoilOffCalculator"),
	CARBONATION(R.string.calcCarbonation, R.drawable.ic_launcher, "CarbonationCalculator");
	
	
	Calculator(int resourceId, int imageId, String className) {
		this.resourceId = resourceId;
		this.imageId = imageId;
		this.fragmentClassName = "com.tolstovenator.brewcalc.calc." + className + "Fragment";
		this.activityClassName = "com.tolstovenator.brewcalc.calc." + className + "Activity";
	}
	
	private int resourceId;
	private int imageId;
	private String fragmentClassName;
	private String activityClassName;
	
	public int getResourceId() {
		return resourceId;
	}
	
	public int getImageId() {
		return imageId;
	}
	
	public String getFragmentClassName() {
		return fragmentClassName;
	}
	
	public String getActivityClassName() {
		return fragmentClassName;
	}
}
