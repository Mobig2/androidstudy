package com.example.ex24_httpex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HttpGetTest extends Activity {

	Button btnGet;
	TextView tvHtml;
	Handler handler;
	ProgressBar progressBar1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_get_test);

		// 3: 추가한 클래스를 이용한 핸들러 변수 만들기
		handler = new ProgressHandler();

		tvHtml = (TextView) findViewById(R.id.tvHtml1);

		btnGet = (Button) findViewById(R.id.btnGet);
		btnGet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String sUrl = "http://www.enjoypuzzle.com/";

				RequestGetThread thread = new RequestGetThread(sUrl);
				thread.start();
			}
		});
	}

	class RequestGetThread extends Thread {
		String sUrl = "";

		public RequestGetThread(String sUrl) {
			this.sUrl = sUrl;
		}

		public void run() {
			// Get으로 부르기
			StringBuilder output = new StringBuilder();
			try {
				URL url = new URL(sUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					conn.setDoOutput(true);

					int resCode = conn.getResponseCode();
					if (resCode == HttpURLConnection.HTTP_OK) {
						Log.d("gibom", "aaa");
						BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String line = null;
						while (true) {
							line = reader.readLine();
							if (line == null) {
								break;
							}
							output.append(line + "\n");
						}
						reader.close();
						conn.disconnect();

						Log.d("gibom", output.toString());

						// 4: 핸들러에 전달할 메시지 작성
						Message msg = handler.obtainMessage();

						Bundle bundle = new Bundle();
						bundle.putString("data", output.toString());
						msg.setData(bundle);

						handler.sendMessage(msg);
					} else {
						Log.d("gibom", "bbb");
					}
				} else {
					Log.d("gibom", "ccc");
				}
			} catch (Exception ex) {
				Log.d("err", "Exception in processing response.", ex);
			}
		}
	}

	// 2: 핸들러 클래스 생성
	class ProgressHandler extends Handler {

		public void handleMessage(Message msg) {
			// 5: 핸들러에 메시지가 전달되면 원하는 동작 처리
			Bundle bundle = msg.getData();
			String data = bundle.getString("data");

			tvHtml.setText(data);
		}
	}
}
