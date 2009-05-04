package com.almondmendoza.monBattery;
/* <!-- 
* Copyright (C) 2009 AlmondMendoza
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
-->
*/

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;




public class BatteryService extends Service {
	private int APP_ID = 10000;
	private int currentPercent = 0;
	private Intent intentFrom;
	public static boolean isRunning;
	 
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 100);
            Log.d("BATTERY_STAT", intent.getClass().getName());
            
            currentPercent = level * 100 / scale;
 			Notification notification = new Notification(R.drawable.icon,
 	                	context.getResources().getString(R.string.your_battery_status), System.currentTimeMillis());
 			notification.number = currentPercent;
 			notification.flags = Notification.FLAG_NO_CLEAR; 

 			Intent contentIntent = new Intent(context,Main.class); 	        
 			notification.setLatestEventInfo(context,
 					context.getResources().getString(R.string.battery_status) + " " + String.valueOf(notification.number) + "%",
 					context.getResources().getString(R.string.your_battery_status),
 		            PendingIntent.getActivity(context, 0, contentIntent,Intent.FLAG_ACTIVITY_NEW_TASK));
 				
 			mManager.notify(APP_ID , notification);
 			
		}
	};
	private NotificationManager mManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("BATTERY_STAT", "battery service onCreate");
		BatteryService.isRunning = true;
		mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		this.unregisterReceiver(this.mBatInfoReceiver);
		BatteryService.isRunning = false;
		removeServiceNotification();
		super.onDestroy();
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		intentFrom = intent;
		super.onStart(intent, startId);
	}
	
	private int getPercentBatt(){
		return currentPercent;
	}
	
	
	private final IBattery.Stub binder=new IBattery.Stub() {
		   public int getPercent() {
		     return(getPercentBatt());
		   }
		   public void stopNotification(){
			   removeServiceNotification();
		   }
	};
	
	public  void removeServiceNotification(){
		mManager.cancel(APP_ID);
	}

}
