package com.example.ex24_httpex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HttpPostTest extends Activity {

	Button 		btnPost;
	TextView 	tvHtml2;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_post_test);
    
        tvHtml2 = (TextView)findViewById(R.id.tvHtml2);
        
        btnPost = (Button)findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				tvHtml2.setText("");

				String sUrl = getString(R.string.server_addr) + "/my/web/session/login.php";

				getRequest(sUrl);
			}
		});
	}
	
	// Post로 부르기
	public void getRequest(final String strUrl) {
		// responseHandler는 Http요청에 대한 HttpResponse가 반환되면 결과를 처리하기 위한
		// 콜백메서드를 정의하고 있는 객체이다.
		final ResponseHandler<String> responseHandler=
			new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
				Log.d("gikimi","333 : " + new Date().toString());
				String result=null;
				HttpEntity entity=response.getEntity(); 
				result = getHttpData(entity.getContent());

				return result;
			}
		};

		//pDialog=ProgressDialog.show(this, "", "로그인 처리중....");

		// 서버에 HTTP 처리 요청은 새로운 스레드를 생성하여 비동기식으로 처리
		new Thread() {
			@Override
			public void run() {
				Message message = handler.obtainMessage();
				Bundle bundle = new Bundle();

				HttpClient http = new DefaultHttpClient();
				try { 
					// 서버에 전달할 파라메터 세팅   
					ArrayList<NameValuePair> nameValuePairs = 
						new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("userid", "lecture01"));
					nameValuePairs.add(new BasicNameValuePair("userpwd", "gikimi_pw"));

					// 응답시간이 5초가 넘으면 timeout 처리
					HttpParams params = http.getParams();
					HttpConnectionParams.setConnectionTimeout(params, 5000);
					HttpConnectionParams.setSoTimeout(params, 5000);

					// HTTP를 통해 서버에 요청을 전달
					// 요청에 대한 결과는 responseHandler의 handleResponse()메서드가 호출되어 처리
					HttpPost httpPost = new HttpPost(strUrl);
					// 서버에 전달되는 파라메터값을 인코딩
					UrlEncodedFormEntity entityRequest = 
						new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
					httpPost.setEntity(entityRequest);
					Log.d("gikimi","222 : " + new Date().toString());

					String sRtn = http.execute(httpPost, responseHandler); 

					bundle.putString("RESULT", sRtn);
					message.setData(bundle);
					handler.sendMessage(message);

				} catch(SocketTimeoutException e){
					Log.d("gikimi","444 : " + new Date().toString());
					bundle.putString("RESULT", "timeout");
					message.setData(bundle);
					handler.sendMessage(message);
				} catch(Exception e) {e.printStackTrace();}
			}
		}.start();    //스레드를 실행시킨다.

		Log.d("gikimi","111 : " + new Date().toString());
	}
	
	// 서버에서 전송된 데이타를 단순 저장
	public String getHttpData(InputStream input){
    	StringBuilder output = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input)) ;
			String line = null;
			while(true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				output.append(line + "\n");
			}
			reader.close();
		} catch(Exception e) {e.printStackTrace();}
		return output.toString();
	}

	// 네트웍 처리결과를 화면에 반영하기 위한 안드로이드 핸들러
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//pDialog.dismiss();

			String result = msg.getData().getString("RESULT");
			
			if ( result.equals("timeout") ) {
				Toast.makeText(HttpPostTest.this, "Network Timeout Error",
						Toast.LENGTH_LONG).show() ;     
			} else {
				tvHtml2.setText(result);
			}
		} 
	};

}
