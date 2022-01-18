package com.ssafy.ws05.step3;

public class Book {
	
	private String isBn;
	private String title;
	private String author;
	private String publisher;
	private int price;
	private String desc;
	
	//생성자 오버로딩
	//기본 생성자
	//생성자 따로 구현하면 디폴트 기본 생성자는 안 만들어주기 때문에 직접 만들기
	public Book() {}
	
	//부 생성자
	public Book(String isBn, String title, String author, String publisher, int price, String desc) {
		super();
		this.isBn = isBn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.desc = desc;
	}

	//게터 세터
	public String getIsBn() {
		return isBn;
	}

	public void setIsBn(String isBn) {
		this.isBn = isBn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(isBn);
		builder.append(" 	 | ");
		builder.append(title);
		builder.append("	 | ");
		builder.append(author);
		builder.append("	 | ");
		builder.append(publisher);
		builder.append("	 | ");
		builder.append(price);
		builder.append("	 | ");
		builder.append(desc);
		return builder.toString();
	}
	
}
