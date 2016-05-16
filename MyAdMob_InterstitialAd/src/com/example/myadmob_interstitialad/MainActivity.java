package com.example.myadmob_interstitialad;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private static InterstitialAd  interstitial;
    private static final String AD_UNIT_ID = "ca-app-pub-7326469277711196/3614746668";
  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
         
        // 삽입 광고를 만듭니다.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(AD_UNIT_ID);

        // 광고 요청을 만듭니다.
        AdRequest adRequest = new AdRequest.Builder().build();

        // 삽입 광고 로드를 시작합니다.
        interstitial.loadAd(adRequest);
        AdRequest adRequest1 = new AdRequest.Builder()
        		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        		.addTestDevice("9CB4F47F9CF0238A76C9A736A45787F7")
        		.build();
    }

      // 삽입 광고를 표시할 준비가 되면 displayInterstitial()을 호출합니다.
      public void displayInterstitial() {
        if (interstitial.isLoaded()) {
          interstitial.show();
        }
    }
}
