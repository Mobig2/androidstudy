package com.study.exam02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		// 버튼5 :
				// 내가 생성한 액티비티 실행하고 결과 받아오기
				Button button1 = (Button)findViewById(R.id.button1);
				button1.setOnClickListener(new OnClickListener() {
							
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(getApplicationContext(), NewActivity.class); //명시적 호출 (내가 생성한 "NewActivity.class"를 불러라)
						
						startActivity(intent);
							 
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
