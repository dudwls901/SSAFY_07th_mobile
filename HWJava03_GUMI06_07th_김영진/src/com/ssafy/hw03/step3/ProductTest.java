package com.ssafy.hw03.step3;

public class ProductTest {

	public static void main(String[] args) {
		ProductMgrImpl productMgr = ProductMgrImpl.getInstance();

		//삭제
		try {
			productMgr.remove("3");
		}catch(EmptyProductException e){
			e.printError();
		}
		System.out.println();
		
		//추가
		productMgr.add(new TV("1", "삼성 테레비", 50000, 6, 25, "LCD"));
		productMgr.add(new TV("2", "엘지 테레비", 250000, 1, 50, "QLED"));
		productMgr.add(new TV("3", "영진 테레비", 550000, 2, 72, "OLED"));
		productMgr.add(new Refrigerator("4", "비스포크 냉장고", 70000, 5, 300));
		productMgr.add(new Refrigerator("5", "정윤 냉장고", 330000, 1, 700));
		
		//삭제
		try {
			productMgr.remove("3");
		}catch(EmptyProductException e){
			e.printError();
		}
		System.out.println();
		
		//추가
		productMgr.add(new TV("6", "영진 텔레비죤", 1550000, 1, 64, "QLED"));
		productMgr.add(new TV("7", "영진 tv", 755000, 1, 72, "QLED"));
		productMgr.add(new Refrigerator("8", "영진 냉장고", 430000, 1, 900));
		
		// 전체 상품 출력
		for (Product product : productMgr.getProducts()) {
			System.out.println(product);
		}
		System.out.println();
		
		// 상품번호 검색 출력
		System.out.println(productMgr.searchByProductNum("5").toString());

		// 상품명으로 검색 출력
		for (Product product : productMgr.searchByProductName("영진")) {
			System.out.println(product);
		}
		System.out.println();
		
		// TV 전체 출력
		for (Product product : productMgr.getTVs()) {
			System.out.println(product);
		}
		System.out.println();
		
		// 냉장고 전체 출력
		for (Product product : productMgr.getRefrigerators()) {
			System.out.println(product);
		}
		System.out.println();
		
		
		//TODO 400L이상 냉장고 검색
		for (Product product : productMgr.searchByCapacityRefris(400)) {
			System.out.println(product);
		}
		System.out.println();
		
		
		//TODO 50inch이상  티비 검색
		for (Product product : productMgr.searchByInchTVs(50)) {
			System.out.println(product);
		}
		System.out.println();
		
		//TODO 상품번호랑 가격을 입력받아 가격을 변경
		try {
			productMgr.change("4", -1);
		}catch(MinusPriceException e) {
			e.printError();
		}catch(EmptyProductException e) {
			e.printError();
		}
		System.out.println();

		try {
			productMgr.change("4", 10000000);
		}catch(MinusPriceException e) {
			e.printError();
		}catch(EmptyProductException e) {
			e.printError();
		}
		System.out.println();
		
		// 전체 재고 상품 금액 출력
		System.out.println(productMgr.getTotalPrice());
	}
}
