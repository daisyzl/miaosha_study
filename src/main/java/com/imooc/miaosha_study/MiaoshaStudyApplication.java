package com.imooc.miaosha_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MiaoshaStudyApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MiaoshaStudyApplication.class, args);
	}
	//war包需要
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MiaoshaStudyApplication.class);
	}
}
