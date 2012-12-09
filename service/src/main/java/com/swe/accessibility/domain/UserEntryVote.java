package com.swe.accessibility.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UserEntryVote")
public class UserEntryVote implements Serializable{
	
	private UserEntryVoteId pk;
	
	@EmbeddedId
	public UserEntryVoteId getPk() {
		return pk;
	}
	
	public void setPk(UserEntryVoteId pk) {
		this.pk = pk;
	}

}
