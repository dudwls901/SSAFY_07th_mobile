package com.ssafy.ws.model.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.ws.dto.Book;

@Repository
public class BookRepoImpl implements BookRepo {
	
	public BookRepoImpl() {}
	
	@Override
	public int insert(Book book) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Book book) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String isbn) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Book select(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> search() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
