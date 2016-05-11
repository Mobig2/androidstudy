package com.example.ex14_mylist2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	String[] names = { "홍길동", "강감찬", "을지문덕", "양만춘", "유관순" };
	String[] ages = { "20", "25", "30", "35", "40" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		ListView listView1 = (ListView) findViewById(R.id.listView1);
		
		//ArrayAdapter<String> adapter1;
		//adapter1 = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, names);
		//listView1.setAdapter(adapter);
		
		// 2단계
		final MyAdapter adapter = new MyAdapter();
		listView1.setAdapter(adapter);
		
		// 4단계
		listView1.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "selected : " + names[position], Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	// 1단계
	class MyAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return names[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 3단계
			LinearLayout layout = new LinearLayout(getApplicationContext());
			layout.setOrientation(LinearLayout.VERTICAL);
			
			// 1-1단계
			TextView view1 = new TextView( getApplicationContext());
			view1.setText(names[position]);
			view1.setTextSize(40.0f);
			view1.setTextColor(Color.BLUE);
			
			layout.addView(view1);
			//return view1;
			
			TextView view2 = new TextView(getApplicationContext());
			view2.setText(ages[position]);
			view2.setTextSize(40.0f);
			view2.setTextColor(Color.RED);
			
			layout.addView(view2);
			
			return layout;
		}

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
