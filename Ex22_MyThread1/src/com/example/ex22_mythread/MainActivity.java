package com.example.ex22_mythread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView textView1;
	ProgressHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		// 3: 추가한 크랠스를 이용한 핸들러 변수 만들기
		handler = new ProgressHandler();
		
		textView1 = (TextView) findViewById(R.id.textView1);
		
		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				RequestThread thread = new RequestThread();
				thread.start();
				
			}
			
		});
	}

	class RequestThread extends Thread {
		public void run() {
			for (int i=0; i<100; i++){
				 
				//Log.d("gibom", "Request Thread .." + i);
				
				// 1-1 : 쓰레드에서 메인쓰레드의 객체로의 접근은 불가능 확인 예제
				//textView1.setText("Request Thread .." + i);
				 
				Message msg = handler.obtainMessage();
				
				Bundle bundle = new Bundle();
				bundle.putString("data", "Request Thread .. " + i);
				msg.setData(bundle);
				
				handler.sendMessage(msg);
				
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}

	
	// 1-2: 핸들러 클래스 생성 
	class ProgressHandler extends Handler {
		
		public void handleMessage(Message msg){
			
			// 5: 핸들러에 메시지가 전달되면 원하는 동작 처리 
			Bundle bundle = msg.getData();
			String data = bundle.getString("data");
			
			textView1.setText(data);
		}
	}
}