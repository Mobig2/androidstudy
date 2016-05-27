package com.example.xmljsonex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnJson1;
	Button btnJson2;
	Button btnJson3;
	Button btnXml1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		btnJson1 = (Button) findViewById(R.id.btnJson1);
		btnJson1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getJsonData1();

			}
		});
		btnJson2 = (Button) findViewById(R.id.btnJson2);
		btnJson2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getJsonData2();

			}
		});
		btnJson3 = (Button) findViewById(R.id.btnJson3);
		btnJson3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getJsonData3();

			}
		});

		btnXml1 = (Button) findViewById(R.id.btnXml1);
		btnXml1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getXmlData1();

			}
		});
	}

	private void getJsonData1() {
		String json4arr = "{\"number\":[1,2,3,4,5]}";
		try {
			JSONObject jobj = new JSONObject(json4arr);
			JSONArray jarr = jobj.getJSONArray("number");
			for (int i = 0; i < jarr.length(); i++) {
				int tmp = jarr.getInt(i);
				Log.d("gibom", "Json Data : " + tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getJsonData2() {
		String json4obj = "{\"color\":{\"top\":\"red\",\"bottom\":\"black\",\"left\":\"blue\",\"right\":\"green\"}}";
		try {
			JSONObject jobj = new JSONObject(json4obj);
			JSONObject color = jobj.getJSONObject("color");

			String top_color = "";
			if (color.has("left")) {
				top_color = color.getString("left");
				Log.d("gibom", "left color : " + top_color);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getJsonData3() {
		String jString = "{\"menu\": {\"id\": \"file\", \"value\": \"File\", \"popup\": { \"menuitem\": [ {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"}, {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"}, {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}]}}}";
		try {
			JSONObject jObject = new JSONObject(jString);
			JSONObject menuObject = jObject.getJSONObject("menu");

			String attributeId = menuObject.getString("id");
			String attributeValue = menuObject.getString("value");
			JSONObject popupObject = menuObject.getJSONObject("popup");

			JSONArray menuitemArray = popupObject.getJSONArray("menuitem");

			for (int i = 0; i < menuitemArray.length(); i++) {
				Log.d("gibom", menuitemArray.getJSONObject(i).getString("value").toString());
				Log.d("gibom", menuitemArray.getJSONObject(i).getString("onclick").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getXmlData1() {

		try {
			ArrayList<String> aNumber = new ArrayList<String>();
			ArrayList<String> aWord = new ArrayList<String>();
			ArrayList<String> aMean = new ArrayList<String>();

			// XML 처리에 사용할 변수 선언
			int event = 0;
			String currentTag = null;
			Stack<String> tagStack = new Stack<String>();

			// 스택 사용 여부! 값을 바꿔서 실행해 보기소
			// <word>aaa<any>any text</any>bbb</word>에서 bbb의 결과를 확인해 보세요.
			boolean useStack = true;

			// 처리하고자 하는 TAG 등록
			List<String> tagList = Arrays.asList(new String[] { "number", "word", "mean", "any" });

			// XML 파서
			XmlPullParser parser = getResources().getXml(R.xml.test);

			// 파싱 시작
			while ((event = parser.next()) != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					// 시작 태그를 만나면 currentTag에 기록
					if (useStack && currentTag != null) {
						tagStack.push(currentTag);
					}
					currentTag = parser.getName();
					break;
				case XmlPullParser.TEXT:
					// currentTag가 처리하고자 하는 태그이면...
					if (currentTag != null && tagList.contains(currentTag)) {
						String value = parser.getText();

						if (currentTag.equals("number")) {
							aNumber.add(value);
						} else if (currentTag.equals("word")) {
							aWord.add(value);
						} else if (currentTag.equals("mean")) {
							aMean.add(value);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					// 종료 태그를 만나면 currentTag 초기화
					if (useStack && tagStack.size() > 0) {
						currentTag = tagStack.pop();
					} else {
						currentTag = null;
					}
					break;
				default:
					break;
				}
			}

			for (int i = 0; i < aNumber.size(); i++) {
				Log.d("gibom", "number : " + aNumber.get(i));
				Log.d("gibom", "word : " + aWord.get(i));
				Log.d("gibom", "mean : " + aMean.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
