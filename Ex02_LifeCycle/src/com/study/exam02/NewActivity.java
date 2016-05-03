package com.study.exam02;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "onCreate() »£√‚µ ", Toast.LENGTH_SHORT).show();
		Log.d("Study", "onCreate()	»£√‚µ ");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity);
		
		
		
		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "onStart() »£√‚µ ", Toast.LENGTH_SHORT).show();
		Log.d("Study", "onStart()	»£√‚µ ");
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "onResume() »£√‚µ ", Toast.LENGTH_SHORT).show();
		Log.d("Study", "onResume()	»£√‚µ ");
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "onPause() »£√‚µ ", Toast.LENGTH_SHORT).show();
		Log.d("Study", "onPause()	»£√‚µ ");
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "onStop() »£√‚µ ", Toast.LENGTH_SHORT).show();
		Log.d("Study", "onStop()	»£√‚µ ");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "onDestroy() »£√‚µ ", Toast.LENGTH_SHORT).show();
		Log.d("Study", "onDestroy()	»£√‚µ ");
		super.onDestroy();
	}

}
