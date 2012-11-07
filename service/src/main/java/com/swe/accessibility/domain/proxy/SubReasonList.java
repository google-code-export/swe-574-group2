package com.swe.accessibility.domain.proxy;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reasons")
public class SubReasonList {
	
	
	private List<SubReasonProxy> data;

	@XmlElement
	public List<SubReasonProxy> getData() {
		return data;
	}

	public void setData(List<SubReasonProxy> data) {
		this.data = data;
	}

	
	
	
	

}
