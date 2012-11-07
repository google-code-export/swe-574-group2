package com.swe.accessibility.domain.proxy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="updateResult")
public class UpdateResult {

	int resultId;
	
	String resultStatus;
	
	@XmlElement(name="resultStatus")
	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	@XmlElement(name="resultId")
	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	
}