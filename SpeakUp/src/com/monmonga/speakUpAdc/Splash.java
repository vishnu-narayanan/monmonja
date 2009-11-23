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

import com.monmonga.speakUpAdc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

public class Splash extends Activity {
	private static final int STOPSPLASH = 0;
    //time in milliseconds
    private static final long SPLASHTIME = 2500;
    
    private ImageView splash;
    
    //handler for splash screen
    private Handler splashHandler = new Handler() {
         /* (non-Javadoc)
          * @see android.os.Handler#handleMessage(android.os.Message)
          */
         @Override
         public void handleMessage(Message msg) {
              switch (msg.what) {
              case STOPSPLASH:
                   //remove SplashScreen from view
                   //splash.setVisibility(View.GONE);
            	  startActivity(new Intent(getBaseContext(),Main.class));
            	  finish();
                   break;
              }
              super.handleMessage(msg);
         }
    };
    
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle icicle) {
	   	super.onCreate(icicle);
	   	requestWindowFeature(Window.FEATURE_NO_TITLE);

	   	setContentView(R.layout.splash);
        splash = (ImageView) findViewById(R.id.splashscreen);
        Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
   } 
}
