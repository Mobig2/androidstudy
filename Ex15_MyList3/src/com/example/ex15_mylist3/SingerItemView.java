package com.example.ex15_mylist3;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {

	TextView textView1;
	TextView textView2;
	ImageView imageView1;
	
	public SingerItemView(Context context){
		super(context);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.singer_item_view, this, true);
		
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
	}
	
	public void setName(String name) {
		textView1.setText(name);
	}
	
	public void setAge(String age) {
		textView2.setText(age);
	}
	
	public void setImage(int imgNum) {
		imageView1.setImageResource(imgNum);
	}
	
	
}
