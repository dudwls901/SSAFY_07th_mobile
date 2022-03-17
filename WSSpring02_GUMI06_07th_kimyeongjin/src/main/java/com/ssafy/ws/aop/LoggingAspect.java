package com.ssafy.ws.aop;

import java.awt.Point;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.ssafy.ws.dto.Book;


@Component
@Aspect
public class LoggingAspect {
	
	
	@Before("execution(public * *(..))")
	public void before(JoinPoint point) {
		Signature signature = point.getSignature();
		String name = signature.getName();
		Object[] args = point.getArgs();
		System.out.println(name +  "" + Arrays.toString(args));
	}
//
//	@After("execution(public * *(..))")
//	public void after() {
//		System.out.println("Hello After");
//	}
//
//	@AfterReturning(value="execution(public * *(..))", returning="book")
//	public void afterReturning(Book book) {
//		System.out.println("abckambokacmkbomacko");
//		System.out.println("Hello AfterReturning" + book);
//	}
//
//	@Around("execution(public * *(..))")
//	public Book around(ProceedingJoinPoint point) throws Throwable {
//		System.out.println("Around before");
//		Book book = (Book) point.proceed();
//		System.out.println("Around after");
//
//		return book;
//	}
//
//	@AfterThrowing(value="execution(public * *(..))", throwing="ex")
//	public void afterThrowing(Throwable ex) {
//		System.out.println("Hello Throwing!");
//	}
	
//	public void start(JoinPoint point) {
//		Signature signature = point.getSignature();
//		String type = signature.getDeclaringTypeName();
//		String name = signature.getName();
//		Object[] args = point.getArgs();
//		System.out.println(Arrays.deepToString(args));
//		
//		System.out.println(">> log start: " + type + ", " + name+ "()");
//	}
//	
//	public void end() {
//		System.out.println(">> log end");
//	}
//	
//	public Object around(ProceedingJoinPoint pjp) throws Throwable{
//		System.out.println(">> around before");
//		long start = System.nanoTime();
//		
//		Object[] args = pjp.getArgs();
//		for(Object o : args) {
//			System.out.println(">> around:" + o);
//		}
//		
//		//args를 바꿔치기
//		if(args != null && args.length >0) {
//			args[0] = "kim";
//			args[1] = 37;
//		}
//		
//		//위에는 target 실행 이전
//		//핵심 기능 실행하는 부분
//		Object string = pjp.proceed(args); //실제 호출할 메소드
//		
//		//여기서부터는 target 실행 이후
//		long end = System.nanoTime();
//		System.out.println(">> around after: " + (end-start) + "ns");
//		
//		return "Return: Aspect Good bye";
//		
//	}
//	
//	public void returning() {
//		System.out.println(">> returning");
//	}
//	
//	public void throwing() {
//		System.out.println(">> throwing");
//	}
}
