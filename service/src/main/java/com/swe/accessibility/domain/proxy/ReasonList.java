package com.swe.accessibility.domain.proxy;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reasons")
public class ReasonList {
	
	
	private List<ReasonProxy> data;

	@XmlElement
	public List<ReasonProxy> getData() {
		return data;
	}

	public void setData(List<ReasonProxy> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReasonList [data=" + data + "]";
	}
	
	

}
