package com.ssafy.hw03.step3;

public class Product {

	//캡슐화
	private String productNum;
	private String productName;
	private int price;
	private int cnt;
	
	//기본 생성자(초기화용)
	public Product() {}
	
	//부 생성자
	public Product(String productNum, String productName, int price, int cnt) {
		this.productNum = productNum;
		this.productName = productName;
		this.price = price;
		this.cnt = cnt;
	}

	//게터 세터
	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	
}
