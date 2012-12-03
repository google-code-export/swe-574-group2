package com.swe.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.swe.accessibility.domain.User;
import com.swe.accessibilty.service.UserService;

@Component
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    
    
    AuthenticationManager authManager;
    @Autowired
    UserService userService;
    private User user = null;
    
    public AuthenticationTokenProcessingFilter() {
		// TODO Auto-generated constructor stub
	}

    public AuthenticationTokenProcessingFilter(AuthenticationManager authManager ) {
        this.authManager = authManager;
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

    	//TODO write a token util having expire date
    	HttpServletRequest req = (HttpServletRequest) request;
		String token = req.getHeader("AuthToken");
		if (token != null){
			
			if (validateToken(token)){
				System.out.println("Valid Token!!!");
				Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
				UsernamePasswordAuthenticationToken authentication = 
	                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
	            	
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
	            	// set the authentication into the SecurityContext
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            req.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
			}
				
				  
		}
		
//        // continue thru the filter chain
    	System.out.println("In the auth filter");
    	
        chain.doFilter(request, response);
    }
        
        private boolean validateToken(String token){
    		
    		String[] credArr = token.split(":");
    		String name = credArr[0];
    		String password = credArr[1];
    		
    		user = userService.getUser(name, password);
    		
    		boolean status = user == null ? false : true;
    		return status;
    	}
}
