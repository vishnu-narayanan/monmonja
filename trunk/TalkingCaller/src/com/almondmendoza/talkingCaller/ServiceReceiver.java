package com.almondmendoza.talkingCaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;


public class ServiceReceiver extends BroadcastReceiver {
	static final String ACTION ="android.provider.Telephony.SMS_RECEIVED";
	 protected static final String TAG = "ServiceReceiver"; 
	 
	@Override
	public void onReceive(Context context, Intent intent) {
		 
		if (intent.getAction().equals(ACTION)) { 
			handleSMS(context,intent);
		}else{
			handleCall(context,intent);
		}
    }
	
	public void handleCall(Context context, Intent intent){
		MyPhoneStateListener phoneListener=new MyPhoneStateListener();
		phoneListener.context = context;
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        
        telephony.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	public void handleSMS(Context context, Intent intent){
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		Bundle bundle = intent.getExtras();
        
		if (bundle != null) {
			Object[] pdusObj = (Object[]) bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdusObj.length];
			for (int i = 0; i<pdusObj.length; i++) {
				messages[i] = SmsMessage.createFromPdu ((byte[]) pdusObj[i]);
			}
        
			// Feed the StringBuilder with all Messages found.
			for (SmsMessage currentMessage : messages){
				sb.append("Received compressed SMS From: ");
				// Sender-Number
				sb.append(currentMessage.getDisplayOriginatingAddress());
				sb.append(". The message is ");
				// Actual Message-Content
				sb.append(currentMessage.getDisplayMessageBody());
				break;
			}
		}
        
		Log.i(TAG,sb.toString());
        new TtsSpeaker(context,sb.toString());
	}

}