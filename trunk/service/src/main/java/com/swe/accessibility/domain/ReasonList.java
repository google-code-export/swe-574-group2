package com.swe.accessibility.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reasons")
public class ReasonList {
	
	
	private List<Reason> data;

	@XmlElement
	public List<Reason> getData() {
		return data;
	}

	public void setData(List<Reason> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReasonList [data=" + data + "]";
	}
	
	

}
