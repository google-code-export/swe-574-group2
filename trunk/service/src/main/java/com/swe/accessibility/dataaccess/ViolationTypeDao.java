package com.swe.accessibility.dataaccess;

import java.util.List;

import com.swe.accessibility.domain.ViolationType;

public interface ViolationTypeDao {
	
	int insert(ViolationType type);
	
	void delete(ViolationType type);
	
	void update(ViolationType type);
	
	ViolationType getById(int id);

	List<ViolationType> getAll();



}
