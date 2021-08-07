package com.sq.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan({"com.sq.user.mapper", "com.sq.security.mapper"})
@ComponentScan({"com.sq.util", "com.sq.security","com.sq.user"})
public class SqUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqUserApplication.class, args);
	}
}
