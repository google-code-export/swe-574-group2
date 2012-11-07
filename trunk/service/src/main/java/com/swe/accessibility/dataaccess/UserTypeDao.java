package com.swe.accessibility.dataaccess;

import com.swe.accessibility.domain.UserType;

public interface UserTypeDao {
	
	int insert(UserType type);
	
	void delete(UserType type);
	
	void update(UserType tpye);
	
	UserType getById(int id);

}
