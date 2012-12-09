package com.swe.accessibility.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;


@Embeddable
public class UserEntryVoteId implements Serializable{
	
	private Entry entry;
	
	private User user;

	@ManyToOne
	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
 
        UserEntryVoteId that = (UserEntryVoteId) o;
 
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (entry != null ? !entry.equals(that.entry) : that.entry != null)
            return false;
 
        return true;
    }
 
    public int hashCode() {
        int result;
        result = (user != null ? user.hashCode() : 0);
        result = 31 * result + (entry != null ? entry.hashCode() : 0);
        return result;
    }

}
