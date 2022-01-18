package com.ssafy.hw.step3;

public class Refrigerator extends Product{

	//캡슐화
	private int capacity;

	
	//초기화용
	public Refrigerator() {
		super();
	}

	//부 생성자
	public Refrigerator(String productNum, String productName, int price, int cnt, int capacity) {
		super(productNum, productName, price, cnt);
		this.capacity = capacity;
	}

	//게터세터
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.getProductNum());
		builder.append("	 | ");
		builder.append(super.getProductName());
		builder.append("	 | ");
		builder.append(super.getPrice());
		builder.append("	 | ");
		builder.append(super.getCnt());
		builder.append("	 | ");
		builder.append(capacity);
		return builder.toString();
	}
	
	
	
}
