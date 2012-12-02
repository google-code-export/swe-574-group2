package com.swe.fairurban.Helpers;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONHelper {
public final static Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
	

	public static String ToJson(Object o) 
	{	
		String result = "";

		result = gson.toJson(o);
		return result;
	}
	
	public static String ToJson(Object o, Type classType) 
	{	
		String result = "";

		result = gson.toJson(o, classType);
		return result;
	}
	
	public static Object FromJson(String input, Type classType)
	{
		//Gson gson2 = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-DDThh:mm:ss.sTZD").create();
		
		Object result = gson.fromJson(input, classType );
	
		return result;
	}

}
