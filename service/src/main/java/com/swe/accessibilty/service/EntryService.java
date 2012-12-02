package com.swe.accessibilty.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.proxy.EntryProxy;

public interface EntryService {

	@Transactional
	public abstract int addEntry(Entry entry);

	@Transactional
	public abstract List<Entry> getEntry(BigDecimal coordX, BigDecimal coordY);

	@Transactional
	public abstract List<Entry> getEntries();
	
	public abstract List<EntryProxy> loadEntries();

	public abstract List<EntryProxy> loadEntries(BigDecimal coordX, BigDecimal coordY);
	
	@Transactional
	public abstract List<EntryProxy> getEntriesByType(String typeId);

	public abstract List<EntryProxy> getEntriesByCategory(String categoryId);

	public abstract void updateEntryVote(int id, boolean up);

	public abstract EntryProxy getEntryById(int id);

	Entry getRawEntry(int id);
}