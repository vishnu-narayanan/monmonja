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
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Main extends ListActivity {
	protected static final int PREFERENCES_SELECTION = 1;
	protected static final int CALL_CONFIRM = 2;
	protected static final int INSTRUCTION_INTENT = 3;
	protected static final int LANGUAGE_PREFS = 4;
	protected static final int SPEED_SETTINGS = 5;
	
	private Cursor contactsCursor;
	private String idToCall = "";
	private String phoneToDial;
	private String phoneNameStr;
	
	private String[] projection = new String[] {
            People._ID, People.NAME, People.NUMBER
    };
	
	public static final String LOG = "TALKING_CALLER";

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	TtsSpeaker.setContext(getBaseContext());
	    Preferences.loadLanguageSettings(getBaseContext());
        super.onCreate(savedInstanceState);

        // GET THE CONTACT LIST WITH PHONE NUMBER AND NAME
        contactsCursor = this.managedQuery(People.CONTENT_URI,projection, 
        			People.NAME + "!= '' and " + People.NUMBER + "!= ''" , null, People.NAME + " ASC");
        startManagingCursor(contactsCursor);

        String[] columnsToMap = new String[] {People.NAME};
        int[] mapTo = new int[] {android.R.id.text1};
        
        ListAdapter mAdapter = new SimpleCursorAdapter(this,
                     android.R.layout.simple_list_item_1,
                     contactsCursor, columnsToMap, mapTo);
        this.setListAdapter(mAdapter);
        
        loadSettings();
        Log.d(Main.LOG,"Main activity starts");
    } 
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, R.string.settings, 0, R.string.settings).setIcon(R.drawable.settings48);
    	menu.add(0,R.string.instructions,0,R.string.instructions).setIcon(R.drawable.help48);
    	return super.onCreateOptionsMenu(menu);
    }
    
   

    @Override
	public boolean onMenuOpened(int featureId, Menu menu) {
    	TtsSpeaker.speak(R.string.speed_settings);
		// TODO Auto-generated method stub
		return super.onMenuOpened(featureId, menu);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
      Intent intent;
      switch (item.getItemId()) {
      	case R.string.settings:
      		intent = new Intent(this, PreferencesSelection.class);
      		startActivityForResult(intent,PREFERENCES_SELECTION);
        break;
      	case R.string.instructions:
      		intent = new Intent(this, Instruction.class);
      		startActivityForResult(intent,INSTRUCTION_INTENT);
      	break;
      }
      return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	Intent intent;
    	if (requestCode == PREFERENCES_SELECTION) {
    		if(resultCode == RESULT_OK){
    			intent = new Intent(this, LanguageSelection.class);
    			startActivityForResult(intent,LANGUAGE_PREFS);
    	  	}else if (resultCode == PreferencesSelection.NORMAL_PREFERENCES){
    	  		intent = new Intent(this, NormalPreferences.class);
    	  		startActivity(intent);
    	  	}
    	}else if(requestCode == CALL_CONFIRM){
    		if(resultCode == RESULT_OK){
    		  	String tapToCall = (String) getText(R.string.call_start);
    		  	tapToCall = String.format(tapToCall, phoneNameStr);
    		  	TtsSpeaker.speak(tapToCall);
    		  	// START THE CALL
    		  	Uri parsedPhoneNumber = Uri.parse("tel:"+phoneToDial); 
  	        	Intent i = new Intent(Intent.ACTION_CALL,parsedPhoneNumber);
  	        	startActivity(i);
    		}else{
    			TtsSpeaker.speak(R.string.call_cancel);
    		}
    	}else if(requestCode == INSTRUCTION_INTENT){
    		TtsSpeaker.speak(R.string.back_to_your_contact_list);
    	}else if(requestCode == LANGUAGE_PREFS){
    		if(resultCode == RESULT_OK){
    			intent = new Intent(this, SpeedSettings.class);
    			startActivityForResult(intent,SPEED_SETTINGS);
    		}else{
    			TtsSpeaker.speak(R.string.back_to_your_contact_list);
    		}
    	}else if(requestCode == SPEED_SETTINGS){
    		if(resultCode == RESULT_OK){
    			TtsSpeaker.speak(R.string.speed_setting_saved);
    		}else{
    			TtsSpeaker.speak(R.string.back_to_your_contact_list);
    		}
    	}
      
    }




	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	// GET THE ITEM THAT HAD BEEN TAPPED
    	Uri uri = ContentUris.withAppendedId(People.CONTENT_URI, id);
        Cursor c = managedQuery(uri, projection, null, null, null);
        c.moveToFirst();
        
        // SEE IF THE USER TAPS THE SAME CONTACT THE SAME TIME
        int phoneIdIndex = c.getColumnIndex(People._ID);
        int phoneNameIndex = c.getColumnIndex(People.NAME);
        int phoneNumberIndex = c.getColumnIndex(People.NUMBER);
    	phoneNameStr = c.getString(phoneNameIndex);
    	phoneToDial = c.getString(phoneNumberIndex);
    	
		if(idToCall.equals(c.getString(phoneIdIndex))){
        	// SPEAK WHICH CONTACT TO CALL
        	String tapToCall = (String) getText(R.string.tap_to_call);
        	tapToCall = String.format(tapToCall, phoneNameStr);
        	TtsSpeaker.speak(tapToCall);
        	
        	// START THE CONTACT  CONFIRMATION
        	Intent callConfirm = new Intent(this,CallConfirm.class);
        	startActivityForResult(callConfirm,CALL_CONFIRM);
        	
        	// CLEAR THE ID TO CALL
        	idToCall = "";
        }else{
        	// SPEAK THE CONTACT
        	TtsSpeaker.speak(phoneNameStr);        	
        	idToCall = c.getString(phoneIdIndex);
        }
        // TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
	
	// NON OVERRIDE FUNCTION 
	 private void loadSettings(){
    	SharedPreferences settings = getSharedPreferences(Preferences.PREFERENCES_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		int runTimes = settings.getInt("runTimes",0);
        
		// SPEAK DIFFERENT WELCOME NOTE AFTER INSTALLING THE APP
		if(runTimes == 0){
			TtsSpeaker.setWordToSpeak(R.string.welcome_note_with_instruction);
			TtsSpeaker.setSpeed(settings.getInt(Preferences.PREF_SPEECH_RATE, TtsSpeaker.DEFAULT_SPEECH_RATE));
		}else{
			TtsSpeaker.setWordToSpeak(R.string.welcome_note);
			TtsSpeaker.setSpeed(settings.getInt(Preferences.PREF_SPEECH_RATE, TtsSpeaker.DEFAULT_SPEECH_RATE));
		}
	    editor.putInt("runTimes", ++runTimes);
	    editor.commit();
    }
}