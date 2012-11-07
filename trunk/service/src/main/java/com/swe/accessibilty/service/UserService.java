package com.swe.accessibilty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.dataaccess.UserDao;
import com.swe.accessibility.domain.User;

@Service("userService")
@Transactional
public class UserService {
	
	
	@Autowired
	private UserDao userDao;
	
	
	@Transactional
	public User getUser(String name, String password){
		
		return userDao.get(name, password);
	}


	public User getUserByName(String username) {
		
		return userDao.get(username);
			

	}

}
