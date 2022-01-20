package com.ssafy.hw04.step3;

public class EmptyProductException extends Exception {

	private String productNum;
	
	public EmptyProductException() {
		super();
	}
	public EmptyProductException(String productNum) {
		super();
		this.productNum= productNum;
	}
	
	public void printError() {
		System.out.println( productNum + " 상품이 존재하지 않습니다.");
	}
}
