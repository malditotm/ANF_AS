package com.example.anf.entity;

public class Button {
	String target;
	String title;
	
	public Button() {
	}
	
	public Button(String target, String title) {
		this.target = target;
		this.title = title;
	}
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
