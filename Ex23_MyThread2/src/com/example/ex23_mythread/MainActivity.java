package com.example.ex23_mythread;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView textView1;
	Handler handler;
	ProgressBar progressBar1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		// 3: 추가한 크랠스를 이용한 핸들러 변수 만들기
		handler = new Handler();
		
		textView1 = (TextView) findViewById(R.id.textView1);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		
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
				
				// 별도의 핸들러 클래스를 만들지 않고 바로 처리한다.
				// 앞의 예제처럼 핸들러 바깥의 변수와 안쪽의 변수를 공유할 수 없다.
				 final int index = i;
				 handler.post(new Runnable(){

					@Override
					public void run() {
					
						textView1.setText("Request Thread.. " + index);
						progressBar1.incrementProgressBy(1);
					}
					 
				 });
				
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
}

