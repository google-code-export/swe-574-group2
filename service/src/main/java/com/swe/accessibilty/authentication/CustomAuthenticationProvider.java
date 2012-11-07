package com.swe.accessibilty.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.User;
import com.swe.accessibilty.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Resource(name="userService") 
	private UserService userService;
	
	
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		
		List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
		String hashed = DigestUtils.sha1Hex(auth.getCredentials().toString());
		User user = userService.getUserByName(auth.getName());
		System.out.println(auth.getName());
		System.out.println(auth.getCredentials().toString());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		if (user.getUsername() == null){
			throw new AuthenticationCredentialsNotFoundException("user not found");
		}
		
		if(!user.getPassword().equals(hashed)){
			throw new BadCredentialsException("password is wrong");
		}
		String roleName = user.getUserType().getTitle();
		if (roleName.equals("admin"))
			AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		else
			AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
		
		
	}

	@Override
	public boolean supports(Class<?> auth) {
		
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

}
