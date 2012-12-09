package com.swe.accessibility.domain.proxy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="status")
public class LoginStatus {

	    private  boolean loggedIn;
	    private  String username;
	    private String errorReason;

	    public String getErrorReason() {
			return errorReason;
		}

	    @XmlElement(name="error")
		public void setErrorReason(String errorReason) {
			this.errorReason = errorReason;
		}

		public void setLoggedIn(boolean loggedIn) {
			this.loggedIn = loggedIn;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		@XmlElement(name="status")
	    public boolean isLoggedIn() {
	      return loggedIn;
	    }

	    @XmlElement(name="username")
	    public String getUsername() {
	      return username;
	    }

		@Override
		public String toString() {
			
			return "{loggedIn:" + loggedIn + ", username:"
					+ username + ", errorReason:" + errorReason + "}";
		}
	    
	    
	  }
