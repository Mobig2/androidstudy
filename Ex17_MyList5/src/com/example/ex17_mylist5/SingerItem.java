package com.example.ex17_mylist5;

public class SingerItem {
	
	private String name;
	
	private String age;
	
	private int resId;
	
	public SingerItem(String name, String age, int resId) {
		this.name = name;
		this.age = age;
		this.resId = resId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name =name;
	}
	
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
	public int getResId() {
		return resId;
	}
	
	public void setResId(int resId){
		this.resId = resId;
	}

}
