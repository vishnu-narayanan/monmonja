package com.almondmendoza.monBattery;
/* <!-- 
* Copyright (C) 2009 AlmondMendoza
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
-->
*/

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class PrefsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
	public static final String PREFS_NAME = "com.almondmendoza.monBattery";

	private ListPreference languagePref;
	private CheckBoxPreference noticationPref;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.prefs);
		
		languagePref = (ListPreference) findPreference(getResources().getString(R.string.pref_key_language));
		languagePref.setOnPreferenceChangeListener(this);
		
		noticationPref = (CheckBoxPreference) findPreference(getResources().getString(R.string.pref_key_notification));
		noticationPref.setOnPreferenceChangeListener(this);
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setAppLanguage(this);
	}


	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		if(preference.getKey().equals(getResources().getString(R.string.pref_key_language))){
			setAppLanguage(getBaseContext(),newValue.toString());
			stopService(new Intent(this, BatteryService.class));
			connectToBatteryService(getBaseContext());
		}else if(preference.getKey().equals(getResources().getString(R.string.pref_key_notification))){
			connectToBatteryService(getBaseContext(),((Boolean) newValue));				
		}
		return true;
	}
	
	
	/* application specific */
	public static void setAppLanguage(Context context){
		SharedPreferences app_preference = PreferenceManager.getDefaultSharedPreferences(context);
		setAppLanguage(context,app_preference.getString(context.getResources().getString(R.string.pref_key_language), "en-US"));
	}
	
	public static void setAppLanguage(Context context,String languageToLoad){
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
	}
	
	
	public static void connectToBatteryService(Context context) {	 
		SharedPreferences app_preference = PreferenceManager.getDefaultSharedPreferences(context);
		connectToBatteryService(context,app_preference.getBoolean(context.getResources().getString(R.string.pref_key_notification),true));
    } 
	
	public static void connectToBatteryService(Context context,Boolean startTheService) {
		if(startTheService){
			context.startService(new Intent(context, BatteryService.class));
		}else{
			context.stopService(new Intent(context, BatteryService.class));
		}
    }
}
