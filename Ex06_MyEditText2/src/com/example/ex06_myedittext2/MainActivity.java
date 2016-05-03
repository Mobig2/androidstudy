package com.example.ex06_myedittext2;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText inputMessage;
	String strAmount = ""; //임시 저장값 (콤마)
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		inputMessage = (EditText)findViewById(R.id.inputMessage);
		
		//inputMessage.setInputType(InputType.TYPE_CLASS_NUMBER);//숫자만입력
		inputMessage.addTextChangedListener(watcher);
	}
	
	TextWatcher watcher = new TextWatcher() {
		public void onTextChanged(CharSequence str, int start, int before, int count){
			Log.d("gibom", str.toString() + ":" + strAmount);
			if(!str.toString().equals(strAmount)){ //StackOverflow를 막기위해,(문자입력받으려면 이 조건문을 주석처리해야됨)
				strAmount = makeStringComma(str.toString().replace(",", ""));
				inputMessage.setText(strAmount);
				
				inputMessage.setSelection(inputMessage.getText().length(), 
										  inputMessage.getText().length());			
			}
		}
		public void beforeTextChanged(CharSequence str, int start, int count, int after){
			strAmount = str.toString();
		}
		
		public void afterTextChanged(Editable strEditable){
			
		}
	};

	protected String makeStringComma(String str){
		if(str.length() == 0)
			return "";
		long value = Long.parseLong(str);
		DecimalFormat format = new DecimalFormat("###,##0");
		return format.format(value);
	}
	
}
