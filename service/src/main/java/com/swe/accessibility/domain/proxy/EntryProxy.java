package com.swe.accessibility.domain.proxy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.swe.accessibility.domain.Entry;


public class EntryProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3786243773940227607L;
	
	
	private int id;
	
	private byte[] imageMeta;
	
	
	private BigDecimal coordX;
	
	private BigDecimal coordY;
	
	
	private String comment;
	
	
	private int upVoteCount;
	
	
	private int downVoteCount;
	
	private String userName;

	public EntryProxy(Entry entry) {
		
		this.id = entry.getId();
		this.imageMeta = entry.getImageMeta();
		this.coordX = entry.getCoordX();
		this.coordY = entry.getCoordY();
		this.comment = entry.getComment();
		this.downVoteCount = entry.getDownVoteCount();
		this.upVoteCount = entry.getUpVoteCount();
        if (entry.getUser() != null)
        	this.setUserName(entry.getUser().getUsername());
	}

	public byte[] getImageMeta() {
		return imageMeta;
	}

	public void setImageMeta(byte[] imageMeta) {
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

}
