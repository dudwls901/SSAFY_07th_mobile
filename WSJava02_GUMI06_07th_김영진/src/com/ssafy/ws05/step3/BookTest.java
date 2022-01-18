package com.ssafy.ws05.step3;

public class BookTest {
	public static void main(String[] args) {
		BookManager bookManager = new BookManager();
		
		//add
		bookManager.add(new Book("21424", "Java Pro", "김정윤", "jaen.kr",15000, "Java 기본 문법"));
		//삭제 테스트
//		bookManager.remove("21424");
		bookManager.add(new Book("21425", "Java Pro2", "김정윤", "jaen.kr",25000, "Java 응용"));
		bookManager.add(new Book("35355", "분석설계", "소나무", "jaen.kr",30000, "SW 모델링"));
		bookManager.add(new Magazine("45678", "월간 알고리즘", "홍길동", "jaen.kr",15000, "1월 알고리즘", 2021, 1));
		//삭제 테스트
//		bookManager.add(new Book("21424", "Java Pro", "김정윤", "jaen.kr",15000, "Java 기본 문법"));
		
		//전체 도서 출력
		for(Book book : bookManager.getList()) {
			System.out.println(book.toString());
		}
		
		//일반 도서 출력
		for(Book book : bookManager.getBooks()) {
			System.out.println(book.toString());
		}
		
		//잡지 출력
		for(Book book : bookManager.getMagazines()) {
			System.out.println(book.toString());
		}		
		//제목 검색 출력
		for(Book book : bookManager.searchByTitle("Pro")) {
			System.out.println(book.toString());
		}
		
		//고유 번호 검색 출력
		System.out.println(bookManager.searchByisbn("35355").toString());
		
		//도서 가격 총합 출력
		System.out.println(bookManager.getTotalPrice());
		
		//도서 가격 평균 출력
		System.out.println(bookManager.getPriceAvg());
				
	}
}
