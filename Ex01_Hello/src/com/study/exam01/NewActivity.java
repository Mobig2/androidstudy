package com.study.exam01;

import android.app.Activity;
import android.content.Intent;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity);
		
		// 인텐트에 전달된 데이타 구하기
		Intent intent = getIntent();
		final String sName = intent.getStringExtra("CustomerName");
		
		// 인텐트 종료하기
		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v)
			{
				//현재 인텐트 종료 시 인텐트에 전달할 데이터 세팅
				Intent intent = new Intent();
				intent.putExtra("BackData", "강감찬");
				setResult(10, intent); // MainActivity.java에 정의된 onActivityResult 함수의 매개변수 resultCode를 설정함.
				
				finish();
			}
		});
		
		// 토스트로 전달된 데이터 보여주기 
		Button button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v)
			{
				Toast.makeText(getApplicationContext(),
				    "CustomerName : " + sName, Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	

}
