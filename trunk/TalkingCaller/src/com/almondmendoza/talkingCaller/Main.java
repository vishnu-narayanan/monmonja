package com.almondmendoza.talkingCaller;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Main extends ListActivity {
	protected static final int PREFS_UPDATED = 1;
	protected static final int CALL_CONFIRM = 2;
	protected static final int INSTRUCTION_INTENT = 3;
	
	private Cursor contactsCursor;
	private String idToCall = "";
	private String phoneToDial;
	private String phoneNameStr;
	
	private String[] projection = new String[] {
            People._ID, People.NAME, People.NUMBER
    };
	

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    } 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, R.string.settings, 0, R.string.settings).setIcon(R.drawable.settings48);
    	menu.add(0,R.string.instructions,0,R.string.instructions).setIcon(R.drawable.help48);
    	return super.onCreateOptionsMenu(menu);
    }
    
   

    @Override
	public boolean onMenuOpened(int featureId, Menu menu) {
    	new TtsSpeaker(this,R.string.speed_settings);
		// TODO Auto-generated method stub
		return super.onMenuOpened(featureId, menu);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
      Intent intent;
      switch (item.getItemId()) {
      	case R.string.settings:
      		intent = new Intent(this, PreferencesSelection.class);
      		startActivityForResult(intent,PREFS_UPDATED);
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
      if (requestCode == PREFS_UPDATED) {
    	  Intent intent;
    	  if(resultCode == RESULT_OK){
    		 // new TtsSpeaker(this,R.string.saved_preferences);
    	  }else{
    		  intent = new Intent(this, NormalPreferences.class);
    		  startActivity(intent);
    	  }
      }else if(requestCode == CALL_CONFIRM){
    	  if(resultCode == RESULT_OK){
    		  	String tapToCall = (String) getText(R.string.call_start);
    		  	tapToCall = String.format(tapToCall, phoneNameStr);
    		  	new TtsSpeaker(this,tapToCall);
    		  	
    		  	// START THE CALL
    		  	Uri parsedPhoneNumber = Uri.parse("tel:"+phoneToDial); 
  	        	Intent i = new Intent(Intent.ACTION_CALL,parsedPhoneNumber);
  	        	startActivity(i);
    	  }else{
    		  	new TtsSpeaker(this,R.string.call_cancel);
    	  }
      }else if(requestCode == INSTRUCTION_INTENT){
    	  new TtsSpeaker(this,R.string.back_to_your_contact_list);
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
        	new TtsSpeaker(this,tapToCall);
        	
        	// START THE CONTACT  CONFIRMATION
        	Intent callConfirm = new Intent(this,CallConfirm.class);
        	startActivityForResult(callConfirm,CALL_CONFIRM);
        	
        	// CLEAR THE ID TO CALL
        	idToCall = "";
        }else{
        	// SPEAK THE CONTACT
        	new TtsSpeaker(this,phoneNameStr);
        	
        	idToCall = c.getString(phoneIdIndex);
        }
        // TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
	
	// NON OVERRIDE FUNCTION 
	 private void loadSettings(){
    	SharedPreferences settings = getSharedPreferences(PreferencesSelection.PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		int runTimes = settings.getInt("runTimes",0);
        
		if(runTimes == 0){
			new TtsSpeaker(this,R.string.welcome_note_with_instruction,settings.getInt("speechRate", TtsSpeaker.DEFAULT_SPEECH_RATE));
		}else{
			new TtsSpeaker(this,R.string.welcome_note,settings.getInt("speechRate", TtsSpeaker.DEFAULT_SPEECH_RATE));
		}
	    editor.putInt("runTimes", ++runTimes);
	    editor.commit();
    }
}