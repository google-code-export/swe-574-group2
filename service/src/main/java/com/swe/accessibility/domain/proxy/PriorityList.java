package com.swe.accessibility.domain.proxy;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="priorities")
public class PriorityList implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 288062370926569694L;
	
	public List<String> data;

	@XmlElement
	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}
