package com.swe.fairurban.UI;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.swe.fairurban.R;
import com.swe.fairurban.Data.POIData;
import com.swe.fairurban.Helpers.LocationHelper;
import com.swe.fairurban.Helpers.OnServiceConnectionFinishedEvent;
import com.swe.fairurban.Helpers.POIXMLParser;
import com.swe.fairurban.Helpers.ServiceConnector;
import com.swe.fairurban.Helpers.ServiceHelper;
import com.swe.fairurban.Helpers.UserHelper;
import com.swe.fairurban.Map.MyItemizedOverlay;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.SyncStateContract.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Map extends MapActivity {

	MapView map;
	MapController mapController;
	Context appContext;
	Button btnRefresh, btnAddNewToCurrentLocation;
	Boolean locUpdated = false;
	LocationManager locationManager;
	LocationListener locationListener;
	
	double currentLatitude, currentLongitude;
	
	public static final int ACTIVITY_RESULT_CODE_INSERT = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		map = (MapView) findViewById(R.id.map);
		
		btnRefresh = (Button) findViewById(R.id.btnRefresh);
		
		btnAddNewToCurrentLocation = (Button) findViewById(R.id.btnAddCurrentLocation);
		
		appContext = this;
		
		
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RefreshMap();
			}
		});
		
		btnAddNewToCurrentLocation.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				if (locUpdated == false) {
					Toast tGPSException = Toast.makeText(appContext, "Henüz lokasyonunuz bulunamadý.",1000);
					tGPSException.show();
					return;
				}
				
				Bundle b = new Bundle();
				b.putDouble("lat", currentLatitude);
				b.putDouble("lng", currentLongitude);
				
				Intent insertIntent = new Intent(view.getContext(), Insert.class);
				
				insertIntent.putExtras(b);
				
                startActivityForResult(insertIntent,ACTIVITY_RESULT_CODE_INSERT);
			}
		});
		
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
		    AlertForGps();
		}
		
		mapController = map.getController();
		
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				
				if (locUpdated == true) {
					return;
				}
				
				currentLatitude = location.getLatitude();
				
				currentLongitude = location.getLongitude();
				
				locUpdated = true;
			    GeoPoint point = LocationHelper.ConvertLocationToGeoPoint(location);
		
				
				mapController.animateTo(point);
				
				mapController.setZoom(11); //Fixed Zoom Level
				
				locationManager.removeUpdates(locationListener);
				
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};
		

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


		RefreshMap();
		
		
	}
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
		    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

		    switch(requestCode) { 
		    case ACTIVITY_RESULT_CODE_INSERT:
		        if(resultCode == RESULT_OK){  
		            RefreshMap();
		        	runOnUiThread(new Runnable() {
						
						public void run() {
							// TODO Auto-generated method stub
							Toast tConnectionException = Toast.makeText(appContext, "Kayýt baþarýyla eklendi.",1000);
							tConnectionException.show();
						}
					});
		        }
		    }
		}
	
	private void RefreshMap()
	{
		Location locLastGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		Location locLastNetwork = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if (locLastGPS != null) {
			GetData(locLastGPS.getLatitude(), locLastGPS.getLongitude());
		}
		else if (locLastNetwork != null) {
			GetData(locLastNetwork.getLatitude(), locLastNetwork.getLongitude());
		}
		else {
			GeoPoint mapCenter = map.getMapCenter();
			double latitude = mapCenter.getLatitudeE6() / 1E6;
			double longitude = mapCenter.getLongitudeE6() / 1E6;
			GetData(latitude, longitude);
		}
	}
	
	
	private void AlertForGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("GPS kapalý, açmak ister misiniz?")
	           .setCancelable(false)
	           .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                   startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("Hayýr", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                    dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}

	private void GetData(double latitude, double longitude) {
	
		
		ServiceConnector conn = new ServiceConnector(ServiceHelper.GetListDataUrl(latitude, longitude));
		conn.AddListener(new OnServiceConnectionFinishedEvent() {
			
			@Override
			public void ExceptionOccured() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						Toast tConnectionException = Toast.makeText(appContext, "Baðlantý hatasý",1000);
						tConnectionException.show();
					}
				});
				
			}
			
			@Override
			public void ConnectionFinished(final String result) {
				// TODO Auto-generated method stub
				//Parse data and show on map
				runOnUiThread(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						POIXMLParser xmlParser = new POIXMLParser();
						List<POIData> data = xmlParser.ParseXML(result);
						DrawPOIsOnMap(data);
					}
				});
			
			}
		});
		
		conn.Connect();
	}
	
	private void DrawPOIsOnMap(List<POIData> data)
	{
		List< Overlay > mapOverlays = map.getOverlays();
		
		mapOverlays.clear();
		
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);

		MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this);

		for (POIData poiData : data) {
			GeoPoint point = LocationHelper.CreateGeoPoint(poiData.Latitude, poiData.Longitude);
			OverlayItem overlayitem = new OverlayItem(point, "", poiData.Name);
			
			itemizedoverlay.addOverlay(overlayitem);
		}
		
		mapOverlays.add(itemizedoverlay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
