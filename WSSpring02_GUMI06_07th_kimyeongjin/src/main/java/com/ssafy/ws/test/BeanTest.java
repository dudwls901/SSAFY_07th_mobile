package com.ssafy.ws.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.ssafy.ws.dto.Book;
import com.ssafy.ws.model.repo.BookRepo;
import com.ssafy.ws.model.repo.UserRepo;
import com.ssafy.ws.model.service.BookService;
import com.ssafy.ws.model.service.UserService;

public class BeanTest {
	
	public static void main(String[] args) {
		
		ApplicationContext context = new GenericXmlApplicationContext("application.xml");
		
		UserService userService = context.getBean(UserService.class);
		BookService bookService = context.getBean(BookService.class);
		UserRepo userRepo = context.getBean(UserRepo.class);
		BookRepo bookRepo = context.getBean(BookRepo.class);
			
		bookService.insert(new Book());
		userService.select("0");

	}
}
