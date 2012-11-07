package com.swe.accessibilty.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.User;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User user = userService.getUserByName(username);
		
		
		if (user == null)
			throw new UsernameNotFoundException("User name " + username + " not found");
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		if (user.getUserType() != null)
			authorities.add(new SimpleGrantedAuthority(user.getUserType().getTitle()));
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
		
		return userDetails;
	}

}
