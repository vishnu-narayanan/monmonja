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
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SpeedSettings extends Activity implements View.OnClickListener {
	private Button speedUpBtn;
	private Button speedDownBtn;
	private Button speedOkBtn;
	private Button speedCancelBtn;
	private  int speechRate ;
	public static final String PREFS_NAME = "MyPrefsFile";
	private int originalSpeechRate;
	private String[] speed_entries;
	private int currentIndex;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.speed_preferences);
		speedUpBtn = (Button) this.findViewById(R.id.speedUpBtn);
		speedUpBtn.setOnClickListener(this);
		speedDownBtn = (Button) this.findViewById(R.id.speedDownBtn);
		speedDownBtn.setOnClickListener(this);
		speedOkBtn = (Button) this.findViewById(R.id.speedOkBtn);
		speedOkBtn.setOnClickListener(this);
		speedCancelBtn = (Button) this.findViewById(R.id.speedCancelBtn);
		speedCancelBtn.setOnClickListener(this);
	
		setUpSpeedSettings();
		TtsSpeaker.speak(R.string.speed_setting_special);
		
	}
	
	
	private void setUpSpeedSettings(){
		speed_entries = this.getResources().getStringArray(R.array.speed_entryvalues);
		
		SharedPreferences settings = getSharedPreferences(Preferences.PREFERENCES_NAME, 0);
		speechRate = settings.getInt("speechRate", TtsSpeaker.DEFAULT_SPEECH_RATE);
		originalSpeechRate = speechRate;
		
		for(int i =0;i < speed_entries.length;i++){
			if(speed_entries[i].equals(String.valueOf(speechRate))){
				this.currentIndex = i;
			}
		}
	}
	
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == this.speedUpBtn){
			this.currentIndex--;
			if(this.currentIndex < 0){
				this.currentIndex = 0;
				TtsSpeaker.setWordToSpeak(R.string.fastest_testing_phrase);
			}else{
				TtsSpeaker.setWordToSpeak(R.string.testing_speed_up_phrase);
			}
			TtsSpeaker.setSpeed(Integer.valueOf(speed_entries[this.currentIndex]));
		}else if(view == this.speedDownBtn){
			this.currentIndex++;
			if(this.currentIndex >= speed_entries.length){
				this.currentIndex =  speed_entries.length - 1;
				TtsSpeaker.setWordToSpeak(R.string.slowest_testing_phrase);
			}else{
				TtsSpeaker.setWordToSpeak(R.string.testing_speed_down_phrase);
			}
			
			TtsSpeaker.setSpeed(Integer.valueOf(speed_entries[this.currentIndex]));
		}else if(view == this.speedOkBtn){
			savePreferences();
		    setResult(RESULT_OK);
			finish();
		}else{
			TtsSpeaker.setSpeed(originalSpeechRate);
		    setResult(RESULT_CANCELED);
			finish();
		}
	}
	
	private void savePreferences(){
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor editor = settings.edit();
	    editor.putString(Preferences.PREF_LANGUAGE_SPEECH_NAME, String.valueOf(speechRate));
	    editor.commit();
	}

}
