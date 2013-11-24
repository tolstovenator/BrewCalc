package com.tolstovenator.brewcalc.preferences;

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
}
