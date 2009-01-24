package com.almondmendoza.talkingCaller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class Preferences extends Activity implements View.OnClickListener {
	private Button speedUpBtn;
	private Button speedDownBtn;
	private Button speedOkBtn;
	private Button speedCancelBtn;
	private  int speechRate  = TtsSpeaker.DEFAULT_SPEECH_RATE;
	public static final String PREFS_NAME = "MyPrefsFile";
	private int originalSpeechRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.prefs);
		speedUpBtn = (Button) this.findViewById(R.id.speedUpBtn);
		speedUpBtn.setOnClickListener(this);
		speedDownBtn = (Button) this.findViewById(R.id.speedDownBtn);
		speedDownBtn.setOnClickListener(this);
		speedOkBtn = (Button) this.findViewById(R.id.speedOkBtn);
		speedOkBtn.setOnClickListener(this);
		speedCancelBtn = (Button) this.findViewById(R.id.speedCancelBtn);
		speedCancelBtn.setOnClickListener(this);
		
		SharedPreferences settings = getSharedPreferences(PreferencesSelection.PREFS_NAME, 0);
		speechRate = settings.getInt("speechRate", TtsSpeaker.DEFAULT_SPEECH_RATE);
		originalSpeechRate = speechRate;
		new TtsSpeaker(this,R.string.open_preferences);
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == this.speedUpBtn){
			speechRate += 30;
			if(speechRate < TtsSpeaker.FASTEST_SPEECH_RATE){
				new TtsSpeaker(this,R.string.testing_speed_up_phrase,speechRate);
			}else{
				speechRate = TtsSpeaker.FASTEST_SPEECH_RATE;
				new TtsSpeaker(this,R.string.fastest_testing_phrase,speechRate);
			}
		}else if(view == this.speedDownBtn){
			speechRate -= 30;
			if(speechRate > TtsSpeaker.SLOWEST_SPEECH_RATE){
				new TtsSpeaker(this,R.string.testing_speed_down_phrase,speechRate);
			}else{
				speechRate = TtsSpeaker.SLOWEST_SPEECH_RATE;
				new TtsSpeaker(this,R.string.slowest_testing_phrase,speechRate);
			}
		}else if(view == this.speedOkBtn){
			savePreferences();
		}else{
			new TtsSpeaker(this,"",originalSpeechRate);
		    setResult(RESULT_CANCELED);
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
