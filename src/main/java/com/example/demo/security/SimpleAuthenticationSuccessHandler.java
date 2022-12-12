package com.example.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private UserService userService;
	 @Override
     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
             Authentication authentication) throws IOException, ServletException {
     	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String role = userDetails.getAuthorities().toString();
         if (role.equals("[1]")) {
        	 redirectStrategy.sendRedirect(request, response, "/");
         } else if (role.equals("[2]")) {
        	 redirectStrategy.sendRedirect(request, response, "/admin");
         } else if (role.equals("[3]")) {
        	 redirectStrategy.sendRedirect(request, response, "/storehouse");
         }
			/*
			 * System.out.println(role); System.out.println(role.equals("1"));
			 * System.out.println(role.equals("2")); System.out.println(role.equals("3"));
			 */     
	 }
	 	
}
