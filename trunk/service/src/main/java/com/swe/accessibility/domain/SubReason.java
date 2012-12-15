package com.swe.accessibility.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;




@Table(name="Reason")
@Entity
public class SubReason implements Serializable{
	
	

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
	
//	@ManyToMany(cascade = {CascadeType.ALL},targetEntity=ViolationType.class,fetch=FetchType.EAGER,mappedBy="reasons")
//	private Set<ViolationType> violationTypes;
//	
//	public Set<ViolationType> getViolationTypes() {
//		return violationTypes;
//	}
//
//	public void setViolationTypes(Set<ViolationType> violationTypes) {
//		this.violationTypes = violationTypes;
//	}
	
	
	private String extra;
	
	
	private int priority;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "er.reason", cascade = 
	    {CascadeType.PERSIST, CascadeType.MERGE})
	    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, 
	    org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private Set<EntryReason> entryReasons;

	

	public void setEntryReasons(Set<EntryReason> entryReasons) {
		this.entryReasons = entryReasons;
	}
	
	

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

	@Column(name="extra")
	public String getExtra() {
		return extra;
	}
	
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	@Column(name="priority")
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
