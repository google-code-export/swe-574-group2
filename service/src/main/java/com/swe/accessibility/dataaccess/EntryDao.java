package com.swe.accessibility.dataaccess;

import java.math.BigDecimal;
import java.util.List;

import com.swe.accessibility.domain.Entry;

import com.swe.accessibility.domain.SubReason;

public interface EntryDao {
	
	
	int insert(Entry entry);
	
	void delete(Entry entry);
	
	void update(Entry entry);
	
	Entry getById(int id);

	List<Entry> get(BigDecimal coordX, BigDecimal coordY);

	List<Entry> getAll();

	List<Entry> getEntriesByType(String id);

	List<Entry> getEntriesByCategory(SubReason reason);

	List<Entry> getEntriesByPriority(String priority);

	Entry getByIdFetchExtra(int id);

	List<Entry> getEntriesByUser(String username);
	
	

}
