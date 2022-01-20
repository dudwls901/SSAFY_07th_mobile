package com.ssafy.hw04.step3;

public class MinusPriceException extends Exception {

	public MinusPriceException() {
		super();
	}
	
	public void printError() {
		System.out.println("가격을 0원 이상으로 해주세요.");
	}
}
