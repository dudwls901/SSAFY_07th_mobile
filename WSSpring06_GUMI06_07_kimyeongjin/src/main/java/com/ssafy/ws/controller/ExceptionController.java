package com.ssafy.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	/**
	 * 모든 예외를 rough하게 처리하기 위해 Exception 클래스를 처리하도록 설계한다.
	 * 해커 공격의 빌미를 제공할 수 있음으로 서버에서 발생한 구체적인 오류를 표시하지는 않는다.
	 * 하지만 사용자에게 알려줘야할 내용이 있다면 표시해주어야 한다.
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) {
		logger.error("예외 처리", e.getCause());
		ModelAndView mnv = new ModelAndView();
		// 해커 공격의 빌미를 제공할 수 있음으로 서버에서 발생한 구체적인 오류를 표시하지는 않는다.
		if (e instanceof BindException) {
			mnv.addObject("errmsg", "파라미터가 잘 전달되었는지 확인하세요.");
		}
		// 예외가 발생되면 /error/err을 호출한다.
		mnv.setViewName("/error/commonerr");
		return mnv;
	}

}
