package com.swe.accessibility.domain.proxy;

public class Extra {
	
	private String key;
	
	private String value;
	
	private String boundary;
	
	public Extra() {
		
	}
	
	public Extra(String key, String boundary, String value){
		
		this.key = key;
		this.boundary = boundary;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

}
