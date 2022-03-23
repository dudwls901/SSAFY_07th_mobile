package com.ssafy.ws.controller;

import java.net.BindException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.ws.dto.Book;
import com.ssafy.ws.dto.User;
import com.ssafy.ws.model.service.BookService;
import com.ssafy.ws.model.service.UserService;

@Controller
public class BookController {
	
	@Autowired
	private BookService bService;
	
	@Autowired
	private UserService uService;
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index() {
		return "index";
	}	
	
	@PostMapping("/login")
	public String doLogin(User user, HttpSession session, Model model) {
		User loginUser = uService.select(user);
		
		if (loginUser != null && loginUser.getId() != null) {
			session.setAttribute("loginUser", loginUser);
			return "redirect:/";
		}
		else {
			model.addAttribute("msg", "로그인 실패");
			return "index";
		}
	}
	
	@GetMapping("/logout")
	public String doLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/list")
	public String showList(Model model) {
		List<Book> books = bService.search();
		model.addAttribute("books", books);
		return "list";
	}
	
	@GetMapping("/regist")
	public String showRegistForm() {
		return "regist";
	}
	
	@PostMapping("/regist")
	public String doRegist(@ModelAttribute Book book, Model model) throws Exception {	
		if(book.getIsbn() == null ||
				book.getTitle() == null ||
				book.getAuthor() == null ||
				book.getContent() == null ||
				book.getImg() == null				
				) {
				model.addAttribute("errmsg", "파라미터가 잘 전달되었는지 확인하세요.");
				return "/error/commonerr";
		}
		bService.insert(book);
		return "regist_result";
	}
	
	@RequestMapping("/error/commonerr")
	public String showError500() {
		return "error/commonerr";
	}
	
	@RequestMapping("/error/404")
	public String showError404() {
		return "error/404";
	}
	
	//도서 상세
	@GetMapping("/book/{isbn}")
	public String bookDetail(@PathVariable String isbn, Model model) {
		Book book = bService.select(isbn);
		model.addAttribute("article", book);
		
		return "detail";
	}
	
	//도서 수정 get
	@GetMapping("/modify/{isbn}")
	public String bookModify(@PathVariable String isbn, Model model) {
		Book book = bService.select(isbn);
		model.addAttribute("book", book);
		
		return "modify";
	}
	
	//도서 수정 post
	@PostMapping("/modify")
	public String bookModify(Book book) {	
		bService.update(book);
		
		return "redirect:/list";
	}
	
	//도서 삭제
	@GetMapping("delete/{isbn}")
	public String bookDelete(@PathVariable String isbn) {
		bService.delete(isbn);
		
		return "redirect:/list";
	}
}
