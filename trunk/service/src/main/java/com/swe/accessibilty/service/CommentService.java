package com.swe.accessibilty.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.domain.Comment;
import com.swe.accessibility.domain.proxy.CommentProxy;

public interface CommentService {
	
	@Transactional
	public void addComment(Comment comment);
	
	@Transactional
	public void updateComment(Comment comment);
	
	@Transactional
	public void deleteComment(Comment comment);
	
	@Transactional
	public List<CommentProxy> listComment(int entryId);

}
