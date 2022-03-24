package com.ssafy.ws;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.ssafy.ws.model.repo")
@EnableSwagger2
public class WsSpring06Gumi0607KimyeongjinApplication implements WebMvcConfigurer {

	
	public static void main(String[] args) {
		SpringApplication.run(WsSpring06Gumi0607KimyeongjinApplication.class, args);
	}

}
