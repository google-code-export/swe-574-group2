package com.swe.accessibility.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;



@Embeddable
public class EntryReasonId implements Serializable {
 
    private Entry entry;
    
    private SubReason reason;

    @ManyToOne
	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	@ManyToOne
	public SubReason getReason() {
		return reason;
	}

	public void setReason(SubReason reason) {
		this.reason = reason;
	}
	
	  public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	 
	        EntryReasonId that = (EntryReasonId) o;
	 
	        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
	        if (entry != null ? !entry.equals(that.entry) : that.entry != null)
	            return false;
	 
	        return true;
	    }
	 
	    public int hashCode() {
	        int result;
	        result = (reason != null ? reason.hashCode() : 0);
	        result = 31 * result + (entry != null ? entry.hashCode() : 0);
	        return result;
	    }
 
   
}