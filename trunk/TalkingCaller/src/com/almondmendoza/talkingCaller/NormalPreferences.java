package com.almondmendoza.talkingCaller;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;

public class NormalPreferences extends PreferenceActivity  {
	public static Boolean cameFromNormalPref = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		NormalPreferences.cameFromNormalPref = true;
		addPreferencesFromResource(R.layout.normal_preferences);
		
		Preference previewPref = findPreference("preview");
		previewPref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
	        public boolean onPreferenceClick(Preference preference) {
	        	listenToPreview();
	        	return true;
	        }
	      });
	}
	
	private void listenToPreview(){
		NormalPreferences.cameFromNormalPref = true;
		new TtsSpeaker(this,R.string.testing_phrase);
	}
}
