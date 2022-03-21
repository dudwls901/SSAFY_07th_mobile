package com.ssafy.ws.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.ssafy.ws.dto.Book;
import com.ssafy.ws.dto.User;
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
		
		nullCheck(userService, UserService.class.getSimpleName());
		nullCheck(bookService, BookService.class.getSimpleName());
		nullCheck(userRepo, UserRepo.class.getSimpleName());
		nullCheck(bookRepo, BookRepo.class.getSimpleName());
		
		bookService.insert(new Book());
		userService.select("0");
	}
	
	static void nullCheck(Object obj, String className) {
		
		if (obj != null) {
			String name = obj.getClass().getSimpleName();
			System.out.println("get " + name + " !!!");
		}
		else {
			System.out.println("fail " + className + " !!!");
		}
		
	}
}