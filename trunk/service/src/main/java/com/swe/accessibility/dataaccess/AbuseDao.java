package com.swe.accessibility.dataaccess;

import com.swe.accessibility.domain.Abuse;

public interface AbuseDao {
	
	int insert(Abuse abuse);
	
	void delete(Abuse abuse);
	
	void update(Abuse abuse);
	
	Abuse getById(int id);

}
