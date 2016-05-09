package com.example.ex09_myalert;

import com.example.util.CustomProgressDialog;
import com.example.util.MyUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static CustomProgressDialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		// 버튼  1 : 기본형
		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v)
			{
				MyUtil.AlertShow(MainActivity.this, "아이디를 입력해 주세요", "알림");
			}
		});
		
		// 버튼 2 : 상단 없애고 내용만
		Button button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				MyUtil.AlertShow(MainActivity.this, "아이디를 입력해 주세요");
			}
		});
		
		// 버튼 3 : 버튼 처리
		Button button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				
				builder.setMessage("앱을 종료하시겠습니까?")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("알림")
						.setCancelable(false)
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								Toast.makeText(getApplicationContext(), "ID value is" + Integer.toString(id), Toast.LENGTH_SHORT).show();
								dialog.cancel();
								
							}
						})
						.setNegativeButton("NO", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int id) {
								Toast.makeText(getApplicationContext(), "ID value is" + Integer.toString(id), Toast.LENGTH_SHORT).show();
								dialog.cancel();
								
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
				
			}
		});
		
		// 버튼 4 : 진행중 표시
		Button button4 = (Button)findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (progressDialog == null || progressDialog.isShowing()){
					progressDialog = CustomProgressDialog.show(MainActivity.this, "", "처리중입니다.", true, true, null);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
