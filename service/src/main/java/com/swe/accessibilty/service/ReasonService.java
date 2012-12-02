package com.swe.accessibilty.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.domain.Reason;
import com.swe.accessibility.domain.SubReason;
import com.swe.accessibility.domain.proxy.EntryProxy;
import com.swe.accessibility.domain.proxy.SubReasonProxy;

public interface ReasonService {

	@Transactional
	public abstract List<Reason> getReasons();

	@Transactional
	public abstract List<SubReasonProxy> getSubReasons(int id);

	@Transactional
	public abstract SubReason getSubReason(int id);
	
	@Transactional
	public abstract List<EntryProxy> getEntries(int id);

}