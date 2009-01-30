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
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.tts.TTS;

class TtsSpeaker {
	
	private static TTS tts;
	private static int speechRate;
	private static Context context;
	private static String lang;
	private static String wordToSpeak;
	public static Boolean initAgain = true;
	
	private static String LOG = "TtsSpeaker";
	
	public static int DEFAULT_SPEECH_RATE = 190;
	
	public TtsSpeaker(){
		TtsSpeaker.wordToSpeak = "";
		TtsSpeaker.tts = new TTS(TtsSpeaker.context, TtsSpeaker.ttsInitListener,true);
	}
	
	public static void speak(String wordToSpeak){
		TtsSpeaker.wordToSpeak = wordToSpeak;
		if(TtsSpeaker.tts == null || TtsSpeaker.initAgain){
			TtsSpeaker.initAgain = false;
			TtsSpeaker.tts = new TTS(TtsSpeaker.context, TtsSpeaker.ttsInitListener,true);
		}else{
			TtsSpeaker.tts.speak(TtsSpeaker.wordToSpeak, 0, null);
		}
	}
	
	public static void speak(String wordToSpeak,Context context){
		TtsSpeaker.context = context;
		
		TtsSpeaker.speak(TtsSpeaker.wordToSpeak);
	}
	
	public static void speak(int wordToSpeak){
		TtsSpeaker.speak( TtsSpeaker.context.getString(wordToSpeak));
	}
	
	public static void setContext(Context context){
		TtsSpeaker.context = context;
	}
	
	public static void setSpeed(int speechRate){
		TtsSpeaker.speechRate = speechRate;
		TtsSpeaker.initAgain = true;
		TtsSpeaker.tts = new TTS(TtsSpeaker.context, TtsSpeaker.ttsInitListener,true);
	}
	
	public static void setWordToSpeak(String wordToSpeak){
		TtsSpeaker.wordToSpeak = wordToSpeak;
	}
	
	public static void setWordToSpeak(int wordToSpeak){
		TtsSpeaker.wordToSpeak = TtsSpeaker.context.getString(wordToSpeak);
	}
	
	public static void setLanguage(String lang){
		TtsSpeaker.tts.setLanguage(lang);
	}
	
	private static void getPreferences(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(TtsSpeaker.context);
		lang =  (prefs.getString(Preferences.PREF_LANGUAGE_SPEECH_NAME, Preferences.DEFAULT_SPEECH_LANGUAGE));
		TtsSpeaker.tts.setLanguage(lang);
	}

	

	private static TTS.InitListener ttsInitListener = new TTS.InitListener() {
        public void onInit(int version) {
        	Log.d(LOG,"version" + String.valueOf(version));
        	if(TtsSpeaker.initAgain){
        		TtsSpeaker.tts.setSpeechRate(TtsSpeaker.speechRate);
        		getPreferences();
        	}
        	
        	TtsSpeaker.tts.speak(TtsSpeaker.wordToSpeak, 0, null);
        	TtsSpeaker.initAgain = false;
        }
      };
}