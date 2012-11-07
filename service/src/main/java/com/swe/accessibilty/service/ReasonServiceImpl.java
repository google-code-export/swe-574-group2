package com.swe.accessibilty.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.dataaccess.ReasonDao;
import com.swe.accessibility.domain.Entry;
import com.swe.accessibility.domain.Reason;
import com.swe.accessibility.domain.SubReason;
import com.swe.accessibility.domain.proxy.EntryProxy;

@Service("reasonService")
@Transactional
public class ReasonServiceImpl implements ReasonService {
	
	@Autowired
	private ReasonDao reasonDao;
	
	/* (non-Javadoc)
	 * @see com.swe.accessibilty.service.ReasonServiceI#getReasons()
	 */
	@Override
	@Transactional
	public List<Reason> getReasons(){
		
		return reasonDao.getMainAll();
	}
	
	/* (non-Javadoc)
	 * @see com.swe.accessibilty.service.ReasonServiceI#getSubReasons(int)
	 */
	@Override
	@Transactional
	public List<SubReason> getSubReasons(int id){
		
		return reasonDao.getSubAll(id);
	}
	
	/* (non-Javadoc)
	 * @see com.swe.accessibilty.service.ReasonServiceI#getSubReason(int)
	 */
	@Override
	@Transactional
	public SubReason getSubReason(int id){
		
		return reasonDao.getById(id);
	}

	@Override
	@Transactional
	public List<EntryProxy> getEntries(int id) {
		
		SubReason reason = reasonDao.getEntriesBySub(id);
		List<EntryProxy> entries = new ArrayList<EntryProxy>();
		
		for (Entry entry : reason.getEntries()){
			entries.add(new EntryProxy(entry));
		}
		
		return entries;
	}

}
