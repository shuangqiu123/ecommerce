package com.sq.user;

import com.sq.pojo.Member;
import com.sq.security.dto.MemberDetails;
import com.sq.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootApplication
@MapperScan("com.sq.user.mapper")
@ComponentScan({"com.sq.util", "com.sq.security","com.sq.user"})
public class SqUserApplication {

	public static void main(String[] args) {

		SpringApplication.run(SqUserApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Bean
	public UserDetailsService userDetailsService() {
		return uid -> {
			Member admin = userService.selectUserById(Long.parseLong(uid));
			if (admin != null) {
				return new MemberDetails(admin);
			}
			throw new UsernameNotFoundException("uid is not found");
		};
	}
}
