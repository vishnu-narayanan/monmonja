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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;


public class ServiceReceiver extends BroadcastReceiver {
	static final String ACTION ="android.provider.Telephony.SMS_RECEIVED";
	 protected static final String TAG = "ServiceReceiver";
	private SharedPreferences prefs; 
	 
	@Override
	public void onReceive(Context context, Intent intent) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		if (intent.getAction().equals(ACTION)) { 
			if(prefs.getBoolean("read_sms", true) == true)
				handleSMS(context,intent);
		}else{
			Log.d("TtsSpeaker","talking_caller_id");
			if(prefs.getBoolean("talking_caller_id", true) == true)
				handleCall(context,intent);
			
		}
    }
	
	public void handleCall(Context context, Intent intent){
		MyPhoneStateListener phoneListener=new MyPhoneStateListener();
		phoneListener.setContext(context);
		
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	public void handleSMS(Context context, Intent intent){
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
        
		if (bundle != null) {
			Object[] pdusObj = (Object[]) bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdusObj.length];
			for (int i = 0; i<pdusObj.length; i++) {
				messages[i] = SmsMessage.createFromPdu ((byte[]) pdusObj[i]);
			}
        
			String txtSpeech = (String) context.getText(R.string.received_new_sms);
			
			// Feed the StringBuilder with all Messages found.
			for (SmsMessage currentMessage : messages){
				String incomingNumber = currentMessage.getDisplayOriginatingAddress();
				try{
					Long f = Long.valueOf(currentMessage.getDisplayOriginatingAddress());
					char[] charIncomingNumber = f.toString().toCharArray();
		 	   		char[] finalIncomingNumber = new char[charIncomingNumber.length * 2];
		 	   		for(int i = 0,j = 0; i <charIncomingNumber.length;i++,j = j + 2){
		 	   			
		 	   			finalIncomingNumber[j] = charIncomingNumber[i];
		 	   			finalIncomingNumber[j+1] = ' ';
		 	   		}
		 	   		incomingNumber = new String(finalIncomingNumber);
				}catch(Exception e){
					incomingNumber = currentMessage.getDisplayOriginatingAddress();
				}
				
	 	   		
				txtSpeech = String.format(txtSpeech, incomingNumber, currentMessage.getDisplayMessageBody());
				break;
			}
			TtsSpeaker.speak(txtSpeech);
		}
        
		
	}
}