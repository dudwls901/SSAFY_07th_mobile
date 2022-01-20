package com.ssafy.hw04.step3;

public interface IProductMgr {
	
	//삽입
	void add(Product product);

	//삭제
	void remove(String productNum) throws EmptyProductException;
	
	//전체 상품 반환
	Product[] getProducts();
	
	//상품 번호로 검색 후 리스트 반환
	Product searchByProductNum(String productNum);
	
	//상품명으로 검색 후 리스트 반환
	//contains로 포함 구현
	Product[] searchByProductName(String productName);
	
	//TV만 반환
	Product[] getTVs();
	
	//냉장고만 반환
	Product[] getRefrigerators();
	
	//냉장고 용량으로 검색
	Product[] searchByCapacityRefris(int capacity);
	
	//티비 사이즈로 검색
	Product[] searchByInchTVs(int inch);
	
	//가격 변경
	void change(String productNum, int price) throws EmptyProductException, MinusPriceException;
	
	//전체 상품 가격
	int getTotalPrice();
	
	void saveData();
}
