package com.swe.accessibility.dataaccess;

import com.swe.accessibility.domain.UserEntryVote;
import com.swe.accessibility.domain.UserEntryVoteId;

public interface UserEntryVoteDao {
	
	public void add(UserEntryVote userVote);
	
	public UserEntryVote get(UserEntryVoteId id);

}
