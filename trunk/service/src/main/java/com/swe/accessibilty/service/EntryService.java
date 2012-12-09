package com.swe.accessibilty.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.User;
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

	@Transactional
	public abstract void updateEntryVote(Entry entry, boolean up, User user);

	@Transactional
	public abstract EntryProxy getEntryById(int id);

	Entry getRawEntry(int id);

	@Transactional
	boolean checkForVote(Entry entry, User user);
}