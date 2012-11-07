package com.swe.accessibilty.authentication;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.User;
import com.swe.accessibilty.service.UserService;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Resource(name="userService")
	public UserService userService;
	
	@Override
	public void commence(HttpServletRequest req, HttpServletResponse resp,
			AuthenticationException e) throws IOException, ServletException {
		System.out.println("In the entry point");
	
	
		resp.setStatus(HttpStatus.UNAUTHORIZED.value());
		
	}
	
	
}
