package com.swe.accessibility.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="UserType")
@Entity
public class UserType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8251778404522635371L;

	@Id
	private int id;
	
	@Column(name="Title")
	private String title;

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
}
