package com.ssafy.ws07.step3;

public class ISBNNotFoundException extends Exception {
	String isbn;

	public ISBNNotFoundException(Throwable t) {
		super(t);
	}
	public ISBNNotFoundException(String isbn) {
		super(isbn);
	}
	public String getIsbn() {
		return isbn;
	}
}
