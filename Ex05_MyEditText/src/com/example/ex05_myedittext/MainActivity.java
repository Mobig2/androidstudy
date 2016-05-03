package com.example.ex05_myedittext;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

TextView textView1;
	
	EditText etId;
	EditText etPwd;
	EditText etYear;
	EditText etMonth;
	EditText etDay;
	
	String sId;
	String sPwd;
	String sYear;
	String sMonth;
	String sDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		textView1 = (TextView)findViewById(R.id.textView1);
		
		etId = (EditText)findViewById(R.id.etId);
		etPwd = (EditText)findViewById(R.id.etPwd);
		etYear = (EditText)findViewById(R.id.etYear);
		etMonth = (EditText)findViewById(R.id.etMonth);
		etDay = (EditText)findViewById(R.id.etDay);
		
		//패스워드 입력시 글자수 체크
		etPwd.addTextChangedListener(watcher);
		
		
		//키보드 내리기 버튼
		//inputType과 maxLength가 EditText에 지정되어 있으면 키보드에 리턴이 다음으로 됨
		//입력칸이 키보드에 가려지면 자동으로 올라감
		Button btnKeyDown = (Button)findViewById(R.id.btnKeyDown);
		btnKeyDown.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				//IME Hide
				InputMethodManager mgr = 
						(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		});
		
		//로그인 버튼
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				sId = etId.getText().toString();
				sPwd = etPwd.getText().toString();
				
				if(sId.length() < 3){
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("알림")
					.setMessage("아이디를 입혁해 주세요")
					.setCancelable(false)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setPositiveButton("닫기", null)
					.show();
				etId.requestFocus();
				return;
				}else if(sPwd.length() < 5){
					new AlertDialog.Builder(MainActivity.this)
					.setTitle("알림")
					.setMessage("비밀번호를 정확히 입력해 주세요")
					.setCancelable(false)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setPositiveButton("닫기", null)
					.show();
				etId.requestFocus();
				return;
				}
			}
		});
	}
	TextWatcher watcher = new TextWatcher() {
		String beforeText;
		public void onTextChanged(CharSequence str, int start, int before, int count){
			byte[] bytes = null;
			try{
				bytes = str.toString().getBytes("KSC5601");
				int strCount = bytes.length;
				textView1.setText(strCount + " / 8 바이트");
			}catch(UnsupportedEncodingException ex){
				ex.printStackTrace();
			}
		}
		
		public void beforeTextChanged(CharSequence str, int start, int count, int after){
			beforeText = str.toString();
			Log.d("beforeTextChanged", str.toString());
		}
		
		public void afterTextChanged(Editable strEditable) {
			String str = strEditable.toString();
			Log.d("afterTextChanged", str);
			try{
				byte[] strBytes = str.getBytes("KSC5601");
				if(strBytes.length > 8){
					etPwd.setText(beforeText);
					etPwd.setSelection(beforeText.length()-1, beforeText.length()-1);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	};
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
