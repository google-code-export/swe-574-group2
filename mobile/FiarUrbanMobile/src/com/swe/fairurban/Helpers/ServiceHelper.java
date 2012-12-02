package com.swe.fairurban.Helpers;

public class ServiceHelper {
	
	private final static String MAIN_SERVICE_HOST = "http://testpalette.com:8080/RestAccessibilty";
	
	

	private final static String LIST_DATA_URL = "/service/entries";
	
	private final static String LOGIN_URL = "/service/login/sigin";
	
	private final static String INSERT_DATA_URL = "/service/entries/add";
	
	private final static String GET_CATEGORIES_URL = "/service/categories/";
	
	
	public static String GetListDataUrl()
	{
		return MAIN_SERVICE_HOST + String.format(LIST_DATA_URL);
	}
	
	public static String GetListDataForCategoryUrl(Integer subCategoryId)
	{
		return MAIN_SERVICE_HOST + String.format(LIST_DATA_URL) + "?categoryId=" + subCategoryId;
	}
	
	public static String GetMainCategoriesUrl()
	{
		return MAIN_SERVICE_HOST + GET_CATEGORIES_URL;
	}
	
	public static String GetSubCategoriesUrl(Integer parentCategoryId)
	{
		return MAIN_SERVICE_HOST + GET_CATEGORIES_URL + parentCategoryId;
	}
	
	public static String GetInsertDataUrl()
	{
		return MAIN_SERVICE_HOST + INSERT_DATA_URL;
	}
	
	public static String GetLoginUrl()
	{
		return MAIN_SERVICE_HOST + LOGIN_URL;
	}
	
}
