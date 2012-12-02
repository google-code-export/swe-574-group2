package com.swe.accessibility.dataaccess;

import java.util.List;

import com.swe.accessibility.domain.Comment;

public interface CommentDao {
	
	public void addComment(Comment comment);
	
	public void updateComment(Comment comment);
	
	public void deleteComment(Comment comment);
	
	public List<Comment> listComments(int entryId);
	
	public List<Comment> listCommentsByUser(int userId);
	
	public Comment getComment(int id);

}
