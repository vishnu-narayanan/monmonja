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

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LanguageSelection extends Activity implements View.OnClickListener {
	private String[] entries;
	private String[] speechValues;
	private String[] wordValues;
	private int currentIndex = 0;
	private Button languageBtn;
	private Button languageNextBtn;
	private Button languageCancelBtn;
	
	private SharedPreferences prefs;
	private String originalLanguage;
	private HashMap<String,Integer> testingPhases;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.language_selection);
		
		
		entries = this.getResources().getStringArray(R.array.language_entries);
		speechValues =  this.getResources().getStringArray(R.array.speech_language_values);
		wordValues =  this.getResources().getStringArray(R.array.language_values);
		
		this.languageBtn = (Button) this.findViewById(R.id.languageBtn);
		this.languageBtn.setOnClickListener(this);
		this.languageCancelBtn = (Button) this.findViewById(R.id.languageCancelBtn);
		this.languageCancelBtn.setOnClickListener(this);
		this.languageNextBtn = (Button) this.findViewById(R.id.languageNextBtn);
		this.languageNextBtn.setOnClickListener(this);
		
		this.getDefaultLanguage();
		this.setUpTestingPhase();
		TtsSpeaker.speak(R.string.preferences_language_open_speech);
	}

	public void onClick(View v) {
		Log.d("TtsSpeaker",v.toString());
		// TODO Auto-generated method stub
		if(v == this.languageBtn){
			this.currentIndex++;
			if(this.currentIndex == this.entries.length){
				this.currentIndex = 0;
			}
			this.languageBtn.setText(this.entries[this.currentIndex]);
			
			
			TtsSpeaker.setLanguage(this.speechValues[this.currentIndex]);
			TtsSpeaker.speak(getString(testingPhases.get(this.speechValues[this.currentIndex])));
		}else if(v == this.languageCancelBtn){
			Log.d("TtsSpeaker",originalLanguage);
			TtsSpeaker.setLanguage(originalLanguage);
			setResult(RESULT_CANCELED);
			finish();
		}else if(v == this.languageNextBtn){
			saveLanguages();
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private void setUpTestingPhase(){
		testingPhases = new HashMap<String, Integer>();
		
		testingPhases.put("en-us", R.string.testing_phrase_en_us);
		testingPhases.put("es-la", R.string.testing_phrase_tl);
	}
	
	private void saveLanguages(){
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor editor = settings.edit();
		
	    editor.putString(Preferences.PREF_LANGUAGE_SPEECH_NAME, speechValues[this.currentIndex]);
	    editor.putString(Preferences.PREF_LANGUAGE_WORDS_NAME, wordValues[this.currentIndex]);
	    editor.commit();
	    
	    Preferences.loadLanguageSettings(getBaseContext());
	    TtsSpeaker.setLanguage(speechValues[this.currentIndex]);
	}
	
	private void getDefaultLanguage(){
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String lang = (prefs.getString(Preferences.PREF_LANGUAGE_SPEECH_NAME, "en-us"));
		originalLanguage = lang;
		
		for(int i =0;i < speechValues.length;i++){
			if(speechValues[i].equals(lang)){
				this.currentIndex = i;
				originalLanguage = this.speechValues[this.currentIndex];
			}
		}
		
		this.languageBtn.setText(this.entries[this.currentIndex]);
		
	}

}
