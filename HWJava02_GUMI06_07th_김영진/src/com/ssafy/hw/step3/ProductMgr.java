package com.ssafy.hw.step3;

import java.util.ArrayList;
import java.util.Arrays;


public class ProductMgr {

//	private ArrayList<Product> products = new ArrayList<Product>();
	//과제 조건 객체 배열! 사용...
	//MAX_SIZE 임의 설정
	public static final int MAX_SIZE = 100;
	private Product[] products = new Product[MAX_SIZE];
	private int size=0;
	
	
	//삽입
	public void add(Product product) {
		products[size++]=product;
	}

	//삭제
	public void remove(String productNum) {
		if(size==0) 
			System.out.println("상품을 먼저 추가해주세용.");
		else
		{
			//productNum으로 삭제할 인덱스 찾기
			for(int i=0; i<size;i++) {
				if(products[i].getProductNum().equals(productNum)) {
					//삭제할 인덱스부터 한 칸씩 땡기기
					for(int j = i; j<size-1;j++) {
						products[j]=products[j+1];
					}
				}
			}
			//현재 리스트의 마지막 칸 초기화 및 사이즈 -1
			products[--size] = new Product();
		}
			
	}
	
	//전체 상품 반환
	public Product[] getProducts() {
		System.out.println("********************************상품 전체 목록********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    인치 /용량    디스플레이 타입");
		System.out.println("________________________________________________________________________");
		return Arrays.copyOf(products, size);
	}
	
	//상품 번호로 검색 후 리스트 반환
	public Product searchByProductNum(String productNum) {
		System.out.println("********************************상품 번호로 검색  : "+productNum+"********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    인치 /용량    디스플레이 타입");
		System.out.println("________________________________________________________________________");
		for(int i=0; i<size; i++) {
			if(products[i].getProductNum().equals(productNum)) {
				return products[i];
			}
		}
		//상품이 없는 경우 빈 상품 반환
		System.out.println("해당상품이 없어요.");
		return new Product();
	}
	
	//상품명으로 검색 후 리스트 반환
	//contains로 포함 구현
	public Product[] searchByProductName(String productName) {
		System.out.println("********************************상품명 포함 검색  : "+productName+"********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    인치 /용량    디스플레이 타입");
		System.out.println("________________________________________________________________________");
		ArrayList<Product> result = new ArrayList<Product>();
		
		for(int i=0; i<size; i++) {
			if(products[i].getProductName().contains(productName)) {
				result.add(products[i]);
			}
		}
		return result.toArray(new Product[result.size()]);
	}
	
	//TV만 반환
	public Product[] getTVs() {
		System.out.println("********************************TV 목록********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    인치       디스플레이 타입");
		System.out.println("________________________________________________________________________");
		ArrayList<Product> result = new ArrayList<Product>();

		for(int i=0; i<size; i++) {
			if(products[i] instanceof TV) {
				result.add(products[i]);
			}
		}
		return result.toArray(new Product[result.size()]);
	}
	
	//냉장고만 반환
	public Product[] getRefrigerators() {
		System.out.println("********************************냉장고 목록********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    용량       디스플레이 타입");
		System.out.println("________________________________________________________________________");
		ArrayList<Product> result = new ArrayList<Product>();
		
		for(int i=0; i<size; i++) {
			if(products[i] instanceof Refrigerator) {
				result.add(products[i]);
			}
		}
		
		return result.toArray(new Product[result.size()]);
	}
	
	//전체 상품 가격
	public int getTotalPrice() {
		System.out.printf("전체 상품 가격 총합 : ");
		int sum=0;
		for(int i=0; i<size; i++) {
			sum += products[i].getPrice()*products[i].getCnt();
		}
		return sum;
	}
	
	
}
