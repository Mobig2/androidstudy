package com.example.ex_mylayout2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	LinearLayout layout1;
	LinearLayout layout2;
	LinearLayout layout3;
	ImageView imageView1;
	boolean imageSelected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout01);
		
		imageSelected = false;
		
		layout1 = (LinearLayout)findViewById(R.id.Layout1);
		layout2 = (LinearLayout)findViewById(R.id.Layout2);
		layout3 = (LinearLayout)findViewById(R.id.Layout3);
		
		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
				layout3.setVisibility(View.GONE);
				
			}
		});
		
		Button button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
				layout3.setVisibility(View.GONE);
				
			}
		});
		
		Button button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.GONE);
				layout3.setVisibility(View.VISIBLE);
				
			}
		});
		
		Button button6 = (Button)findViewById(R.id.button6);
		button6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imageView1 = (ImageView)findViewById(R.id.imageView1);
				
				if(imageSelected)
				{
					imageView1.setImageResource(R.drawable.house);
					imageSelected = false;
				}else{
					imageView1.setImageResource(R.drawable.car);
					imageSelected = true;
				}
				
			}
		});
		
		
	}

}
