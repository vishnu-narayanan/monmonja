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
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

public class Preferences {
	public static final String PREFERENCES_NAME = "TalkingCallerPrefs";
	
	public static final String PREF_LANGUAGE_WORDS_NAME = "words_lang_pref";
	public static final String PREF_LANGUAGE_SPEECH_NAME = "speech_lang_pref";
	public static final String PREF_SPEECH_RATE = "rate_pref";
	public static final String DEFAULT_SPEECH_LANGUAGE = "en-us"; 
	
	public static void loadLanguageSettings(Context context){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		String lang = (prefs.getString(Preferences.PREF_LANGUAGE_WORDS_NAME, Preferences.DEFAULT_SPEECH_LANGUAGE));
		
		Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
	}
}
