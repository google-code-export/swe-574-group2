package com.swe.accessibilty.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.dataaccess.EntryDao;
import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.proxy.EntryProxy;

@Service(value="entryService")
@Transactional
public class EntryServiceImpl implements EntryService {

	@Autowired
	private EntryDao entryDao;
	
	/* (non-Javadoc)
	 * @see com.swe.accessibilty.service.EntryServiceInter#addEntry(com.swe.accessibility.domain.Entry)
	 */
	@Override
	@Transactional
	public int addEntry(Entry entry) {
		
		return entryDao.insert(entry);
		
	}

	/* (non-Javadoc)
	 * @see com.swe.accessibilty.service.EntryServiceInter#getEntry(java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	@Transactional
	public List<Entry> getEntry(BigDecimal coordX, BigDecimal coordY) {
		
		return entryDao.get(coordX, coordY);
	}
	
	
	/* (non-Javadoc)
	 * @see com.swe.accessibilty.service.EntryServiceInter#getEntries()
	 */
	@Override
	@Transactional
	public List<Entry> getEntries(){
		
		return entryDao.getAll();
	}
	
	public List<EntryProxy> loadEntries(){
		
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		for (Entry entry : getEntries()){
			entries.add(new EntryProxy(entry));
		}
		
		return entries;
	}
	
	public List<EntryProxy> loadEntries(BigDecimal coordX, BigDecimal coordY){
		
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		for (Entry entry : getEntry(coordX, coordY)){
			entries.add(new EntryProxy(entry));
		}
		
		return entries;
	}

	@Override
	@Transactional
	public List<EntryProxy> getEntriesByType(String typeId) {
		
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		for (Entry entry : entryDao.getEntriesByType(typeId)){
			entries.add(new EntryProxy(entry));
		}
		
		return entries;
	}

	@Override
	public List<EntryProxy> getEntriesByCategory(String categoryId) {
		
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		for (Entry entry : entryDao.getEntriesByCategory(categoryId)){
			entries.add(new EntryProxy(entry));
		}
		
		return entries;
	}

	@Override
	public void updateEntryVote(int id, boolean up) {
		
		Entry entry = entryDao.getById(id);
		
		if(up)
			entry.setUpVoteCount(entry.getUpVoteCount()+1);
		else
			entry.setDownVoteCount(entry.getDownVoteCount()+1);
		
		entryDao.update(entry);
		
	}
		
	
	
	
	

}
