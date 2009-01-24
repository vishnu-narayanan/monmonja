package com.almondmendoza.talkingCaller;

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