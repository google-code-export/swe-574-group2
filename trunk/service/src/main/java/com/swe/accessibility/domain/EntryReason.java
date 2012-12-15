package com.swe.accessibility.domain;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EntryReason")
@AssociationOverrides({
@AssociationOverride(name = "er.entry", joinColumns = @JoinColumn(name = "entryId")),
@AssociationOverride(name = "er.reason", joinColumns = @JoinColumn(name = "reasonId"))
        })
public class EntryReason implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3503942712551273826L;
	
	private EntryReasonId er = new EntryReasonId();
	
	
	private String extra;

	@EmbeddedId
	public EntryReasonId getEr() {
		return er;
	}

	public void setEr(EntryReasonId er) {
		this.er = er;
	}

	
	@Transient
	public Entry getEntry(){
		
		return getEr().getEntry();
	}
	
	public void setEntry(Entry entry){
		
		getEr().setEntry(entry);
		
	}
	
	@Transient
	public SubReason getReason(){
		
		return getEr().getReason();
	}
	
	public void setReason(SubReason reason){
		
		getEr().setReason(reason);
	}

	@Column(name="extra")
	public String getExtra() {
		return extra;
	}
	
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
 
        EntryReason that = (EntryReason) o;
 
        if (getEr() != null ? !getEr().equals(that.getEr()) : that.getEr() != null) return false;
 
        return true;
    }
 
    public int hashCode() {
        return (getEr() != null ? getEr().hashCode() : 0);
    }
	
	
	

}
