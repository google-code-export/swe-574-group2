package com.swe.accessibility.dataaccess;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.Comment;

@Component
public class CommentDaoImpl implements CommentDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void addComment(Comment comment) {
		
		sessionFactory.getCurrentSession().save(comment);

	}

	@Override
	public void updateComment(Comment comment) {
		
		sessionFactory.getCurrentSession().update(comment);

	}

	@Override
	public void deleteComment(Comment comment) {
		
		sessionFactory.getCurrentSession().delete(comment);

	}

	@Override
	public List<Comment> listComments(int entryId) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Comment c where c.entry.id =:e");
		query.setParameter("e", entryId);
		return query.list();
	}

	@Override
	public List<Comment> listCommentsByUser(int userId) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Comment c where c.user.id =:u");
		query.setParameter("u", userId);
		return query.list();
	}

	@Override
	public Comment getComment(int id) {
		
		return (Comment) sessionFactory.getCurrentSession().get(Comment.class,id);
	}

}
