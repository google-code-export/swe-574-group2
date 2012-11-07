package com.swe.accessibility.dataaccess;

import com.swe.accessibility.domain.User;

public interface UserDao {
	
	int insert(User user);
	
	void delete(User user);
	
	void update(User user);
	
	User getById(int id);

	User get(String name, String password);

	User get(String username);

}
