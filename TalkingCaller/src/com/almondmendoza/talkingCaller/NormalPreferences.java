package com.almondmendoza.talkingCaller;
/*
* Copyright (C) 2009 AlmondMendoza.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*
*	Creatd By: Almond Joseph Mendoza
*	Last Modified Date: Sunday 27, 2008
*
*/
import java.util.HashMap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;

public class NormalPreferences extends PreferenceActivity  {
	private HashMap<String,Integer> testingPhases;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.normal_preferences);
		
		Preference previewPref = findPreference("preview");
		previewPref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
	        public boolean onPreferenceClick(Preference preference) {
	        	listenToPreview();
	        	return true;
	        }
	      });
		this.setUpTestingPhase();
		TtsSpeaker.speak("");
	}
	
	private void setUpTestingPhase(){
		testingPhases = new HashMap<String, Integer>();
		testingPhases.put("en-us", R.string.testing_phrase_en_us);
		testingPhases.put("es-la", R.string.testing_phrase_tl);
	}
	
	@Override
	protected void onDestroy() {
		TtsSpeaker.initAgain = true;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String languageCode = prefs.getString(Preferences.PREF_LANGUAGE_SPEECH_NAME,  Preferences.DEFAULT_SPEECH_LANGUAGE);
		TtsSpeaker.setLanguage(languageCode);
		Preferences.loadLanguageSettings(getBaseContext());
		
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void listenToPreview(){
		TtsSpeaker.initAgain = true;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String languageCode = prefs.getString(Preferences.PREF_LANGUAGE_SPEECH_NAME, Preferences.DEFAULT_SPEECH_LANGUAGE);
		String speedSettings = prefs.getString(Preferences.PREF_SPEECH_RATE, "190");
		Log.d("TtsSpeaker",speedSettings);
		TtsSpeaker.setLanguage(languageCode);
		TtsSpeaker.setWordToSpeak(getString(testingPhases.get(languageCode)));
		TtsSpeaker.setSpeed(Integer.valueOf(speedSettings) );
	}
}