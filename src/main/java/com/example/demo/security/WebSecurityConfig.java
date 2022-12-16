package com.example.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.user.User;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private SimpleAuthenticationSuccessHandler successHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/js/**", "/css/**", "/images/**", "/", "/products/**", "/register/**",
						"/register-save/**", "/login", "/product/{id}")
				.permitAll()
				// Chỉ cho người có role khách hàng vào
				.antMatchers("/cart").hasAnyAuthority("1")
				// Chỉ cho người có role quản lý vào
				.antMatchers("/admin/**").hasAnyAuthority("2")
				// Chỉ cho người có role quản lý kho vào
				.antMatchers("/storehouse/**", "/add-product/**", "/edit-product/**", "/save-product",
						"/add-category/**", "/edit-category/**", "/save-category")
				.hasAnyAuthority("3")
				// Chỉ cho người có role kế toán vào
				.antMatchers("/accountant/**").hasAnyAuthority("4").anyRequest().authenticated().and()
				.formLogin(
						form -> form.loginPage("/login?permit").usernameParameter("email").passwordParameter("password")
								.loginProcessingUrl("/login").successHandler(successHandler).failureUrl("/login?fail"))
				.logout().invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll().and()
				// Ghi nhớ đăng nhập 2 ngày
				.rememberMe().rememberMeParameter("remember-me").key("uniqueAndSecret")
				.tokenValiditySeconds(2 * 24 * 60 * 60);
		return http.build();
	}

	@Bean
	public static PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

}