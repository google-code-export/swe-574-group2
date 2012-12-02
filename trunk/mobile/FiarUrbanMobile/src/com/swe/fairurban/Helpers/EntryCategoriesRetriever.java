package com.swe.fairurban.Helpers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.swe.fairurban.JSONClasses.EntryCategory;
import com.swe.fairurban.JSONClasses.JSONDataContainer;
import com.swe.fairurban.JSONClasses.ListEntry;


public class EntryCategoriesRetriever {

	private static List<EntryCategory> Categories;
	
	private static HashMap<Integer, List<EntryCategory>> SubCategoriesMap = new HashMap<Integer, List<EntryCategory>>();
	
	Integer catIndexIterator;
	
	public List<EntryCategory> GetMainCategories()
	{
		return Categories;
	}
	
	public List<EntryCategory> GetSubCategories(Integer mainCategoryId)
	{
		return SubCategoriesMap.get(mainCategoryId);
	}
	
	public void RetrieveAllCategories(final CategoryReceivedEvent onReceivedEvent)
	{
	
		RetrieveMainCategories(new CategoryReceivedEvent() {
			
			@Override
			public void ErrorOccured() {
				// TODO Auto-generated method stub
				onReceivedEvent.ErrorOccured();
			}
			
			@Override
			public void CategoriesReceived(List<EntryCategory> cats) {
				// TODO Auto-generated method stub
				Categories = cats;
				
				
				for (int i = 0; i < cats.size(); i++) {
					EntryCategory parentCategory = cats.get(i);
					Boolean result = RetrieveSubCategories(parentCategory.id);
					
					if (!result) {
						onReceivedEvent.ErrorOccured();
						break;
					}
				}
				
				onReceivedEvent.CategoriesReceived(Categories);
			}
		});
	}
	
	private void RetrieveMainCategories(final CategoryReceivedEvent onReceivedEvent)
	{
		ServiceConnector conn = new ServiceConnector(ServiceHelper.GetMainCategoriesUrl());
		
		conn.SetPostRequest(false);
		
		conn.AddListener(new OnServiceConnectionFinishedEvent() {
			
			@Override
			public void ExceptionOccured() {
				onReceivedEvent.ErrorOccured();
			}
			
			@Override
			public void ConnectionFinished(String result) {
				Type listType = new TypeToken<JSONDataContainer<EntryCategory>>() {}.getType();
				
				JSONDataContainer<EntryCategory> data = null;
				
				data = (JSONDataContainer<EntryCategory>)JSONHelper.FromJson(result,listType);	
			
				onReceivedEvent.CategoriesReceived(data.data);
			}
		});
		
		conn.Connect();
	}
	
	
	private Boolean RetrieveSubCategories(Integer parentCategoryId)
	{
		ServiceConnector conn = new ServiceConnector(ServiceHelper.GetSubCategoriesUrl(parentCategoryId));
		
		conn.SetPostRequest(false);
		
		String result = conn.SyncConnect();
		
		if (result != null) {
			Type listType = new TypeToken<JSONDataContainer<EntryCategory>>() {}.getType();
			
			JSONDataContainer<EntryCategory> data = null;
			
			data = (JSONDataContainer<EntryCategory>)JSONHelper.FromJson(result,listType);	
			
			SubCategoriesMap.put(parentCategoryId, data.data);
		
			return true;
		}
		else {
			return false;
		}
		
		
	}
}
