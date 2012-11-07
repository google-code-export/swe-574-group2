package com.swe.accessibility.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="entryResult")
public class InsertEntryResult {

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

	@XmlElement(name="entryId")
	public int getEntryId() {
		return entryId;
	}

	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}

	int entryId;
}
