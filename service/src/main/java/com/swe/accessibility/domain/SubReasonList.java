package com.swe.accessibility.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reasons")
public class SubReasonList {
	
	
	private List<SubReason> data;

	@XmlElement
	public List<SubReason> getData() {
		return data;
	}

	public void setData(List<SubReason> data) {
		this.data = data;
	}

	
	
	
	

}
