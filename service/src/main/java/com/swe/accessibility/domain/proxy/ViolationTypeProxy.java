package com.swe.accessibility.domain.proxy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name="ViolationType")
@Entity
public class ViolationTypeProxy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2082643732924331943L;
	
	@Id
	private int id;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="ImageMeta")
	private String imageMeta;
	
	
	//private Set<SubReason> reasons;
	
	public int getId() {
		return id;
	}

//	public Set<SubReason> getReasons() {
//		return reasons;
//	}
//
//	public void setReasons(Set<SubReason> reasons) {
//		this.reasons = reasons;
//	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageMeta() {
		return imageMeta;
	}

	public void setImageMeta(String imageMeta) {
		this.imageMeta = imageMeta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
