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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Main extends Activity { 
	private TextView contentTxt;
	
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int level = intent.getIntExtra("level", 0);
            int scale = intent.getIntExtra("scale", 100);
			contentTxt.setText(String.valueOf(level * 100 / scale) + "%");
		}
	};
	
	
	@Override 
	public void onCreate(Bundle icicle) { 
	    super.onCreate(icicle); 
	    setContentView(R.layout.main);
	    contentTxt = (TextView) this.findViewById(R.id.monospaceTxt);
	    this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	    
	    
	    
	    PrefsActivity.connectToBatteryService(getBaseContext());
	    PrefsActivity.setAppLanguage(getBaseContext());
	}
	
	@Override
 	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add(0,R.string.preferences,0,R.string.preferences).setIcon(R.drawable.settings);
		menu.add(1,R.string.buy_battery,1,R.string.buy_battery).setIcon(R.drawable.amazon);
		menu.add(1,R.string.about_app,1,R.string.about_app).setIcon(R.drawable.info);
		return super.onPrepareOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.string.preferences:
				startActivity(new Intent(this,PrefsActivity.class));
				return (true);
      		case R.string.buy_battery: 
      			String url = "http://www.amazon.com/gp/search?ie=UTF8&keywords=android%20"+Build.DEVICE+"%20battery&tag=monmonja05-20&index=blended&linkCode=ur2&camp=1789&creative=9325";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
      		break;
      		case R.string.about_app: 
      			String urlAbout = "http://almondmendoza.com/android-applications/";
                Intent aboutIntent = new Intent(Intent.ACTION_VIEW);
                aboutIntent.setData(Uri.parse(urlAbout));
                startActivity(aboutIntent);
      		break;
      		
		}
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}
}