package com.example.ex11_introex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Intro extends Activity {
	private static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 2000;
	
	private Handler splashHandler = new Handler() {
		
		public void handleMessage(Message msg){
			Intent intent;
			
			switch (msg.what){
			case STOPSPLASH:
				intent = new Intent(Intro.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.hold);
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_activity);
	}
	
	public void onResume() {
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		
		super.onResume();
	}
}
