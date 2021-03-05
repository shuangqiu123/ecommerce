package com.sq.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sq.user.mapper")
public class SqUserApplication {

	public static void main(String[] args) {

		SpringApplication.run(SqUserApplication.class, args);
	}

}
