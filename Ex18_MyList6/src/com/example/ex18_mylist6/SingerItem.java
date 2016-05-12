package com.example.ex18_mylist6;

public class SingerItem {
	
	private String name;
	
	private String telnum;
	
	private int resId;
	
	public SingerItem(String name, String age, int resId) {
		this.name = name;
		this.telnum = age;
		this.resId = resId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name =name;
	}
	
	public String getTelnum() {
		return telnum;
	}
	
	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}
	
	public int getResId() {
		return resId;
	}
	
	public void setResId(int resId){
		this.resId = resId;
	}

}
