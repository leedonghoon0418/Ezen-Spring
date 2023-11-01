package com.myweb.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.myweb.www.security.CustomeAuthMemberService;
import com.myweb.www.security.LoginFileureHandler;
import com.myweb.www.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	// Security package를 생성하여 사용자 핸들러 생성
	
	// 비밀번호 암호화 객체 빈 생성
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// SeccessHandler 빈 객체 생성 => 사용자가 생성
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
	}
	
	// FailureHandler 빈 객체 생성 => 사용자가 생성
	@Bean
	public AuthenticationFailureHandler authFileureHandler() {
		return new LoginFileureHandler();
	}
	
	// UserDetail 빈 객체 생성 => 사용자가 생성
	@Bean
	public UserDetailsService customUserService() {
		return new CustomeAuthMemberService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 로그인 사용자에 대한 값을 config
		auth.userDetailsService(customUserService()) // 
		.passwordEncoder(bcPasswordEncoder()); //패스워드 암호화
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		//승인요청
		http.authorizeRequests()
		.antMatchers("/member/list")
		.hasRole("ADMIN")
		.antMatchers("/","/board/list","/board/detail","/resources/**","/upload/**","/comment/**","/member/register","/member/login").permitAll()// 비회원에게 허용된 페이지
		.anyRequest().authenticated(); // => 인증된 사용자만 처리
		
		// 커스텀 로그인 페이지를 구성
		// Controller에 주소요청 맵핑도 같이 꼭 적어줘야 함.
		http.formLogin()
		.usernameParameter("email") 
		.passwordParameter("pwd")
		.loginPage("/member/login")
		.successHandler(authSuccessHandler()) 
		.failureHandler(authFileureHandler());
		
		// 로그아웃 페이지
		// 반드시 method = post 로 할것. 
		http.logout()
		.logoutUrl("/member/logout") // 로그아웃 url
		.invalidateHttpSession(true) // 세션 끊기
		.deleteCookies("JSESSIONID") // 쿠키삭제
		.logoutSuccessUrl("/"); // 로그아웃 후 경로 
		
	}
	
	
}
