package com.swe.accessibility.dataaccess;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.UserType;

@Component
public class UserTypeDaoImpl implements UserTypeDao {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public int insert(UserType type) {
		
		return (Integer) sessionFactory.getCurrentSession().save(type);
	}

	@Override
	public void delete(UserType type) {
		
		sessionFactory.getCurrentSession().delete(type);
	}

	@Override
	public void update(UserType type) {
		
		sessionFactory.getCurrentSession().update(type);
	}

	@Override
	public UserType getById(int id) {
		
		return (UserType) sessionFactory.getCurrentSession().load(UserType.class, id);
	}

}
