package com.swe.filters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter{

	 public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	 public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
	    
	
	protected CustomAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		// TODO Auto-generated constructor stub
	}

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		
			System.out.println("In the auth filter");
		    String username = obtainUsername(request);
	        String password = obtainPassword(request);

	        if (username == null) {
	            username = "";
	        }

	        if (password == null) {
	            password = "";
	        }

	        username = username.trim();

	        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

	        // Allow subclasses to set the "details" property
	        setDetails(request, authRequest);
	        return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	
	

	

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

	protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }
	
	 protected String obtainUsername(HttpServletRequest request) {
	        return request.getParameter(usernameParameter);
	 }
	
}
