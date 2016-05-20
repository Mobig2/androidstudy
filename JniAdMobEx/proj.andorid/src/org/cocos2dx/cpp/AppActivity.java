/****************************************************************************
Copyright (c) 2008-2010 Ricardo Quesada
Copyright (c) 2010-2012 cocos2d-x.org
Copyright (c) 2011      Zynga Inc.
Copyright (c) 2013-2014 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class AppActivity extends Activity {

	static public Handler handler;

	private static AppActivity _appActivity;
	private AdView adView;
	private InterstitialAd interstitialAd;
	private AdRequest adRequest;

	// private static final String AD_UNIT_ID =
	// "ca-app-pub-0000000000000000/0000000000";
	private static final String AD_UNIT_ID = "ca-app-pub-7326469277711196/7545418664";
	private static final String AD_UNIT_ID_FULL = "ca-app-pub-7326469277711196/3614746668";

	// Helper get display screen to avoid deprecated function use
	private Point getDisplaySize(Display d) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return getDisplaySizeGE11(d);
		}
		return getDisplaySizeLT11(d);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private Point getDisplaySizeGE11(Display d) {
		Point p = new Point(0, 0);
		d.getSize(p);
		return p;
	}

	private Point getDisplaySizeLT11(Display d) {
		try {
			Method getWidth = Display.class.getMethod("getWidth", new Class[] {});
			Method getHeight = Display.class.getMethod("getHeight", new Class[] {});
			return new Point(((Integer) getWidth.invoke(d, (Object[]) null)).intValue(),
					((Integer) getHeight.invoke(d, (Object[]) null)).intValue());
		} catch (NoSuchMethodException e2) // None of these exceptions should
											// ever occur.
		{
			return new Point(-1, -1);
		} catch (IllegalArgumentException e2) {
			return new Point(-2, -2);
		} catch (IllegalAccessException e2) {
			return new Point(-3, -3);
		} catch (InvocationTargetException e2) {
			return new Point(-4, -4);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		int width = getDisplaySize(getWindowManager().getDefaultDisplay()).x;

		LinearLayout.LayoutParams adParams = new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_UNIT_ID);

		adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				// .addTestDevice("HASH_DEVICE_ID")
				.addTestDevice("9CB4F47F9CF0238A76C9A736A45787F7").build();

		adView.loadAd(adRequest);
		adView.setBackgroundColor(Color.BLACK);
		adView.setBackgroundColor(0);
		addContentView(adView, adParams);

		// ----------------------------------------------------------------
		// 삽입 광고
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID_FULL);

		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Log.d("study", "onAdLoaded");

				if (interstitialAd.isLoaded()) {
					interstitialAd.show();
				} else {
					Log.d("study", "Interstitial ad was not ready to be shown.");
				}
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				String message = String.format("onAdFailedToLoad (%s)", getErrorReason(errorCode));
				Log.d("study", message);
			}
		});
		// ------------------------------------------------------------------------
		_appActivity = this;
		// ------------------------------------------------------------------------
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					showAd();
				} else if (msg.what == 1) {
					hideAd();
				} else if (msg.what == 2) {
					showFullAd();
				}
			}

		};
		// ------------------------------------------------------------------------
	}

	public static void hideAd() {
		Log.d("Cocos2d-x", "bbbbb");
		_appActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (_appActivity.adView.isEnabled())
					_appActivity.adView.setEnabled(false);
				if (_appActivity.adView.getVisibility() != 4)
					_appActivity.adView.setVisibility(View.INVISIBLE);
			}
		});
		/*
		 * Cocos2dxHelper.runOnGLThread(new Runnable(){
		 * 
		 * @Override public void run() { if(_appActivity.adView.isEnabled())
		 * _appActivity.adView.setEnabled(false);
		 * if(_appActivity.adView.getVisibility() != 4)
		 * _appActivity.adView.setVisibility(View.INVISIBLE); } });
		 */
	}

	public static void showAd() {
		Log.d("Cocos2d-x", "aaaaa");
		_appActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (_appActivity.adView.isEnabled())
					_appActivity.adView.setEnabled(true);
				if (_appActivity.adView.getVisibility() == 4)
					_appActivity.adView.setVisibility(View.INVISIBLE);
			}

		});
		/*
		 * Cocos2dxHelper.runOnGLThread(new Runnable(){
		 * 
		 * @Override public void run() { if(_appActivity.adView.isEnabled())
		 * _appActivity.adView.setEnabled(true);
		 * if(_appActivity.adView.getVisibility() != 4)
		 * _appActivity.adView.setVisibility(View.INVISIBLE); } });
		 */
	}

	public static void showFullAd() {
		Log.d("Cocos2d-x", "ccccc");
		_appActivity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// 삽입 광고 로드 시작
				_appActivity.interstitialAd.loadAd(_appActivity.adRequest);
			}
		});
		/*
		 * Cocos2dxHelper.runOnGLThread(new Runnable(){
		 * 
		 * @Override public void run() { // 삽입 광고 로드 시작
		 * _appActivity.interstitialAd.loadAd(_appActivity.adRequest); } });
		 */
	}

	protected void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}
	}

	protected void onPause() {
		super.onResume();
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	protected void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}

	/** Gets a string error reason from an error code. */
	private String getErrorReason (int errorCode){
		String errorReason = "";
		switch(errorCode){
		case AdRequest.ERROR_CODE_INTERNAL_ERROR:
		errorReason = "Internal error";
			break;
		case AdRequest.ERROR_CODE_INVALID_REQUEST:
		errorReason = "Internal request";
			break;
		case AdRequest.ERROR_CODE_NETWORK_ERROR:
		errorReason = "Network Error";
			break;
		case AdRequest.ERROR_CODE_NO_FILL:
		errorReason = "No fill";
			break;
		}
		return errorReason;
	}
	//------------------------------------------------------------------
	public static void ShowAdPopup()
	{
		handler.sendEmptyMessage( 0 );
	}
	
	public static void HideAdPopup()
	{
		handler.sendEmptyMessage( 1 );
	}
	
	public static void ShowAdFull()
	{
		handler.sendEmptyMessage( 2 );
	}
}
