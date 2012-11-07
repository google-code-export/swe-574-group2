package com.swe.accessibilty.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Component;


public class CustomTokenBasedRememberMeServices extends TokenBasedRememberMeServices{
	
	public CustomTokenBasedRememberMeServices() {
		super();
	}
	public CustomTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

}
