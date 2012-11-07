package com.swe.accessibility.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class UserObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5113408664429402589L;

	@Override
	public String toString() {
		return "UserObject [username=" + username + ", password=" + password
				+ "]";
	}

	private String username;
	
	private String password;

	@XmlElement
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
