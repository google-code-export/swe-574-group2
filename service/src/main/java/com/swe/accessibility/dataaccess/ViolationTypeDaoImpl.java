package com.swe.accessibility.dataaccess;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.ViolationType;

@Component
public class ViolationTypeDaoImpl implements ViolationTypeDao {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public int insert(ViolationType abuse) {
		
		return (Integer) sessionFactory.getCurrentSession().save(abuse);
	}

	@Override
	public void delete(ViolationType abuse) {
		
		sessionFactory.getCurrentSession().delete(abuse);
	}

	@Override
	public void update(ViolationType abuse) {
		
		sessionFactory.getCurrentSession().update(abuse);
	}

	@Override
	public ViolationType getById(int id) {
		
		return (ViolationType) sessionFactory.getCurrentSession().load(ViolationType.class, id);
		
	}
	
	@Override
	public List<ViolationType> getAll(){
		
		
		Query query = sessionFactory.getCurrentSession().createQuery("select v.id, v.title, v.title from ViolationType v");
		
		List<ViolationType> types = query.list();
		return types;
		
	}
	
	

}
