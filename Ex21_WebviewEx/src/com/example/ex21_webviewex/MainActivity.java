package com.example.ex21_webviewex;

import com.example.util.CustomProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final View handler = null;
	WebView web;
	EditText etMessage;
	Button btnLocalHtml;
	Button btnWebHtml;
	Button btnJSCall;
	Button btnStringLoad;
	public CustomProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		web = (WebView) findViewById(R.id.web1);
		web.clearCache(true); // 캐쉬 지우기
		web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 캐쉬 사용하지 않기
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setDefaultTextEncodingName("UTF-8");
		web.loadUrl("http://m.naver.com");
		web.setWebViewClient(new myWebView());
		web.setWebChromeClient(new myWebChromeClient());
		// web.setHorizontalScrollBarEnabled(false); // 세로 scroll 제거
		// web.setVerticalScrollBarEnabled(false); // 가로 scroll 제거
		web.addJavascriptInterface(new JavaScriptBridge(), "android");

		etMessage = (EditText) findViewById(R.id.etMessage);

		btnLocalHtml = (Button) findViewById(R.id.btnLocalHtml);
		btnLocalHtml.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getLocalHtmlLoad();
			}
		});

		btnWebHtml = (Button) findViewById(R.id.btnWebHtml);
		btnWebHtml.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getWebHtmlLoad();
			}
		});

		btnJSCall = (Button) findViewById(R.id.btnJavaScriptCall);
		btnJSCall.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				web.loadUrl("javascript:setMessage('" + etMessage.getText() + "')");
			}

		});

		btnStringLoad = (Button) findViewById(R.id.btnHtmlString);
		btnStringLoad.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getStringLoad();
			}
		});

	}

	// 로컬 html 부르기
	private void getLocalHtmlLoad() {
		web.loadUrl("file:///android_asset/jsexam.html");
	}

	// 웹 html 부르기
	private void getWebHtmlLoad() {
		web.loadUrl("http://m.google.com");
	}

	// String 웹뷰로 부르기
	private void getStringLoad() {
		String summary = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>" + "<html>" + "<body>"
				+ "Hello~ 강감찬" + "<img src='http://www.pierload.x-y.net/img/totoro.jpg'>" + "</body>" + "</html>";

		// Android 4.0 이하 버전
		// web.loadData(summary, "text/html", "UTF-8");
		// Android 4.1 이상 버전
		web.loadData(summary, "text/html; charset=UTF-8", null);
	}

	// 웹뷰에서 자바스크립트의 Alert/Confirm을 보여 줄 수 있도록 한다.
	private class myWebChromeClient extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
			result.confirm();

			// JavaScript가 띄우는 알림에는 주소가 포함되므로 가리기 위해 아래와 같이 처리한다.
			new AlertDialog.Builder(view.getContext())
					// .setIcon(R.drawable.icon);
					// .setTitle(R.string.title_activity_main)
					.setMessage(message).setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					}).setCancelable(true).create().show();

			return true;
		};

		@Override
		public boolean onJsConfirm(WebView view, String url, String message, final android.webkit.JsResult result) {
			result.confirm();

			new AlertDialog.Builder(view.getContext())
					// .setTitle(R.string.title_activity_main)
					.setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.cancel();
						}
					}).create().show();

			return true;
		};
	}

	// 안드로이드 자체 WebView가 아닌 내가 만든 WebView 직접 사용한다고 명시
	// 직접 처리하기때문에 접근권한이 필요하다. 접근권한은 AndroidManifest.xml에 필요한 권한을 추가했다.
	private class myWebView extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.startsWith("http://")) {
				view.loadUrl(url);
				return true;
			}
			if (url.startsWith("mailto:")) {
				Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
				startActivity(i);
				return true;
			}
			if (url.startsWith("sms:")) {
				Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
				startActivity(i);
				return true;
			}
			if (url.startsWith("tel:")) {
				Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(url));
				startActivity(i);
				return true;
			}
			if (url.startsWith("geo:")) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(i);
				return true;
			}
			return true;
		}

		/**
		 * 웹페이지 로딩이 시작할 때 처리
		 */
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progressDialog = CustomProgressDialog.show(MainActivity.this, "", "", true, true, null);

		}

		/**
		 * 웹페이지 로딩중 에러가 발생했을때 처리
		 */
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			Toast.makeText(getApplicationContext(), "Loading Error : " + description, Toast.LENGTH_SHORT).show();

			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}

		/**
		 * 웹페이지 로딩이 끝났을 때 처리
		 */
		@Override
		public void onPageFinished(WebView view, String url) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}

	// 자바스크립트에서 Call할 클래스 함수 (쓰레드 동작방식과 같다. Jni 형태이다.)
	final class JavaScriptBridge {

		@JavascriptInterface
		public void test() {
			handler.post(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(), "테스트", Toast.LENGTH_SHORT).show();
				}
			});
		}

		// Parameter must be final
		@JavascriptInterface
		public void testParams(final String arg) {
			handler.post(new Runnable() {
				public void run() {
					Log.d("err", "setMessage(" + arg + ")");
					Toast.makeText(getApplicationContext(), "테스트파람:" + arg, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	// 이전키 눌렀을 때 WebView 히스토리 back
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
