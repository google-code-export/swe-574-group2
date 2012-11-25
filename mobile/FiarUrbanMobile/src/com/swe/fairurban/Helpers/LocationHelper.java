package com.swe.fairurban.Helpers;

import android.location.Location;

import com.google.android.maps.GeoPoint;

public class LocationHelper {
	public static GeoPoint CreateGeoPoint(double latitude, double longitude)
	{
		int lat = (int) (latitude * 1E6);
	    int lng = (int) (longitude * 1E6);
	    GeoPoint point = new GeoPoint(lat, lng);
	    
	    return point;
	}
	
	
	public static GeoPoint ConvertLocationToGeoPoint(Location l)
	{
		int lat = (int) (l.getLatitude() * 1E6);
	    int lng = (int) (l.getLongitude() * 1E6);
	    GeoPoint point = new GeoPoint(lat, lng);
	    
	    return point;
	}
}
