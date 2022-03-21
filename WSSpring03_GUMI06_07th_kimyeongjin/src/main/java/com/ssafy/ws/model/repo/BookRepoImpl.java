package com.ssafy.ws.model.repo;

import java.util.ArrayList;
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
		List<Book> books = new ArrayList<>(); 
		books.add(new Book("111-222-3333", "홍길동", "책제목 1", 10000, "좋은 책 1", "abc1.png")); 
		books.add(new Book("111-222-4444", "임꺽정", "책제목 2", 20000, "좋은 책 2", "abc2.png")); 
		books.add(new Book("111-333-4444", "장길산", "책제목 3", 30000, "좋은 책 3", "abc3.png"));
		return books;
	}
	
	
}
