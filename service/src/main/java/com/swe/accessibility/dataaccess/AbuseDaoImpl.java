package com.swe.accessibility.dataaccess;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.Abuse;

@Component
public class AbuseDaoImpl implements AbuseDao {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public int insert(Abuse abuse) {
		
		return (Integer) sessionFactory.getCurrentSession().save(abuse);
	}

	@Override
	public void delete(Abuse abuse) {
		
		sessionFactory.getCurrentSession().delete(abuse);
	}

	@Override
	public void update(Abuse abuse) {
		
		sessionFactory.getCurrentSession().update(abuse);
	}

	@Override
	public Abuse getById(int id) {
		
		return (Abuse) sessionFactory.getCurrentSession().load(Abuse.class, id);
	}

}
