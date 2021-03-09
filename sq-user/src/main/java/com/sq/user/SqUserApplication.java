package com.sq.user;

import com.sq.pojo.Member;
import com.sq.security.dto.MemberDetails;
import com.sq.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootApplication
@MapperScan({"com.sq.user.mapper", "com.sq.security.mapper"})
@ComponentScan({"com.sq.util", "com.sq.security","com.sq.user"})
@EnableEurekaClient
public class SqUserApplication {

	public static void main(String[] args) {

		SpringApplication.run(SqUserApplication.class, args);
	}
}
