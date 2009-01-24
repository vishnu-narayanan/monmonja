package com.almondmendoza.talkingCaller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.tts.TTS;

class TtsSpeaker {
	
	private static TTS tts;
	private static int SPEECH_RATE;
	private static Context context;
	private static String lang;
	private String wordToSpeeak;
	private SharedPreferences prefs;

	public static int DEFAULT_SPEECH_RATE = 190;
	//http://espeak.sourceforge.net/commands.html
	public static int SLOWEST_SPEECH_RATE = 80;
	public static int FASTEST_SPEECH_RATE = 390;
	
	public TtsSpeaker(Context contextArg, String str) {
		context = contextArg;
		wordToSpeeak = str;
		if(tts == null){
			tts = new TTS(context,ttsInitListener,true);
			this.getPreferences();
		}else{
			Log.d("TTSSpeaker",String.valueOf(NormalPreferences.cameFromNormalPref));
			if(NormalPreferences.cameFromNormalPref == true)
				this.getPreferences();
			tts.setSpeechRate(SPEECH_RATE);
			tts.speak(wordToSpeeak, 0, null);
		}
		NormalPreferences.cameFromNormalPref = false;
	}
	
	public TtsSpeaker(Context context, String str, int speechRate) {
		SPEECH_RATE = speechRate;
		new TtsSpeaker(context,str);
	}
	
	public TtsSpeaker(Context context, int rTitle) {
		new TtsSpeaker(context,context.getString(rTitle));
	}
	
	public TtsSpeaker(Context context, int rTitle, int speechRate) {
		SPEECH_RATE = speechRate;
		new TtsSpeaker(context,context.getString(rTitle),speechRate);
	}
	
	private void getPreferences(){
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		lang = (prefs.getString("lang_pref", "en-us"));
		Log.d("TTSSpeaker",lang);
		tts.setLanguage(lang);
	}

	

	private TTS.InitListener ttsInitListener = new TTS.InitListener() {
        public void onInit(int version) {
        	tts.setSpeechRate(SPEECH_RATE);
        	tts.speak(wordToSpeeak, 0, null);
        }
      };
}