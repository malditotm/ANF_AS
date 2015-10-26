package com.example.anf.entity;

import android.graphics.Bitmap;

public class Promotion {
	long id;
	Button button;
	String description;
	String footer;
	String image;
	String title;
	
	Bitmap imageBtmp;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Bitmap getImageBtmp() {
		return imageBtmp;
	}
	public void setImageBtmp(Bitmap imageBtmp) {
		this.imageBtmp = imageBtmp;
	}
}
