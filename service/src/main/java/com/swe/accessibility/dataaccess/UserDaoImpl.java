package com.swe.accessibility.dataaccess;

import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.User;

@Component
public class UserDaoImpl implements UserDao {

	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int insert(User user) {
		
		return (Integer) sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public void delete(User user) {
		
		sessionFactory.getCurrentSession().delete(user);
		
	}

	@Override
	public void update(User user) {
		
		sessionFactory.getCurrentSession().update(user);
		
	}

	@Override
	public User getById(int id) {
		
		return (User) sessionFactory.getCurrentSession().load(User.class, id);
	}

	@Override
	public User get(String name, String password) {
		
		User user = new User();
		Query query = sessionFactory.getCurrentSession().createQuery("from User where username = :name and password = :pass");
		query.setParameter("name", name);
		query.setParameter("pass", password);
		
		if (query.list().size() == 1)
			user = (User) query.list().get(0);
		
		return user;
	}

	@Override
	public User get(String name) {
		
		User user = new User();
		Query query = sessionFactory.getCurrentSession().createQuery("from User  where username = :name");
		query.setParameter("name", name);
		
		
		if (query.list().size() == 1)
			user = (User) query.list().get(0);
		
		return user;
		
		
	}
	
	
	

}
