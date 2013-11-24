package com.tolstovenator.brewcalc.preferences;

import com.tolstovenator.brewcalc.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements
	OnSharedPreferenceChangeListener{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String settings = getArguments().getString("settings");
        if ("efficiency".equals(settings)) {
            addPreferencesFromResource(R.xml.preferences_efficiency);
        } else if ("measurements".equals(settings)) {
            addPreferencesFromResource(R.xml.preferences_measurements);
            updateDefaultBatchSize();
        } else if ("calibration".equals(settings)) {
            addPreferencesFromResource(R.xml.preferences_calibration);
        } else if ("hops".equals(settings)) {
            addPreferencesFromResource(R.xml.preferences_hops);
        } else if ("colours".equals(settings)) {
            addPreferencesFromResource(R.xml.preferences_colour);
        }
        initSummaries(this.getPreferenceScreen());
        getPreferenceScreen().getSharedPreferences()
			.registerOnSharedPreferenceChangeListener(this);
    }
	
	void initSummaries(PreferenceGroup pg) {
		for (int i = 0; i < pg.getPreferenceCount(); ++i) {
			Preference p = pg.getPreference(i);
			if (p instanceof PreferenceGroup)
				initSummaries((PreferenceGroup) p); // recursion
			else
				setSummary(p);
		}
	}
	
	private void setSummary(Preference pref) {
		// react on type or key
		if (pref instanceof ListPreference) {
			ListPreference listPref = (ListPreference) pref;
			pref.setSummary(listPref.getEntry());
		} else if (pref instanceof EditTextPreference){
			EditTextPreference editTextPreference = (EditTextPreference) pref;
			pref.setSummary(editTextPreference.getText());
		}
	}

	/**
	 * used to change the summary of a preference
	 */
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		Preference pref = findPreference(key);
		this.setSummary(pref);
		if (key.equals("homepro_type") || key.equals("fluid_units")) {
			updateDefaultBatchSize();
		}
	}

	private void updateDefaultBatchSize() {
		Preference p = getPreferenceManager().findPreference("pref_batch_size");
		boolean pro = getPreferenceManager().getSharedPreferences().getBoolean("homepro_type", false);
		boolean metric = getPreferenceManager().getSharedPreferences().getBoolean("fluid_units", false);
		if (pro && metric) {
			p.setTitle(R.string.pref_batch_size_hl);
		} else if (pro) {
			p.setTitle(R.string.pref_batch_size_bbl);
		} else if (metric) {
			p.setTitle(R.string.pref_batch_size_L);
		} else {
			p.setTitle(R.string.pref_batch_size_gal);
		}
	}
	
}
