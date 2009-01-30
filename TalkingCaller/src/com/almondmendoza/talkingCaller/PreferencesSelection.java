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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreferencesSelection extends Activity implements View.OnClickListener {
	private Button psYesBtn;
	private Button psNoBtn;
	public static int NORMAL_PREFERENCES = 9;
	public static final String PREFS_NAME = "MyPrefsFile";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.preferences_selection);
		
		psYesBtn = (Button) this.findViewById(R.id.psYesBtn);
		psYesBtn.setOnClickListener(this);
		psNoBtn = (Button) this.findViewById(R.id.psNoBtn);
		psNoBtn.setOnClickListener(this);
		
		TtsSpeaker.speak(R.string.open_preferences);
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == this.psYesBtn){
			this.setResult(RESULT_OK);
			finish();
		}else if(view == this.psNoBtn){
			this.setResult(NORMAL_PREFERENCES);
			finish();
		}
	}
}
