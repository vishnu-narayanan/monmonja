package com.monmonga.speakUpAdc;
/*
Copyright (C) 2009 Momonja, http://www.almondmendoza.com/

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

import java.util.ArrayList;
import java.util.Iterator;

import android.R.color;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.monmonga.speakUpAdc.db.WordsBase;
import com.monmonga.speakUpAdc.db.WordsProvider;

public class SpeechGame extends Activity implements View.OnClickListener,TextToSpeech.OnInitListener {
	private static final int VOICE_RECOGNITION_INTENT = 1234;
	private Button btnStartSaying;
	private ListView listWordToSay;
	private int numberOfWords;
	private int currentIndex;
	 
	private ArrayList<ArrayList<String>> answers;
	private String wordToSay;
	private Cursor  c;
	
	private TextToSpeech myTts;
	private Boolean ttsHasInit;
	
	private class SpeechWordsAdapter extends CursorAdapter{
		Context mContext;
		private final LayoutInflater mInflater;
		
		public SpeechWordsAdapter(Context context, Cursor c) {
			super(context, c);
			mContext = context;
			mInflater = LayoutInflater.from(context);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void bindView(View view, Context arg1, Cursor cursor) {
			// TODO Auto-generated method stub
			TextView t = (TextView) view.findViewById(R.id.speechListWordFrom);
	        t.setText(cursor.getString(cursor.getColumnIndex(WordsBase.Words.WORD)));
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// TODO Auto-generated method stub
			final View view = mInflater.inflate(R.layout.speech_list, parent, false);
			return view;
		}
		
	}
	
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.speech);
        
        SharedPreferences app_preference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		if(app_preference.getBoolean("isFirstRunSpeech",true) == true){
	        Editor ed = app_preference.edit();
	        ed.putBoolean("isFirstRunSpeech", false);
	        ed.commit();
	        showFirstRunDialog();
		}
		
        answers =  new ArrayList<ArrayList<String>> ();
        
		
        numberOfWords = Integer.parseInt(app_preference.getString(PrefsActivity.NUMBER_OF_WORDS,"5"));
        currentIndex = 0;
        WordsProvider.LIMIT = numberOfWords;
        
        btnStartSaying = (Button) findViewById(R.id.btnStartSaying);
        btnStartSaying.setBackgroundColor(color.background_light);
        btnStartSaying.setOnClickListener(this);
        
        String level = "easy";
        Bundle extras = getIntent().getExtras(); 
        if(extras !=null)  {
        	level= extras.getString("level");
        }
        
        c = managedQuery(WordsBase.Words.CONTENT_URI, null,
    			WordsBase.Words.DIFFICULTY + " = ?",
    			new String[]{  level },
    			"RANDOM()"
    		);
        listWordToSay = (ListView) findViewById(R.id.listWordToSay);
        listWordToSay.setOnItemClickListener(mListener);
        
        listWordToSay.setAdapter(new SpeechWordsAdapter(this,c));
        listWordToSay.setSelected(true);
        listWordToSay.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        
        ttsHasInit = false;
        myTts = new TextToSpeech(this, this);
    }
    
    private AdapterView.OnItemClickListener mListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
        	if(ttsHasInit){
        		LinearLayout ll = (LinearLayout)v;
        		TextView tx = (TextView)ll.findViewById(R.id.speechListWordFrom);
        	      myTts.speak(tx.getText().toString(),  TextToSpeech.QUEUE_FLUSH, null);
        	}else{
        		Toast.makeText(getBaseContext(), "Still initializing the speech, please try again shortly", Toast.LENGTH_SHORT).show();
        	}
        }
    };
    
	private void startVoiceRecognitionActivity() {
		c.moveToPosition(currentIndex);
        wordToSay = c.getString(c.getColumnIndex(WordsBase.Words.WORD));
        String pleaseSay = getString(R.string.pleaseSay,wordToSay);
        
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, pleaseSay);
        startActivityForResult(intent, VOICE_RECOGNITION_INTENT);
    }
	
	

      @Override
   	public boolean onPrepareOptionsMenu(Menu menu) {
  		menu.clear();
  		menu.add(0,R.string.instructions_title,0,R.string.instructions_title).setIcon(R.drawable.instructions);
  		return super.onPrepareOptionsMenu(menu);
  	}
  	
  	@Override
  	public boolean onOptionsItemSelected(MenuItem item) {
  		switch (item.getItemId()) {
  			case R.string.instructions_title:
  				showFirstRunDialog();
  				return true;  			
  		}
  		// TODO Auto-generated method stub
  		return super.onOptionsItemSelected(item);
  	}
  	
//	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnStartSaying){
			startVoiceRecognitionActivity();
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_INTENT && resultCode == RESULT_OK) {
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			
			answers.add(matches);
			currentIndex++;
			if(currentIndex < numberOfWords){
				startVoiceRecognitionActivity();
			}else{
				gameOver();
			}
		}
	}
	
	private void gameOver(){
		Intent resultIntent = new Intent(this,ResultsActivity.class);
		int i = 0;
		for (Iterator<ArrayList<String>> it = answers.iterator(); it.hasNext ();i++) {
			ArrayList<String> answer = it.next();
			resultIntent.putExtra("answer" + i, answer);
			
		}
		
		if(c.moveToFirst()){
			int j = 0;
			do{
				resultIntent.putExtra("word" + j, c.getString(c.getColumnIndex(WordsBase.Words.WORD)));
				j++;
			}while(c.moveToNext());
		}
		startActivity(resultIntent);
		finish();
	}
	
	private void showFirstRunDialog(){
		final Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.first_run_speech);
		dialog.setTitle(R.string.instructions_title);
		
		Button speechFirstButton = (Button) dialog.findViewById(R.id.btnFirstRunOkay);
		speechFirstButton.setOnClickListener(new View.OnClickListener()    {
	         public void onClick(View v)  { 
	        	 dialog.dismiss();
	         }
		});
		
		TextView txtFirstRunInstruction = (TextView) dialog.findViewById(R.id.txtFirstRunInstruction);
		txtFirstRunInstruction.setText(R.string.instructions);
		dialog.show();
	}

	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		ttsHasInit = true;
    	Toast.makeText(getBaseContext(), "Speech initialized, tap the words to hear how its pronounced", Toast.LENGTH_SHORT).show();
	}
}
