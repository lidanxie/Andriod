package com.example.entity;

public class Share {
	private int ico;//头像
	private String username;//用户名
	private String words;//文字
	private int img;//图片
	private String img_path;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public int getIco() {
		return ico;
	}
	public void setIco(int ico) {
		this.ico = ico;
	}
	public int getImg() {
		return img;
	}
	public void setImg(int img) {
		this.img = img;
	}
	public void setImgPath(String img_path) {
		this.img_path = img_path;
	}
	public String getImgPath() {
		return img_path;
	}
	
}
