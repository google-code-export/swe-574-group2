package com.swe.accessibility.domain.proxy;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Config implements Serializable{

	private String imageHost;
	
	public Config() {
		
	}
	
	public Config(String imageHost) {
		
		this.imageHost = imageHost;
	}
	
	
	public String getImageHost() {
		return imageHost;
	}
	
	public void setImageHost(String imageHost) {
		this.imageHost = imageHost;
	}
	
	
}
