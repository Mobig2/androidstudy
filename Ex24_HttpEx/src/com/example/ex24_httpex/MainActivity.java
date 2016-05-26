package com.example.ex24_httpex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnGetAct;
	Button btnPostAct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		btnGetAct = (Button) findViewById(R.id.btnGetAct);
		btnGetAct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, HttpGetTest.class);
				startActivity(intent);

			}
		});
		btnPostAct = (Button) findViewById(R.id.btnPostAct);
		btnPostAct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, HttpPostTest.class);
				startActivity(intent);

			}
		});
	}

}
