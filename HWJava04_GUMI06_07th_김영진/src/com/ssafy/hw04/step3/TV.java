package com.ssafy.hw04.step3;

public class TV extends Product {

	//캡슐화
	private int inch;
	private String type;
	
	//초기화용
	public TV() {
		super();
	}

	//부 생성자
	public TV(String productNum, String productName, int price, int cnt, int inch, String type) {
		super(productNum, productName, price, cnt);
		this.inch = inch;
		this.type = type;
	}

	//게터세터
	public int getInch() {
		return inch;
	}

	public void setInch(int inch) {
		this.inch = inch;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		builder.append(inch);
		builder.append("	 | ");
		builder.append(type);
		return builder.toString();
	}
	
	
}
