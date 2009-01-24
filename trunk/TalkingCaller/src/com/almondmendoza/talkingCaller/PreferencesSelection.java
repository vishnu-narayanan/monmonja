package com.almondmendoza.talkingCaller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class PreferencesSelection extends Activity implements View.OnClickListener {
	private Button psYesBtn;
	private Button psNoBtn;
	private  int speechRate  = TtsSpeaker.DEFAULT_SPEECH_RATE;
	public static final String PREFS_NAME = "MyPrefsFile";
	private int originalSpeechRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.preferences_selection);
		
		psYesBtn = (Button) this.findViewById(R.id.psYesBtn);
		psYesBtn.setOnClickListener(this);
		psNoBtn = (Button) this.findViewById(R.id.psNoBtn);
		psNoBtn.setOnClickListener(this);
		
		SharedPreferences settings = getSharedPreferences(PreferencesSelection.PREFS_NAME, 0);
		speechRate = settings.getInt("speechRate", TtsSpeaker.DEFAULT_SPEECH_RATE);
		originalSpeechRate = speechRate;
		new TtsSpeaker(this,R.string.open_preferences);
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == this.psYesBtn){
			this.setResult(RESULT_OK);
			finish();
		}else if(view == this.psNoBtn){
			this.setResult(RESULT_CANCELED);
			finish();
		}
	}
	
	private void savePreferences(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
	    editor.putInt("speechRate", speechRate);
	    // Don't forget to commit your edits!!!
	    editor.commit();
	    
	    setResult(RESULT_OK);
		finish();
	}

}
