package com.ssafy.hw.step3;

public class ProductTest {

	public static void main(String[] args) {
		ProductMgr productMgr = new ProductMgr();

		//추가
		productMgr.add(new TV("1", "삼성 테레비", 50000, 6, 25, "LCD"));
		productMgr.add(new TV("2", "엘지 테레비", 250000, 1, 50, "QLED"));
		productMgr.add(new TV("3", "영진 테레비", 550000, 2, 72, "OLED"));
		productMgr.add(new Refrigerator("4", "비스포크 냉장고", 70000, 5, 500));
		productMgr.add(new Refrigerator("5", "정윤 냉장고", 330000, 1, 700));
		
		//삭제
		productMgr.remove("3");

		//추가
		productMgr.add(new TV("6", "영진 텔레비죤", 1550000, 1, 64, "QLED"));
		productMgr.add(new TV("7", "영진 tv", 755000, 1, 72, "QLED"));
		
		// 전체 상품 출력
		for (Product product : productMgr.getProducts()) {
			System.out.println(product.toString());
		}
		System.out.println();
		
		// 상품번호 검색 출력
		System.out.println(productMgr.searchByProductNum("5").toString());

		// 상품명으로 검색 출력
		for (Product product : productMgr.searchByProductName("영진")) {
			System.out.println(product.toString());
		}
		System.out.println();
		
		// TV 전체 출력
		for (Product product : productMgr.getTVs()) {
			System.out.println(product.toString());
		}
		System.out.println();
		
		// 냉장고 전체 출력
		for (Product product : productMgr.getRefrigerators()) {
			System.out.println(product.toString());
		}
		System.out.println();
		
		// 전체 재고 상품 금액 출력
		System.out.println(productMgr.getTotalPrice());
	}
}
