package com.swe.accessibility.domain.proxy;

import java.io.Serializable;
import java.util.List;
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

import com.swe.accessibility.domain.SubReason;


public class SubReasonProxy implements Serializable{
	
	

	private static final long serialVersionUID = -1118384270599532798L;

	
	private int id;
	
	
	private String title;
	
	
	private int parentReasonId;
	
	
	private Set<EntryProxy> entries;
	
	
	
	
	public SubReasonProxy(){
		
	}

	public SubReasonProxy(SubReason subReason){
		
		this.id= subReason.getId();
		this.parentReasonId = subReason.getParentReasonId();
		this.title = subReason.getTitle();
		
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

	public Set<EntryProxy> getEntries() {
		return entries;
	}

	public void setEntries(Set<EntryProxy> entries) {
		this.entries = entries;
	}

	
	
}
