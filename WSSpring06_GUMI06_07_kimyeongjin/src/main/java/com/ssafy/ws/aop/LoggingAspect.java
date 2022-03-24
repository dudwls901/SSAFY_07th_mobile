package com.ssafy.ws.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//LoggingAspect를 빈으로 선언한다.
@Component
//이 클래스가 aspect임을 선언한다.
@Aspect
public class LoggingAspect {
	private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


	@Before(value = "execution(* com.ssafy.ws.model..*.*(..))")
	public void logging(JoinPoint jp) {
		// getSignature: 메서드의 선언부 정보를 반환한다.
		// args: 메서드에 전달된 파라미터 정보를 배열로 반환한다.
		logger.debug("메서드선언부:{} 전달 파라미터:{}",jp.getSignature(), Arrays.toString(jp.getArgs()));
	}
}
