package com.swe.fairurban.Helpers;

public class ServiceHelper {
	private final static String LIST_DATA_URL = "http://testpalette.com/TestPHP/markerInfo.php";
	
	private final static String INSERT_DATA_URL = "http://testpalette.com:8080/RestAccessibilty/service/entries/add";
	
	public static String GetListDataUrl(double latitude, double longitude)
	{
		return String.format(LIST_DATA_URL);
	}
	
	public static String GetInsertDataUrl()
	{
		return INSERT_DATA_URL;
	}
	
}
