package com.example.ex12_sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
		final SharedPreferences.Editor editor = pref.edit();
		
		final EditText tvID = (EditText)findViewById(R.id.etID);
		final EditText tvPwd = (EditText)findViewById(R.id.etPwd);
		
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String sid = tvID.getText().toString();
				String spwd = tvPwd.getText().toString();
				
				editor.putString("id", sid);
				editor.putString("pwd", spwd);
				editor.commit();
			}
		});
		
		String id = pref.getString("id", "");
		String pwd = pref.getString("pwd", "");
		
		Log.d("gikimi", "id:"+id);
		
		tvID.setText(id);
		tvPwd.setText(pwd);
	}

}
