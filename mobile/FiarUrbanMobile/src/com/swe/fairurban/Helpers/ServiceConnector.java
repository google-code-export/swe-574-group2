package com.swe.fairurban.Helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ServiceConnector {
	
	private LinkedList<OnServiceConnectionFinishedEvent> eventListeners = new LinkedList<OnServiceConnectionFinishedEvent>();
	
	public String FilePathToBeUploaded = "";

	public String FileParameterName = "";
	
	private static HttpClient hc = new DefaultHttpClient();
	
	private LinkedList<NameValuePair> parameterPairs = new LinkedList<NameValuePair>();
	
	private String url;
	
	private Boolean PostRequest = true;
	
	
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
	
	public void SetPostRequest(Boolean req)
	{
		PostRequest = req;
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
					
			        
			        String message;

			        HttpResponse resp = null;
			        
			        
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
			        	
			        	if (PostRequest) {
			        		HttpPost p = new HttpPost(url);
					        
				        	if (parameterPairs.size() > 0) {
					    	    p.setEntity(new UrlEncodedFormEntity(parameterPairs));
					     	}
				
					        resp = hc.execute(p);
						}
			        	else {
			        		HttpGet get = new HttpGet(url);
			        
			        		resp = hc.execute(get);
						}
			        	
				        
				        
				        
				        if (resp != null) {
				            if (resp.getStatusLine().getStatusCode() == 200)
				                BroadcastConnectionFinished(EntityUtils.toString(resp.getEntity(),"UTF-8"));
				            else
				            	BroadcastException();
				        }
				        else {
							BroadcastException();
						}

			        } catch (Exception e) {
			            e.printStackTrace();
			            BroadcastException();
			        }
				}
				
			});
		   
		   
		   thConnection.start();
	}
	
	public void ConnectUsingJsonPost()
	{
		
		   Thread thConnection = new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					
			        
			        String message;

			        HttpResponse resp = null;
			        
			        HttpPost p = new HttpPost(url);
			        
			        JSONObject object = new JSONObject();
			        try {
			        	if (parameterPairs != null) {
			        		for (NameValuePair nameValuePair : parameterPairs) {
								object.put(nameValuePair.getName(),nameValuePair.getValue());
							}
						}
			        	

			        } catch (Exception ex) {
			        	BroadcastException();
			        	return;
			        }

				   try {
				        message = object.toString();
	
	
				        p.setEntity(new StringEntity(message, "UTF8"));
				        p.setHeader("Content-type", "application/json");
				        	
				      
				        	
			            resp = hc.execute(p);
				        
				        
				        if (resp != null) {
				            if (resp.getStatusLine().getStatusCode() == 200)
				                BroadcastConnectionFinished(EntityUtils.toString(resp.getEntity(),"UTF-8"));
				            else
				            	BroadcastException();
				        }
				        else {
							BroadcastException();
						}

			        } catch (Exception e) {
			            e.printStackTrace();
			            BroadcastException();
			        }
				}
				
			});
		   
		   
		   thConnection.start();
	}
	
	public String SyncConnect()
	{	        
        String message = null;

        HttpResponse resp = null;
        
        try {

        	if (PostRequest) {
        		HttpPost p = new HttpPost(url);
		        
	        	if (parameterPairs.size() > 0) {
		    	    p.setEntity(new UrlEncodedFormEntity(parameterPairs));
		     	}
	
		        resp = hc.execute(p);
			}
        	else {
        		HttpGet get = new HttpGet(url);
        		
        		resp = hc.execute(get);
			}
        	
	        
	        
	        
	        if (resp != null) {
	            if (resp.getStatusLine().getStatusCode() == 200)
	            	message = EntityUtils.toString(resp.getEntity(),"UTF-8");
	          
	        }

        } catch (Exception e) {
            e.printStackTrace();
            BroadcastException();
        }
        
        
        return message;
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
					
					
			         HttpClient client = hc;  
			  
			         HttpPost post = new HttpPost(url);
			         
			         
			         Options opts = new BitmapFactory.Options();
			         opts.inSampleSize = 2;   // for 1/2 the image to be loaded

			         Bitmap original = BitmapFactory.decodeFile(fileUrl, opts);
			         
//			         Integer[] newSize = BitmapHelper.GetThumbnailSize(original, 100, 100);
//			       
//			         Bitmap thumb = Bitmap.createScaledBitmap (original,newSize[0], newSize[1], false);
//			         
			         
				     //FileBody bin = new FileBody(file);
				  
				     
				     ByteArrayOutputStream stream = new ByteArrayOutputStream();
				     original.compress(CompressFormat.JPEG, 30, stream);
				     InputStream is = new ByteArrayInputStream(stream.toByteArray());
				     
				     ContentBody bin =  new InputStreamBody(is, "img.jpeg");
				
				
				     
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
