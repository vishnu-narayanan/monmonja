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

public class Instruction extends Activity implements View.OnClickListener {
	private Button backInstruction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.instruction);
		this.backInstruction = (Button) this.findViewById(R.id.backInstructionBtn);
		this.backInstruction.setOnClickListener(this);
		TtsSpeaker.speak(getString(R.string.instructions_speech));
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == this.backInstruction){
			this.setResult(Activity.RESULT_CANCELED);
			this.finish();
		}
	}
}
