package com.ssafy.ws07.step3;

import java.util.ArrayList;
import java.util.List;

public interface IBookManager {

	void add(Book book);

	void remove(String isbn);

	Book[] getList();

	Book searchByisbn(String isbn) throws ISBNNotFoundException;

	Book[] searchByTitle(String title);

	Book[] getBooks();

	Book[] getMagazines();

	int getTotalPrice();

	double getPriceAvg();

	void sell(String isbn, int quantity);

	void buy(String isbn, int quantity);
}
