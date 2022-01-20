package com.ssafy.hw04.step3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ProductMgrImpl extends Thread implements IProductMgr{

	//싱글톤
	private static ProductMgrImpl productMgrImpl;
	
	private ArrayList<Product> products = new ArrayList<Product>();
	
	//생성자 외부 차단
	private ProductMgrImpl() {}
	
	//인스턴스 반환
	public static ProductMgrImpl getInstance() {
		if(productMgrImpl==null) {
			productMgrImpl = new ProductMgrImpl(); 
		}
		
		//파일이 없는 경우 예외 처리
		//가져올 데이터가 없는 경우 예외 처리
		productMgrImpl.loadData();
		
		return productMgrImpl;
	}
	
	
	@Override
	public void saveData() {
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("product.dat");
			oos = new ObjectOutputStream(fos);

			for (Product product : products) {
				oos.writeObject(product);
			}
			// EOFException 방지
			oos.writeObject(null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("객체 정보를 파일에 저장 완료");
	}
	
	@Override
	public void run() {
		productMgrImpl.loadData();
	}
	
	 void loadData() {
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream("product.dat");
			ois = new ObjectInputStream(fis);

			Object p = null;
			System.out.println("파일 정보 로드 완료");
			System.out.println("********************************상품 전체 목록********************************");
			while ((p = ois.readObject()) != null) {
				products.add((Product) p);
				System.out.println(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	//삽입
	@Override
	public void add(Product product) {
		products.add(product);
	}

	//삭제
	@Override
	public void remove(String productNum) throws EmptyProductException {
		
		try {	
			//productNum으로 삭제할 인덱스 찾기
			for(int i=0; i<products.size();i++) {
				if(products.get(i).getProductNum().equals(productNum)) {
					System.out.println("********************************삭제된 상품********************************");
					System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    용량       디스플레이 타입");
					System.out.println("________________________________________________________________________");
					System.out.println(products.get(i));
					products.remove(i);
					return;
				}
			}
			throw new EmptyProductException(productNum);
		}catch(EmptyProductException e){
			e.printError();
		}
	}
	
	//전체 상품 반환
	@Override
	public Product[] getProducts() {
		System.out.println("********************************상품 전체 목록********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    인치 /용량    디스플레이 타입");
		System.out.println("________________________________________________________________________");
		
		return products.toArray(new Product[products.size()]);
	}
	
	//상품 번호로 검색 후 상품  반환
	@Override
	public Product searchByProductNum(String productNum) {
		System.out.println("********************************상품 번호로 검색  : "+productNum+"********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    인치 /용량    디스플레이 타입");
		System.out.println("________________________________________________________________________");
		for(Product product : products) {
			if(product.getProductNum().equals(productNum)) {
				return product;
			}
		}
		//상품이 없는 경우 빈 상품 반환
		System.out.println("해당상품이 없어요.");
		return new Product();
	}
	
	//상품명으로 검색 후 리스트 반환
	//contains로 포함 구현
	@Override
	public Product[] searchByProductName(String productName) {
		System.out.println("********************************상품명 포함 검색  : "+productName+"********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    인치 /용량    디스플레이 타입");
		System.out.println("________________________________________________________________________");
		ArrayList<Product> result = new ArrayList<Product>();
		
		for(Product product : products) {
			if(product.getProductName().contains(productName)) {
				result.add(product);
			}
		}
		return result.toArray(new Product[result.size()]);
	}
	
	//TV만 반환
	@Override
	public Product[] getTVs() {
		System.out.println("********************************TV 목록********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    인치       디스플레이 타입");
		System.out.println("________________________________________________________________________");
		ArrayList<Product> result = new ArrayList<Product>();

		for(Product product : products) {
			if(product instanceof TV) {
				result.add(product);
			}
		}
		return result.toArray(new Product[result.size()]);
	}
	
	//냉장고만 반환
	@Override
	public Product[] getRefrigerators() {
		System.out.println("********************************냉장고 목록********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    용량       디스플레이 타입");
		System.out.println("________________________________________________________________________");
		ArrayList<Product> result = new ArrayList<Product>();
		
		for(Product product : products) {
			if(product instanceof Refrigerator) {
				result.add(product);
			}
		}
		
		return result.toArray(new Product[result.size()]);
	}
	
	//capacity 이상 냉장고만 반환
	@Override
	public Product[] searchByCapacityRefris(int capacity) {
		System.out.println("********************************"+capacity+"L 이상 냉장고 목록********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    용량       디스플레이 타입");
		System.out.println("________________________________________________________________________");
		ArrayList<Product> result = new ArrayList<Product>();
		
		for(Product product : products) {
			if(product instanceof Refrigerator) {
				if(((Refrigerator) product).getCapacity()>=capacity) {
					result.add(product);
				}
			}
		}
		
		return result.toArray(new Product[result.size()]);
	}

	//inch 이상 티비만 반환
	@Override
	public Product[] searchByInchTVs(int inch) {
		System.out.println("********************************"+inch+"inch 이상 티비 목록********************************");
		System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    용량       디스플레이 타입");
		System.out.println("________________________________________________________________________");
		ArrayList<Product> result = new ArrayList<Product>();

		for(Product product : products) {
			if(product instanceof TV) {
				if(((TV) product).getInch()>=inch) {
					result.add(product);
				}
			}
		}
		return result.toArray(new Product[result.size()]);
	}

	//상품번호로 상품 가격 변경
	@Override
	public void change(String productNum, int price) throws EmptyProductException, MinusPriceException {
		try {
			for(Product product : products) {
				if(product.getProductNum().equals(productNum)) {
					if(price<0) {
						throw new MinusPriceException();
					}
					product.setPrice(price);
					System.out.println("********************************변경된 상품********************************");
					System.out.println("  제품 번호	     제품 이름	     가격 		     수량	    용량       디스플레이 타입");
					System.out.println("________________________________________________________________________");
					System.out.println(product);
					return;
				}
			}
			throw new EmptyProductException(productNum);
		}catch(MinusPriceException e) {
			e.printError();
		}
		catch(EmptyProductException e) {
			e.printError();
		}
	}
	
	//전체 상품 가격
	@Override
	public int getTotalPrice() {
		System.out.printf("전체 상품 가격 총합 : ");
		int sum=0;
		for(Product product : products) {
			sum += product.getPrice()*product.getCnt();
		}
		return sum;
	}


	
	
}
