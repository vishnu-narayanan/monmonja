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
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneStateListener extends PhoneStateListener {
	private Context context;
	private Cursor contactsCursor;
	private static int previousState = -1;
	private String[] projection = new String[] {
            People._ID, People.NAME, People.NUMBER
    };
	private Timer timer;
	private static String incomingNumber;
	private static final String LOG = "MyPhoneStateListener";
	private static final int secondsDelay = 7000;
	
	public void onCallStateChanged(int state,String incomingNumber){	
		if(MyPhoneStateListener.previousState != state){
			MyPhoneStateListener.previousState = state;
			switch(state)
         	{
               case TelephonyManager.CALL_STATE_IDLE:
                    Log.d(LOG, "IDLE"  + timer);
                    if(timer != null){
                    	timer.cancel();
                    }
                    if(incomingNumber.equals("")){
                    	TtsSpeaker.speak(R.string.call_ended);
                    }else{
                    	speakContact(incomingNumber,R.string.incoming_call_ended);
                    }
                    break;
               case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d(LOG, "OFFHOOK");
                    break;	
               case TelephonyManager.CALL_STATE_RINGING:
            	   Log.d(LOG, "RINGING");
            	   MyPhoneStateListener.incomingNumber = incomingNumber;
            	   timer = new Timer (  ) ;
            	   timer.schedule(new TimerTask(){
            		   public void run (  )   {
            			   if(MyPhoneStateListener.previousState == TelephonyManager.CALL_STATE_IDLE){
            				   this.cancel();
            			   }else{
            				   speakContact(MyPhoneStateListener.incomingNumber,R.string.incoming_ringing);  
            			   }
            			   
            			   Log.d(LOG, "RINGING");
            		   }
            	   }, 0,MyPhoneStateListener.secondsDelay);
            	   break;
         	}
		}
		
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
	public void speakContact(String incomingNumber,int strSpeech){
		Uri contactUri = Uri.withAppendedPath(Contacts.Phones.CONTENT_FILTER_URL, incomingNumber);
 	   	contactsCursor = context.getContentResolver().query(contactUri,projection, 
    			null , null, People.NAME + " ASC");
 	   	
 	   	String txtSpeech = (String) context.getText(strSpeech);
 	  
	  	
 	   	if(contactsCursor.moveToFirst()){
 		   int phoneNameIndex = contactsCursor.getColumnIndex(People.NAME);
 		   String phoneNameStr = contactsCursor.getString(phoneNameIndex);
 		   txtSpeech = String.format(txtSpeech, phoneNameStr);
 		   
     	   TtsSpeaker.speak(txtSpeech);
 	   	}else{
 	   		char[] charIncomingNumber = incomingNumber.toCharArray();
 	   		char[] finalIncomingNumber = new char[charIncomingNumber.length * 2];
 	   		for(int i = 0,j = 0; i <charIncomingNumber.length;i++,j = j + 2){
 	   			finalIncomingNumber[j] = charIncomingNumber[i];
 	   			finalIncomingNumber[j+1] = ' ';
 	   		}
 	   		Log.d(LOG,  new String(finalIncomingNumber));
 	   		
 	   		txtSpeech = String.format(txtSpeech, new String(finalIncomingNumber));
 	   		TtsSpeaker.speak(txtSpeech);
 	   	}
	}

}