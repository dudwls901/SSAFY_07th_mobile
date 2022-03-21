package com.ssafy.ws.model.repo;

import java.util.List;

import com.ssafy.ws.dto.Book;

public interface BookRepo {
	
	int insert(Book book);
	
	int update(Book book);
	
	int delete(String isbn);
	
	Book select(String isbn);
	
	List<Book> search();
}
