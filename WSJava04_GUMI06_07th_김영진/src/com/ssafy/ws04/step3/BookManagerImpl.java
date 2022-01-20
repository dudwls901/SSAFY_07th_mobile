package com.ssafy.ws04.step3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookManagerImpl extends Thread implements IBookManager {

	// 싱글톤 객체 저장할 변수
	private static BookManagerImpl bookManagerImpl;

	private List<Book> books = new ArrayList<Book>();

	// 싱글톤 객체 생성자 외부 차단
	private BookManagerImpl() {
	}

	// 싱글톤 객체 첫 생성, 사용 메서드
	// 객체 생성시 자동으로 데이터 읽어오기
	public static BookManagerImpl getInstance() {
		if (bookManagerImpl == null) {
			bookManagerImpl = new BookManagerImpl();
		}

		//파일 없을 때 호출하면 예외처리
		//가져올 데이터가 없는 경우 예외 처리
		bookManagerImpl.loadData();

		return bookManagerImpl;
	}

	@Override
	public void run() {
		// 파일의 데이터 읽어오기
		loadData();
	}

	// 데이터 저장하기
	@Override
	public void saveData() {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("book.dat");
			oos = new ObjectOutputStream(fos);

			for (Book book : books) {
				oos.writeObject(book);
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

	// 데이터 불러오기
	// 데이터 파일 없을 경우 에러 반환
	// Thread로 구현
	private void loadData() {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream("book.dat");
			ois = new ObjectInputStream(fis);

			Object p = null;
			System.out.println("파일 정보 로드 완료");
			System.out.println("********************************도서 전체 목록********************************");
			while ((p = ois.readObject()) != null) {
				books.add((Book) p);
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

	@Override
	public void add(Book book) {
		books.add(book);
	}

	@Override
	public void remove(String isbn) {
		if (books.isEmpty()) {
			System.out.println("책을 먼저 추가해주세용.");
		} else {
			for (int i = 0; i < books.size(); i++) {
				if (books.get(i).getIsBn().contains(isbn)) {
					books.remove(i);
					return;
				}
			}
		}
	}

	@Override
	public Book[] getList() {
		System.out.println("********************************도서 전체 목록********************************");
		return books.toArray(new Book[books.size()]);
	}

	// 인자 추가할 수 없어서 그냥 buy sell에 직접 searchByisBn 로직 넣기..
	@Override
	public Book searchByisbn(String isbn) {
		
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getIsBn() == isbn) {
				return books.get(i);
			}
		}
		return new Book();
	}

	@Override
	public Book[] searchByTitle(String title) {
		System.out
				.println("********************************도서 제목 포함검색  : " + title + "********************************");
		ArrayList<Book> result = new ArrayList<Book>();

		for (Book book : books) {
			if (book.getTitle().contains(title)) {
				System.out.println(title + book);
				result.add(book);
			}
		}
		return result.toArray(new Book[result.size()]);
	}

	@Override
	public Book[] getBooks() {
		System.out.println("********************************일반 도서 목록********************************");
		ArrayList<Book> result = new ArrayList<Book>();

		for (Book book : books) {
			if (!(book instanceof Magazine)) {
				result.add(book);
			}
		}
		return result.toArray(new Book[result.size()]);
	}

	@Override
	public Book[] getMagazines() {
		System.out.println("********************************잡지 목록********************************");
		ArrayList<Book> result = new ArrayList<Book>();

		for (Book book : books) {
			if (book instanceof Magazine) {
				result.add(book);
			}
		}
		return result.toArray(new Book[result.size()]);
	}

	@Override
	public int getTotalPrice() {
		System.out.printf("도서 가격 총합 : ");
		int sum = 0;
		for (Book book : books) {
			sum += book.getPrice();
		}
		return sum;
	}

	@Override
	public double getPriceAvg() {
		System.out.printf("도서 가격 평균 : ");
		double avg = 0;
		for (Book book : books) {
			avg += book.getPrice();
		}
		return avg / books.size();
	}

	@Override
	public void sell(String isbn, int quantity) {
		try {

			for (int i = 0; i < books.size(); i++) {
				if (books.get(i).getIsBn() == isbn) {
					System.out.println("********************************도서판매 : " + isbn + "," + quantity
							+ "개********************************");
					// result가 있음을 보장, 재고가 부족하다면 catch로
					books.get(i).setQuantity(books.get(i).getQuantity() - quantity);
					System.out.println(books.get(i));
					return;
				}
			}
			throw new ISBNNotFoundException(isbn);

		} catch (QuantityException e) {
			System.out.println("수량이 부족합니다.");
		} catch (ISBNNotFoundException e) {
			System.out.println(isbn + "도서가 존재하지 않습니다.");
		}

	}

	@Override
	public void buy(String isbn, int quantity) {
		try {

			for (int i = 0; i < books.size(); i++) {
				if (books.get(i).getIsBn() == isbn) {
					System.out.println("********************************도서구매 : " + isbn + "," + quantity
							+ "개********************************");
					// 구매한 경우 책이 추가되는듯
					books.get(i).setQuantity(books.get(i).getQuantity() + quantity);
					System.out.println(books.get(i));
					return;
				}
			}
			throw new ISBNNotFoundException(isbn);

		} catch (QuantityException e) {
			System.out.println("수량이 부족합니다.");
		} catch (ISBNNotFoundException e) {
			System.out.println(isbn + "도서가 존재하지 않습니다.");
		}

	}

}
