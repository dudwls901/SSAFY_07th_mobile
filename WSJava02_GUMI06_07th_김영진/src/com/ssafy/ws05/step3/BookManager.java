package com.ssafy.ws05.step3;

import java.util.ArrayList;
import java.util.Arrays;

public class BookManager {
	
	public static final int MAX_SIZE = 100; 
	private Book[] books = new Book[MAX_SIZE];
	private int size=0;
	
	public void add(Book book) {
		books[size++]=book;
	}
	
	public void remove(String isbn) {
		if(size==0) 
			System.out.println("책을 먼저 추가해주세용.");
		else
		{
			//isbn으로 삭제할 인덱스 찾기
			for(int i=0; i<size;i++) {
				if(books[i].getIsBn().equals(isbn)) {
					//삭제할 인덱스부터 한 칸씩 땡기기
					for(int j = i; j<size-1;j++) {
						books[j]=books[j+1];
					}
				}
			}
			//현재 리스트의 마지막 칸 초기화 및 사이즈 -1
			books[--size] = new Book();
		}
			
	}
	
	public Book[] getList() {
		System.out.println("********************************도서 전체 목록********************************");
		return Arrays.copyOf(books, size);
	}
	
	public Book searchByisbn(String isbn) {
		System.out.println("********************************고유번호로 도서 검색  : "+isbn+"********************************");
		for(int i=0; i<size; i++) {
			if(books[i].getIsBn().equals(isbn)) {
				return books[i];
			}
		}
		//도서가 없는 경우 빈 도서 반환
		System.out.println("해당 도서가 없어요.");
		return new Book();
	}
	
	public Book[] searchByTitle(String title) {
		System.out.println("********************************도서 제목 포함검색  : "+title+"********************************");
		ArrayList<Book> result = new ArrayList<Book>();
		
		for(int i=0; i<size; i++) {
			if(books[i].getTitle().contains(title)) {
				result.add(books[i]);
			}
		}
		return result.toArray(new Book[result.size()]);
	}
	
	public Book[] getBooks() {
		System.out.println("********************************일반 도서 목록********************************");
		ArrayList<Book> result = new ArrayList<Book>();

		for(int i=0; i<size; i++) {
			if(!(books[i] instanceof Magazine)) {
				result.add(books[i]);
			}
		}
		return result.toArray(new Book[result.size()]);
	}
	
	public Book[] getMagazines() {
		System.out.println("********************************잡지 목록********************************");
		ArrayList<Book> result = new ArrayList<Book>();
		
		for(int i=0; i<size; i++) {
			if(books[i] instanceof Magazine) {
				result.add(books[i]);
			}
		}
		
		return result.toArray(new Book[result.size()]);
	}
	
	public int getTotalPrice() {
		System.out.printf("도서 가격 총합 : ");
		int sum=0;
		for(int i=0; i<size; i++) {
			sum += books[i].getPrice();
		}
		return sum;
	}
	
	public double getPriceAvg() {
		System.out.printf("도서 가격 평균 : ");
		double avg=0;
		for(int i=0; i<size; i++) {
			avg+= books[i].getPrice();
		}
		return avg/size;
	}
}
