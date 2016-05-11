package com.example.ex15_mylist3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	String[] names = { "홍길동", "강감찬", "을지문덕", "양만춘", "유관순" };
	String[] ages = { "20", "25", "30", "35", "40" };
	int[] images = { R.drawable.face1, R.drawable.face2, R.drawable.face3,
					 R.drawable.face1, R.drawable.face2 };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
	// 3단계
	final MyAdapter adapter = new MyAdapter();
	
	ListView listView1 = (ListView) findViewById(R.id.listView1);
	
	listView1.setAdapter(adapter);

	// 4단계
	listView1.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			Toast.makeText(getApplicationContext(), "selected" + names[position], Toast.LENGTH_SHORT).show();
			
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
				
//				LinearLayout layout = new LinearLayout(getApplicationContext());
//				layout.setOrientation(LinearLayout.VERTICAL);
//
//				TextView view1 = new TextView( getApplicationContext());
//				view1.setText(names[position]);
//				view1.setTextSize(40.0f);
//				view1.setTextColor(Color.BLUE);
//				
//				layout.addView(view1);
//				
//				TextView view2 = new TextView(getApplicationContext());
//				view2.setText(ages[position]);
//				view2.setTextSize(40.0f);
//				view2.setTextColor(Color.RED);
//				
//				layout.addView(view2);
//				
//				return layout;
				
				// 2단계
				SingerItemView view = new SingerItemView(getApplicationContext());
				view.setName(names[position]);
				view.setAge(ages[position]);
				view.setImage(images[position]);
				
				return view;
			}

		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
