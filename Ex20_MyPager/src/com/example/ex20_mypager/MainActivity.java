package com.example.ex20_mypager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	ViewPager pager1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		pager1 = (ViewPager) findViewById(R.id.pager1);
		//기본 3개 : 다음으로 숫자 조정
		pager1.setOffscreenPageLimit(3);
		
		MyPagerAdapter adapter = new MyPagerAdapter();
		
		pager1.setAdapter(adapter);
		
		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager1.setCurrentItem(0);
			}
		});
		
		Button button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager1.setCurrentItem(1);
			}
			
		});
		
		Button button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager1.setCurrentItem(2);
			}
			
		});
	}

	class MyPagerAdapter extends PagerAdapter{
		String[] names = {"홍길동","강감찬","을지문덕"};

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view.equals(obj);
		}
		
		public void destroyItem(View container, int position, Object object){
			//pager1.removeView((LinearLayout)object);
			pager1.removeView((View)object);
			
			//super.destroyItem(container, position, object);
		}
		
		public Object instantiateItem(View container, int position){
			
			LinearLayout layout = new LinearLayout(getApplicationContext());
			layout.setOrientation(LinearLayout.VERTICAL);
			
			TextView view1 = new TextView(getApplicationContext() );
			view1.setText(names[position]);
			view1.setTextSize(40.0f);
			view1.setTextColor(Color.BLUE);
			
			layout.addView(view1);
			
			pager1.addView(layout, position);
			
			return layout;
			
		}
		
	}
	
}
