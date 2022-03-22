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

	/**
	 * 메서드 선언 전에 동작해야 하므로 @Before advicce로 구성한다.
	 * point cut의 구성은 아래와 같다.
	 * 리턴 타입: * 이므로 모든 리턴 타입에 대해 적용된다.
	 * 클래스명: com.ssafy.ws.model 패키지로 시작하고 .. 이므로 하위의 모든 경로, 클래스에 적용된다.
	 * 메서드명: * 이므로 모든 메서드 이름에 적용된다.
	 * 파라미터:.. 이므로 파라미터의 개수, 타입에 상관없이 적용된다.
	 * @param jp JoinPoint를 통해 joinpoint의 다양한 정보에 접근할 수 있다.
	 */
	@Before(value = "execution(* com.ssafy.ws.model..*.*(..))")
	public void logging(JoinPoint jp) {
		// getSignature: 메서드의 선언부 정보를 반환한다.
		// args: 메서드에 전달된 파라미터 정보를 배열로 반환한다.
		logger.debug("메서드선언부:{} 전달 파라미터:{}",jp.getSignature(), Arrays.toString(jp.getArgs()));
	}
}
