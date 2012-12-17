package com.swe.accessibilty.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.dataaccess.EntryDao;
import com.swe.accessibility.dataaccess.ReasonDao;
import com.swe.accessibility.dataaccess.UserEntryVoteDao;
import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.SubReason;
import com.swe.accessibility.domain.User;
import com.swe.accessibility.domain.UserEntryVote;
import com.swe.accessibility.domain.UserEntryVoteId;
import com.swe.accessibility.domain.proxy.Config;
import com.swe.accessibility.domain.proxy.EntryProxy;

@Service(value="entryService")
@Transactional
public class EntryServiceImpl implements EntryService {

	@Autowired
	private EntryDao entryDao;
	
	@Autowired
	private ReasonDao reasonDao;
	
	@Autowired
	private Config config;
	
	@Autowired
	private UserEntryVoteDao userEntryVoteDao;
	
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
			EntryProxy proxy = new EntryProxy(entry);
		
			proxy.setImageMeta(config.getImageHost() + "/" + proxy.getImageMeta());
			entries.add(proxy);
		}
		
		return entries;
	}
	
	public List<EntryProxy> loadEntries(BigDecimal coordX, BigDecimal coordY){
		
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		for (Entry entry : getEntry(coordX, coordY)){
			EntryProxy proxy = new EntryProxy(entry);
			
			proxy.setImageMeta(config.getImageHost() + "/" + proxy.getImageMeta());
			entries.add(proxy);
		}
		
		return entries;
	}

	@Override
	@Transactional
	public List<EntryProxy> getEntriesByType(String typeId) {
		
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		for (Entry entry : entryDao.getEntriesByType(typeId)){
			EntryProxy proxy = new EntryProxy(entry);
			proxy.setImageMeta(config.getImageHost() + "/" + proxy.getImageMeta());
			entries.add(proxy);
		}
		
		return entries;
	}

	@Override
	@Transactional
	public List<EntryProxy> getEntriesByCategory(String categoryId) {
		
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		
		SubReason reason = reasonDao.getEntriesBySub(Integer.parseInt(categoryId));
		for (Entry entry : entryDao.getEntriesByCategory(reason)){
			EntryProxy proxy = new EntryProxy(entry);
			proxy.setImageMeta(config.getImageHost() + "/" + proxy.getImageMeta());
			entries.add(proxy);
		}
		
		return entries;
	}

	@Override
	public void updateEntryVote(Entry entry, boolean up,User user) {
		
		
		
		if(up)
			entry.setUpVoteCount(entry.getUpVoteCount()+1);
		else
			entry.setDownVoteCount(entry.getDownVoteCount()+1);
		
		entryDao.update(entry);
		UserEntryVote userVote = new UserEntryVote();
		UserEntryVoteId pk = new UserEntryVoteId();
		pk.setEntry(entry);
		pk.setUser(user);
		userVote.setPk(pk);
		userEntryVoteDao.add(userVote);
		
	}

	@Override
	public EntryProxy getEntryById(int id) {
		
		Entry entry =  entryDao.getById(id);
		
		EntryProxy result = new EntryProxy(entry);
		
		//result.setComments(entry.getComments());
		
		return result;
	
		
	}
	
	@Override
	public Entry getRawEntry(int id){
		
		return entryDao.getById(id);
	}
	
	@Override
	@Transactional
	public boolean checkForVote(Entry entry, User user){
		
		boolean status = false;
		UserEntryVoteId id = new UserEntryVoteId();
		id.setEntry(entry);
		id.setUser(user);
		
		UserEntryVote obj = userEntryVoteDao.get(id);
		
		if (obj != null)
			status = true;
		
		return status;
		
	}

	@Override
	@Transactional
	public List<EntryProxy> loadEntries(String priority) {
		
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		for (Entry entry : entryDao.getEntriesByPriority(priority)){
			EntryProxy proxy = new EntryProxy(entry);
		
			proxy.setImageMeta(config.getImageHost() + "/" + proxy.getImageMeta());
			entries.add(proxy);
		}
		
		return entries;
	}

	@Override
	@Transactional
	public void editEntry(Entry entry) {
	
		entryDao.update(entry);
		
	}

	
	
	
	
	
	

}
