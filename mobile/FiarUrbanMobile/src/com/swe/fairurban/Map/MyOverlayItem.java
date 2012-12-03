package com.swe.fairurban.Map;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.swe.fairurban.JSONClasses.ListEntry;

public class MyOverlayItem extends OverlayItem {

	ListEntry entry;
	public MyOverlayItem(GeoPoint arg0, String arg1, String arg2, ListEntry entry) {
		super(arg0, arg1, arg2);
		this.entry = entry;
	}

}
