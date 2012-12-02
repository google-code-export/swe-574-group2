package com.swe.fairurban.Helpers;

import java.util.List;

import com.swe.fairurban.JSONClasses.EntryCategory;

public abstract class CategoryReceivedEvent {
	public abstract void CategoriesReceived(List<EntryCategory> cats);
	public abstract void ErrorOccured();
}
