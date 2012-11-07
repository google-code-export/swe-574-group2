package com.swe.accessibility.domain.proxy;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entries")
public class EntryList {
	
	
	public List<EntryProxy> data;

	@XmlElement
	public List<EntryProxy> getData() {
		return data;
	}

	public void setData(List<EntryProxy> data) {
		this.data = data;
	}
	
	
	
	

}
