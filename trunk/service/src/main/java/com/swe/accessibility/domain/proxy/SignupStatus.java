package com.swe.accessibility.domain.proxy;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="status")
public class SignupStatus implements Serializable{

	private String status;
	
	private String error;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
