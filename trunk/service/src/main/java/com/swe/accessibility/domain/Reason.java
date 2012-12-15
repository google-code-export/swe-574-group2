package com.swe.accessibility.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;




@Table(name="Reason")
@Entity
public class Reason implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1118384270599532798L;

	@Id
	private int id;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="ParentReasonId")
	private int parentReasonId;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public int getParentReasonId() {
		return parentReasonId;
	}

	public void setParentReasonId(int parentReasonId) {
		this.parentReasonId = parentReasonId;
	}

	
}
