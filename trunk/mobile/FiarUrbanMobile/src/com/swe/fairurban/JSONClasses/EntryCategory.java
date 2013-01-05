package com.swe.fairurban.JSONClasses;

import java.util.List;

public class EntryCategory {

	
	public Integer id;
	public String title;
	public Integer parentReasonId;
	public ExtraData extra;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.title;
	}
	
}
