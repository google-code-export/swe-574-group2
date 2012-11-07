package com.swe.accessibility.dataaccess;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.Reason;
import com.swe.accessibility.domain.SubReason;

@Component
public class ReasonDaoImpl implements ReasonDao {

	@Autowired
	private SessionFactory sessionFactory;
	 
	@Override
	public int insert(Reason reason) {
		
		return  (Integer) sessionFactory.getCurrentSession().save(reason);
		
	}

	@Override
	public void update(Reason reason) {
		
		sessionFactory.getCurrentSession().update(reason);
	}

	@Override
	public void delete(Reason reason) {
		
		sessionFactory.getCurrentSession().delete(reason);

	}

	@Override
	public SubReason getById(int id) {
		
		return (SubReason) sessionFactory.getCurrentSession().load(SubReason.class, id);
	}

	@Override
	public List<Reason> getMainAll() {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Reason where id = parentReasonId");
	    return query.list();
	}

	@Override
	public List<SubReason> getSubAll(int id) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from SubReason r where r.parentReasonId=:parentId and r.id <> :id");
		
		query.setParameter("parentId", id);
		query.setParameter("id", id);
		
		
		return query.list();
	}
	
	
	@Override
	public SubReason getEntriesBySub(int id) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from SubReason r where r.id=:id");
		query.setParameter("id", id);
		
		
		if (query.list().size() == 1)
			return  (SubReason) query.list().get(0);
		
		return null;
	}
	

}
