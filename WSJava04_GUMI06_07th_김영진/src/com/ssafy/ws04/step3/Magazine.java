package com.ssafy.ws04.step3;

public class Magazine extends Book {
	private int year;
	private int month;

	// 기본 생성자
	public Magazine() {
		super();
	}

	// 부 생성자
	// 첫 줄에 부모 생성자 호출
	public Magazine(String isBn, String title, String author, String publisher, int price, String desc, int quantity,
			int year, int month) {
		super(isBn, title, author, publisher, price, desc, quantity);
		this.year = year;
		this.month = month;
	}

	// 게터 세터
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.getIsBn());
		builder.append("	 | ");
		builder.append(super.getTitle());
		builder.append("	 | ");
		builder.append(super.getAuthor());
		builder.append("	 | ");
		builder.append(super.getPublisher());
		builder.append("	 | ");
		builder.append(super.getPrice());
		builder.append("	 | ");
		builder.append(super.getDesc());
		builder.append("	 | ");
		builder.append(super.getQuantity());
		builder.append("	 | ");
		builder.append(year);
		builder.append("	 | ");
		builder.append(month);
		return builder.toString();
	}

}
