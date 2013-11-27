package com.tolstovenator.brewcalc.preferences;

import com.tolstovenator.brewcalc.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
	
	public enum ExtractPotential {
		SG,
		POINTS,
		PLATO,
		IOB_HWE,
		PERCENT_YIELD
	}
	
	public enum ColorMethod {
		SRM,
		EBC,
		EBC_NEW
	}
	
	public static boolean isProBrewer(Activity activity) {
		return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("homepro_type", false);
	}
	
	public static ExtractPotential getExtractPotential(Activity activity) {
		SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String value = defaultSharedPreferences.getString("pref_extract_potential", "0");
		Integer intValue = Integer.parseInt(value);
		return ExtractPotential.values()[intValue.intValue()];
	}
	
	public static ColorMethod getColorMethod(Activity activity) {
		return ColorMethod.SRM;
	}
	
	public static double getDefaultBatch(Activity activity) {
		try {
			String size = PreferenceManager.getDefaultSharedPreferences(activity).getString("pref_batch_size", "0");
			return Double.valueOf(size);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static boolean isAmericanLiquid(Activity activity) {
		return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("pref_fluid_units", false);
	}
	
	public static String getFluidUnits(Activity activity) {
		boolean pro = isProBrewer(activity);
		boolean american = isAmericanLiquid(activity);
		if (pro && american) return activity.getString(R.string.Barrels);
		if (!pro && american) return activity.getString(R.string.Gallons);
		if (pro) return activity.getString(R.string.Hectoliters);
		return activity.getString(R.string.Liters);
	}
}
