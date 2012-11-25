package com.swe.fairurban.Helpers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.swe.fairurban.Data.POIData;

public class POIXMLParser {
	public List<POIData> ParseXML(String xml)
	{
		ArrayList<POIData> result = new ArrayList<POIData>();
		
		try {
			InputStream in = new ByteArrayInputStream(xml.getBytes());
			
			XmlPullParser parser = Xml.newPullParser();
	        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	        parser.setInput(in, null);
	        parser.nextTag();
	        
	        parser.require(XmlPullParser.START_TAG, null, "markers");
	        while (parser.next() != XmlPullParser.END_TAG) {
	            if (parser.getEventType() != XmlPullParser.START_TAG) {
	                continue;
	            }
	            String name = parser.getName();
	            // Starts by looking for the entry tag
	            if (name.equals("marker")) {
	                result.add(readEntry(parser));
	            } else {
	                skip(parser);
	            }
	        }  
	        
			
		} catch (Exception e) {
			
		}
	
		return result;
	}
	
	private POIData readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		
		POIData result = new POIData();
		
		String nameStr = parser.getAttributeValue(null, "comment"); 
        String idStr = parser.getAttributeValue(null, "id"); 
        String latStr = parser.getAttributeValue(null, "lat"); 
        String lngStr = parser.getAttributeValue(null, "lng"); 
        
        result.Id = Integer.parseInt(idStr);
        result.Name = nameStr;
        result.Latitude = Double.parseDouble(latStr);
        result.Longitude = Double.parseDouble(lngStr);
		
	    parser.require(XmlPullParser.START_TAG, null, "marker");
	    
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
//	        if (name.equals("title")) {
//	            title = readTitle(parser);
//	        }
	        
	      
	    }
	    return result;
	}
	
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
}
