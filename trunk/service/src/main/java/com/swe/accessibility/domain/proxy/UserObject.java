package com.swe.accessibility.domain.proxy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class UserObject {
	
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
