package com.sq.security.config;

import com.sq.pojo.Member;
import com.sq.security.component.JwtAuthenticationTokenFilter;
import com.sq.security.component.RestAuthenticationEntryPoint;
import com.sq.security.component.RestfulAccessDeniedHandler;
import com.sq.security.dto.MemberDetails;
import com.sq.security.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig manage the access to URLs
 * credit: http://www.macrozheng.com/#/architect/mall_arch_04
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailService userService;


    @Autowired
    private AccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .sessionManagement()// no need for session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // allow unauthorized access to static resources
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                )
                .permitAll()
                .antMatchers("/user/login/**", "/user/register/**","/item/**")// allow anonymous access for given urls
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS) //跨域请求会先进行一次options请求
                .permitAll()
                .anyRequest()// authorized any other requests
                .authenticated();


        // disable http cache
        httpSecurity.headers().cacheControl();

        // add JWT Filter
        httpSecurity.addFilterBefore(
                jwtAuthenticationTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);

        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return uid -> {
            Member member = userService.getMemberByUid(uid);
            if (member != null) {
                return new MemberDetails(member);
            }
            throw new UsernameNotFoundException("uid is not found");
        };
    }
}
