package com.swe.accessibility.domain.proxy;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import com.swe.accessibility.domain.ViolationType;

@XmlRootElement(name="types")
public class TypeList implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 288062370926569694L;
	
	public List<ViolationType> data;

	@XmlElement
	public List<ViolationType> getData() {
		return data;
	}

	public void setData(List<ViolationType> data) {
		this.data = data;
	}

}
