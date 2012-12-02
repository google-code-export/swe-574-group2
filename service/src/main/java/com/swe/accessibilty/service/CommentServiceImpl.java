package com.swe.accessibilty.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.dataaccess.CommentDao;
import com.swe.accessibility.domain.Comment;
import com.swe.accessibility.domain.proxy.CommentProxy;

@Service(value="commentService")
@Transactional
public class CommentServiceImpl implements CommentService {

	
	@Autowired
	private CommentDao commentDao;
	
	@Override
	@Transactional
	public void addComment(Comment comment) {
		
		commentDao.addComment(comment);

	}

	@Override
	@Transactional
	public void updateComment(Comment comment) {
		
		commentDao.updateComment(comment);

	}

	@Override
	@Transactional
	public void deleteComment(Comment comment) {
	
		commentDao.deleteComment(comment);

	}

	@Override
	@Transactional
	public List<CommentProxy> listComment(int entryId) {
		
		List<Comment> comments =  commentDao.listComments(entryId);
		List<CommentProxy> values = new ArrayList<CommentProxy>();
		
		for (Comment comment : comments){
			CommentProxy value = new CommentProxy(comment);
			values.add(value);
		}
		
		return values;

	}

}
