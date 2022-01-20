package com.ssafy.hw04.step3;

public class ProductTest {

	public static void main(String[] args) {
		ProductMgrImpl productMgr = ProductMgrImpl.getInstance();

		
		//추가
//		productMgr.add(new TV("1", "삼성 테레비", 50000, 6, 25, "LCD"));
//		productMgr.add(new TV("2", "엘지 테레비", 250000, 1, 50, "QLED"));
//		productMgr.add(new TV("3", "영진 테레비", 550000, 2, 72, "OLED"));
//		productMgr.add(new Refrigerator("4", "비스포크 냉장고", 70000, 5, 300));
//		productMgr.add(new Refrigerator("5", "정윤 냉장고", 330000, 1, 700));
		
		//파일에 정보 저장
		productMgr.saveData();
		
		//파일 정보 불러오기
		productMgr.loadData();
		
	}
}
