package com.almondmendoza.talkingCaller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneStateListener extends PhoneStateListener {
	public static Context context;
	private Cursor contactsCursor;
	private int previousState = -1;
	private String[] projection = new String[] {
            People._ID, People.NAME, People.NUMBER
    };
	
	public void onCallStateChanged(int state,String incomingNumber){
		if(this.previousState != state){
			switch(state)
         	{
               case TelephonyManager.CALL_STATE_IDLE:
                    Log.d("DEBUG", "IDLE");
                    speakContact(incomingNumber," has ended the call");
                    break;
               case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d("DEBUG", "OFFHOOK");
                    break;
               case TelephonyManager.CALL_STATE_RINGING:
            	   Log.d("DEBUG", "RINGING");
            	   speakContact(incomingNumber," is calling");
            	   break;
         	}
			this.previousState = state;
		}
	}	
	
	public void speakContact(String incomingNumber,String strSpeech){
		Uri contactUri = Uri.withAppendedPath(Contacts.Phones.CONTENT_FILTER_URL, incomingNumber);
 	   	contactsCursor = context.getContentResolver().query(contactUri,projection, 
    			null , null, People.NAME + " ASC");
 	   	if(contactsCursor.moveToFirst()){
 		   int phoneNameIndex = contactsCursor.getColumnIndex(People.NAME);
 		   String phoneNameStr = contactsCursor.getString(phoneNameIndex);
     	   new TtsSpeaker(context,phoneNameStr + strSpeech);
 	   	}else{
 		   new TtsSpeaker(context,incomingNumber + strSpeech);
 	   	}
	}

}