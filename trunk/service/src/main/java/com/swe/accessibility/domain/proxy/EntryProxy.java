package com.swe.accessibility.domain.proxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.factory.annotation.Autowired;

import com.swe.accessibility.domain.Comment;
import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.EntryReason;
import com.swe.accessibility.domain.Priority;
import com.swe.accessibility.domain.SubReason;


public class EntryProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3786243773940227607L;
	
	
	
	
	private int id;
	
	private String imageMeta;

	
	private BigDecimal coordX;
	
	private BigDecimal coordY;
	
	
	private String comment;
	
	
	private int upVoteCount;
	
	
	private int downVoteCount;
	
	private String userName;
	
	private boolean fixed;
	
	
	private List<CommentProxy> comments;
	
	private List<SubReason> reasons;
	
	private String priority;
	
	
	public EntryProxy() {
		
	}

	public EntryProxy(Entry entry) {
		
		this.id = entry.getId();
		setImageMeta(entry.getImageMeta());
		this.coordX = entry.getCoordX();
		this.coordY = entry.getCoordY();
		this.comment = entry.getComment();
		this.downVoteCount = entry.getDownVoteCount();
		this.upVoteCount = entry.getUpVoteCount();
        if (entry.getUser() != null)
        	this.setUserName(entry.getUser().getUsername());
        this.fixed = entry.isFixed();
        
        int code = entry.getPriority();

		switch (code) {
		case 1:
			this.priority = Priority.LOW.getLabel();
			break;
		case 2:
			this.priority = Priority.HIGH.getLabel();
		case 3:
			this.priority = Priority.CRITICAL.getLabel();
			break;
		default:
			this.priority = null;
		}
       
	}

	public String getImageMeta() {
		return imageMeta;
	}

	public void setImageMeta(String imageMeta) {
		this.imageMeta = imageMeta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public BigDecimal getCoordX() {
		return coordX;
	}

	public void setCoordX(BigDecimal coordX) {
		this.coordX = coordX;
	}

	public BigDecimal getCoordY() {
		return coordY;
	}

	public void setCoordY(BigDecimal coordY) {
		this.coordY = coordY;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getUpVoteCount() {
		return upVoteCount;
	}

	public void setUpVoteCount(int upVoteCount) {
		this.upVoteCount = upVoteCount;
	}

	public int getDownVoteCount() {
		return downVoteCount;
	}

	public void setDownVoteCount(int downVoteCount) {
		this.downVoteCount = downVoteCount;
	}
	
	


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<CommentProxy> getComments() {
		return comments;
	}

	public void setComments(List<CommentProxy> comments) {
		this.comments = comments;
	}

	public boolean isFixed() {
		return fixed;
	}
	
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	

}
