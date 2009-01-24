package com.almondmendoza.talkingCaller;

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
		
		new TtsSpeaker(this,R.string.instructions_speech);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v ==this.backInstruction){
			this.setResult(Activity.RESULT_CANCELED);
			this.finish();
		}
	}
}
