package com.ssafy.ws04.step3;

import java.util.ArrayList;

public class BookTest {
	public static void main(String[] args) {

		//생성과 동시에 파일 데이터 로드
		BookManagerImpl bookManager = BookManagerImpl.getInstance();
		
		
		//파일에 넣어놓기
		// add
//		bookManager.add(new Book("21424", "Java Pro", "김정윤", "jaen.kr", 15000, "Java 기본 문법", 10));
//		// 삭제 테스트
//		bookManager.remove("21424");
//		bookManager.add(new Book("21424", "Java Pro", "김정윤", "jaen.kr", 15000, "Java 기본 문법", 10));
//
//		bookManager.add(new Book("21425", "Java Pro2", "김정윤", "jaen.kr", 25000, "Java 응용", 20));
//		bookManager.add(new Book("35355", "분석설계", "소나무", "jaen.kr", 30000, "SW 모델링", 30));
//		bookManager.add(new Magazine("45678", "월간 알고리즘", "홍길동", "jaen.kr", 10000, "1월 알고리즘", 40, 2021, 1));
		
		
		//파일에 데이터 저장
		bookManager.saveData();

		//파일 정보 불러오기
		bookManager.start();
	}
}
