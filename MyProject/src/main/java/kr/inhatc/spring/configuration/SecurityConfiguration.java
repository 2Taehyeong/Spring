package kr.inhatc.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		// TODO Auto-generated method stub
		// 웹페이지에 대한 권한 설정

		// 인증되지 않은 사용자 접근 경로
		security.authorizeRequests().antMatchers("/").permitAll();
		
		// MEMBER 권한이 있는 사용자 접근 경로
		security.authorizeRequests().antMatchers("/user/**").hasAnyRole("MEMBER", "ADMIN");
		
		// ADMIN 권한이 있는 사용자 접근 경로
//		security.authorizeRequests().antMatchers("/board/**").hasRole("ADMIN");
		security.authorizeRequests().antMatchers("/board/**").hasRole("MEMBER");
		
		// RESTfull을 사용하기 위해서는 비활성화
		security.csrf().disable();
		
		// 로그인 관련 페이지와 성공시 이동할 페이지 설정
		security.formLogin().loginPage("/login/login").defaultSuccessUrl("/", true);
		
		// 실패시 이동할 페이지
		security.exceptionHandling().accessDeniedPage("/login/accessDenied");
		
		// 로그아웃 요청시 세션을 강제 종료하고 시작 페이지로 이동
		security.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
		
	}

	/**
	 * <pre>
	 * 1. 개요 : 패스워드에 암호화 처리
	 * 2. 처리내용 : 암호화 처리
	 * </pre>
	 * @Method Name : passwordEncoder
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
