package com.swe.accessibility.domain.proxy;

import java.io.Serializable;
import java.sql.Date;

import com.swe.accessibility.domain.Comment;

public class CommentProxy implements Serializable{
	
	
	
	private int id;
	
	private String text;
	
	private Date insertDate;
	
	private String username;
	
	public CommentProxy(Comment comment){
		
		this.id = comment.getId();
		this.text = comment.getText();
		this.insertDate = comment.getInsertdate();
		this.username = comment.getUser().getUsername();
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
