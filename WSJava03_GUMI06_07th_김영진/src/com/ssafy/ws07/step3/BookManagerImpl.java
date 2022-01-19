package com.ssafy.ws07.step3;

import java.util.ArrayList;
import java.util.List;

public class BookManagerImpl implements IBookManager {

	// 싱글톤 객체 저장할 변수
	private static BookManagerImpl bookManagerImpl;

	private List<Book> books = new ArrayList<Book>();

	// 싱글톤 객체 생성자 외부 차단
	private BookManagerImpl() {
	}

	// 싱글톤 객체 첫 생성, 사용 메서드
	public static BookManagerImpl getInstance() {
		if (bookManagerImpl == null) {
			bookManagerImpl = new BookManagerImpl();
		}
		return bookManagerImpl;
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
					System.out.println("********************************도서판매 : " + isbn + "," + quantity + "개********************************");
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
					System.out.println("********************************도서구매 : " + isbn + "," + quantity + "개********************************");
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
