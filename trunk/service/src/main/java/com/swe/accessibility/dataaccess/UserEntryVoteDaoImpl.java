package com.swe.accessibility.dataaccess;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swe.accessibility.domain.UserEntryVote;
import com.swe.accessibility.domain.UserEntryVoteId;

@Component
public class UserEntryVoteDaoImpl implements UserEntryVoteDao {

	@Autowired
	private SessionFactory factory;
	
	@Override
	public void add(UserEntryVote userVote) {
		
		factory.getCurrentSession().save(userVote);

	}

	@Override
	public UserEntryVote get(UserEntryVoteId id) {
		
		
		return (UserEntryVote) factory.getCurrentSession().get(UserEntryVote.class, id);

	}

}
