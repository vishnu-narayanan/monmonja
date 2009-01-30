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

public class CallConfirm extends Activity implements View.OnClickListener {
    /** Called when the activity is first created. */
	private Button callBtn;
	private Button cancelBtn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.call_confirm);
        this.callBtn = (Button) this.findViewById(R.id.callBtn);;
        this.cancelBtn = (Button) this.findViewById(R.id.cancelCallBtn);
        this.callBtn.setOnClickListener(this);
        this.cancelBtn.setOnClickListener(this);
    }

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == this.callBtn){
			setResult(RESULT_OK);
			finish();
		}else{
			setResult(RESULT_CANCELED);
			finish();
		}
	}
}