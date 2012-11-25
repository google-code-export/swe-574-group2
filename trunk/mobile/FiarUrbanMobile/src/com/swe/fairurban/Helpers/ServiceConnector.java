package com.swe.fairurban.Helpers;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ServiceConnector {
	
	private LinkedList<OnServiceConnectionFinishedEvent> eventListeners = new LinkedList<OnServiceConnectionFinishedEvent>();
	
	public String FilePathToBeUploaded = "";

	public String FileParameterName = "";
	
	private LinkedList<NameValuePair> parameterPairs = new LinkedList<NameValuePair>();
	
	private String url;
	
	public void AddListener(OnServiceConnectionFinishedEvent event)
	{
		eventListeners.add(event);
	}
	
	public void RemoveListener(OnServiceConnectionFinishedEvent event)
	{
		eventListeners.remove(event);
	}
	
	private void BroadcastException()
	{
		for (OnServiceConnectionFinishedEvent event : eventListeners) {
			event.ExceptionOccured();
		}
	}
	
	private void BroadcastConnectionFinished(String result)
	{
		for (OnServiceConnectionFinishedEvent event : eventListeners) {
			event.ConnectionFinished(result);
		}
	}
	
	public ServiceConnector(String url)
	{
		this.url = url;
	}


	
	public void AddParameter(String name, String value)
	{
		NameValuePair pair = new BasicNameValuePair(name, value);
		parameterPairs.add(pair);
	}
	
	public void Connect()
	{
		
		   Thread thConnection = new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					
			        HttpClient hc = new DefaultHttpClient();
			        String message;

			        HttpPost p = new HttpPost(url);
//			        JSONObject object = new JSONObject();
			        try {
//			        	if (pairs != null) {
//			        		for (NameValuePair nameValuePair : pairs) {
//								object.put(nameValuePair.getName(),nameValuePair.getValue());
//							}
//						}
//			        	
//
//			        } catch (Exception ex) {
//			        	BroadcastException();
//			        	return;
//			        }
//
//			        try {
//			        message = object.toString();
//
//
//			        p.setEntity(new StringEntity(message, "UTF8"));
//			        p.setHeader("Content-type", "application/json");
			        
		        	if (parameterPairs.size() > 0) {
			    	    p.setEntity(new UrlEncodedFormEntity(parameterPairs));
			     	}
			    
			        
			     
			        HttpResponse resp = hc.execute(p);
			        if (resp != null) {
			            if (resp.getStatusLine().getStatusCode() == 200)
			                BroadcastConnectionFinished(EntityUtils.toString(resp.getEntity()));
			            }

			        } catch (Exception e) {
			            e.printStackTrace();
			            BroadcastException();
			        }
				}
				
			});
		   
		   
		   thConnection.start();
	}
	
	public void MultipartConnect()
	{
		Thread thConnection = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				String fileUrl = FilePathToBeUploaded;
				String fileParameterName = FileParameterName;
				
				File file = new File(fileUrl);
				try {
					
					
			         HttpClient client = new DefaultHttpClient();  
			  
			         HttpPost post = new HttpPost(url);
			         
			    
			    
			         
				     FileBody bin = new FileBody(file);
				     MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
				     reqEntity.addPart(fileParameterName, bin);
				     

			     	if (parameterPairs.size() > 0) {
			     		for (NameValuePair pair : parameterPairs) {
			     			reqEntity.addPart(pair.getName(),new StringBody(pair.getValue()));
						}
			     	}
				     
				     post.setEntity(reqEntity);  
				     HttpResponse response = client.execute(post);  
				     HttpEntity resEntity = response.getEntity();  
				     
				     String responseString = EntityUtils.toString(response.getEntity());
				     
				     if (response != null) {
			            if (response.getStatusLine().getStatusCode() == 200)
			            {
			                BroadcastConnectionFinished(responseString);
			                return;
			            }
				     }
				     
				     BroadcastException();

				      
				     
				} catch (Exception e) {
				    e.printStackTrace();
				    BroadcastException();
				}
			}
		});
		
		thConnection.start();
		
	}
	
	
	
}
