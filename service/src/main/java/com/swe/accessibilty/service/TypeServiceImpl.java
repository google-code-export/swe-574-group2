package com.swe.accessibilty.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swe.accessibility.dataaccess.ViolationTypeDao;
import com.swe.accessibility.domain.ViolationType;
import com.swe.accessibility.domain.proxy.EntryProxy;

@Service(value="typeService")
@Transactional
public class TypeServiceImpl implements TypeService {

	@Autowired
	private ViolationTypeDao violationTypeDao;
	
	@Override
	@Transactional
	public List<ViolationType> getAll() {
		
		return violationTypeDao.getAll();
	}

	

}
