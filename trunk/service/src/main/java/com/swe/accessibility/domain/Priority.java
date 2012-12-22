package com.swe.accessibility.domain;

public enum Priority {
	
	LOW("Low",1),CRITICAL("Critical",2);
	
	private String label;
	
	private int degree;
	
	private Priority(String label,int degree) {
		
		this.setLabel(label);
		this.setDegree(degree);
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}

