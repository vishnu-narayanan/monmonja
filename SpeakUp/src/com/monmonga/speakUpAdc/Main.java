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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParserException;

import android.R.color;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import com.monmonga.speakUpAdc.R;
import com.monmonga.speakUpAdc.db.WordsBase;

public class Main extends Activity implements View.OnClickListener {
	
	private Button btnEasyGame;
	private Button btnMediumGame;
	private Button btnHardGame;
	private Dialog firstRunDialog;
	
	private class FirstRunInsertTask extends AsyncTask<Intent,Void,Integer> {
		private Context mContext;	
		
		public FirstRunInsertTask(Context context) {
	        mContext = context;
	    }
		
		@Override
		protected Integer doInBackground(Intent... arg0) {
			// TODO Auto-generated method stub
			firstRun();
			return 1;
		}
		
		@Override
        protected void onPostExecute(Integer parsed) {
			enableInstruction();
        }

	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
        
        SharedPreferences app_preference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		if(app_preference.getBoolean("isFirstRun",true) == true){
	        Editor ed = app_preference.edit();
	        ed.putBoolean("isFirstRun", false);
	        ed.commit();
	        showFirstRunDialog(true);
	        new FirstRunInsertTask(getApplicationContext()).execute(this.getIntent());
		}
		
        btnEasyGame = (Button) findViewById(R.id.btnEasyGame);
        btnEasyGame.setOnClickListener(this);
        btnEasyGame.setBackgroundColor(color.background_light);
        btnEasyGame.setEnabled(true);
        btnMediumGame = (Button) findViewById(R.id.btnMediumGame);
        btnMediumGame.setOnClickListener(this);
        btnMediumGame.setBackgroundColor(color.background_light);
        btnMediumGame.setEnabled(true);
        btnHardGame = (Button) findViewById(R.id.btnHardGame);
        btnHardGame.setOnClickListener(this);
        btnHardGame.setBackgroundColor(color.background_light);
        btnHardGame.setEnabled(true);
    }    

	//@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(getBaseContext(), R.string.form_the_words_start, Toast.LENGTH_SHORT).show();
		Intent gameIntent = new Intent(this,SpeechGame.class);
		
		if(v == btnEasyGame){
			gameIntent.putExtra("level", "easy");
			startActivity(gameIntent);
		}else if(v == btnMediumGame){
			gameIntent.putExtra("level", "medium");
			startActivity(gameIntent);
		}else if(v == btnHardGame){
			gameIntent.putExtra("level", "hard");
			startActivity(gameIntent);
		}
	}
	
	@Override
 	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add(0,R.string.preferences,0,R.string.preferences).setIcon(R.drawable.settings);
		menu.add(0,R.string.instructions_title,0,R.string.instructions_title).setIcon(R.drawable.instructions);
		menu.add(0,R.string.about,0,R.string.about).setIcon(R.drawable.about);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.string.preferences:
				startActivity(new Intent(this,PrefsActivity.class));
				return (true);
			case R.string.instructions_title:
				showFirstRunDialog(false);
				enableInstruction();
				return true;
			case R.string.about:
				String urlAbout = "http://monmonja.com/android/SpeakUp.php";
                Intent aboutIntent = new Intent(Intent.ACTION_VIEW);
                aboutIntent.setData(Uri.parse(urlAbout));
                startActivity(aboutIntent);
				return true;
			
		}
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
	
	private void enableInstruction(){
		Button speechFirstButton = (Button) firstRunDialog.findViewById(R.id.btnFirstRunOkay);
		speechFirstButton.setEnabled(true);
	}
	
	private void showFirstRunDialog(Boolean realFirstRun){
		firstRunDialog = new Dialog(this);

		firstRunDialog.setContentView(R.layout.first_run_app);
		firstRunDialog.setTitle(R.string.instructions_title);
		
		Button speechFirstButton = (Button) firstRunDialog.findViewById(R.id.btnFirstRunOkay);
		speechFirstButton.setEnabled(false);
		speechFirstButton.setOnClickListener(new View.OnClickListener()    {
	         public void onClick(View v)  { 
	        	 firstRunDialog.dismiss();
	         }
		});
		
		TextView txtFirstRunInstruction = (TextView) firstRunDialog.findViewById(R.id.txtFirstRunInstruction);
		if(realFirstRun){
			txtFirstRunInstruction.setText(getString(R.string.first_run_insert_notification) + getString(R.string.instructions_main));
		}else{
			txtFirstRunInstruction.setText(R.string.instructions_main);			
		}
		
		firstRunDialog.show();
	}
	
	private void firstRun(){
		managedQuery(WordsBase.Words.CONTENT_URI, null,null,null,null);
	}
	
}
