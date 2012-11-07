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
import javax.persistence.Table;




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
	
	@ManyToMany(cascade = {CascadeType.ALL},targetEntity=ViolationType.class,fetch=FetchType.LAZY,mappedBy="reasons")
	private Set<ViolationType> violationTypes;
	
	public Set<ViolationType> getViolationTypes() {
		return violationTypes;
	}

	public void setViolationTypes(Set<ViolationType> violationTypes) {
		this.violationTypes = violationTypes;
	}
	

	@ManyToMany(cascade = {CascadeType.ALL},targetEntity=Entry.class,fetch=FetchType.LAZY)
    @JoinTable(name="EntryReason",
                joinColumns={@JoinColumn(name="EntryId")},
                inverseJoinColumns={@JoinColumn(name="ReasonId")})
	private Set<Entry> entries;

	public Set<Entry> getEntries() {
		return entries;
	}

	public void setEntries(Set<Entry> entries) {
		this.entries = entries;
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

	
	
}
