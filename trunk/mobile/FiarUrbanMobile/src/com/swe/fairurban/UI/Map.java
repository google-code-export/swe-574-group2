package com.swe.fairurban.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.gson.reflect.TypeToken;
import com.swe.fairurban.R;
import com.swe.fairurban.Data.POIData;
import com.swe.fairurban.Helpers.CategoryReceivedEvent;
import com.swe.fairurban.Helpers.EntryCategoriesRetriever;
import com.swe.fairurban.Helpers.JSONHelper;
import com.swe.fairurban.Helpers.LocationHelper;
import com.swe.fairurban.Helpers.OnServiceConnectionFinishedEvent;
import com.swe.fairurban.Helpers.POIXMLParser;
import com.swe.fairurban.Helpers.ServiceConnector;
import com.swe.fairurban.Helpers.ServiceHelper;
import com.swe.fairurban.Helpers.UserHelper;
import com.swe.fairurban.JSONClasses.EntryCategory;
import com.swe.fairurban.JSONClasses.JSONDataContainer;
import com.swe.fairurban.JSONClasses.ListEntry;
import com.swe.fairurban.Map.MyItemizedOverlay;
import com.swe.fairurban.Map.TappedLocationOverlay;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.SyncStateContract.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Map extends MapActivity {

	MapView map;
	MapController mapController;
	Context appContext;
	Button btnRefresh, btnAddNewToCurrentLocation;
	Boolean locUpdated = false;
	LocationManager locationManager;
	LocationListener locationListener;
	
	Spinner spnMainCategories, spnSubCategories;
	
	ProgressDialog pd;
	
	double currentLatitude, currentLongitude;
	
	public static final int ACTIVITY_RESULT_CODE_INSERT = 1;
	
	TappedLocationOverlay tappedLocationOverlay = null;
	
	GeoPoint tappedPoint = null;
	
	private GestureDetector gestureDetector;
	
	
	long lastTappedTime = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

	
		
		map = (MapView) findViewById(R.id.map);
		
		btnRefresh = (Button) findViewById(R.id.btnRefresh);
		
		spnMainCategories = (Spinner) findViewById(R.id.spnMainCategories);
		
		spnSubCategories = (Spinner) findViewById(R.id.spnSubCategories);
		
		
		btnAddNewToCurrentLocation = (Button) findViewById(R.id.btnAddCurrentLocation);
		
		appContext = this;
		
		pd = new ProgressDialog(this);
		
		pd.setCancelable(false);
		
		pd.setMessage("Sunucudan ana kategorilerin listesi alýnýyor.");
		
		pd.show();
		
		EntryCategoriesRetriever retriever = new EntryCategoriesRetriever();
		
		retriever.RetrieveAllCategories(new CategoryReceivedEvent() {
			
			@Override
			public void ErrorOccured() {
				runOnUiThread(new Runnable() {
					public void run() {
						pd.dismiss();
						Toast tGetError = Toast.makeText(appContext, "Beklenmeyen bir hata oluþtu. Yazýlmý yeniden baþlatýn.",1000);
						tGetError.show();
					}
				});
				
			}
			
			@Override
			public void CategoriesReceived(List<EntryCategory> cats) {
				
				runOnUiThread(new Runnable() {
					
					public void run() {
						pd.dismiss();
						SetupMapView();
					}
				});
			}
		});
		
		
	}
	
	protected void SetupMapView()
	{
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


		EntryCategoriesRetriever retriever = new EntryCategoriesRetriever();
		
		List<EntryCategory> mainCategories =  new LinkedList<EntryCategory>();
		
		mainCategories.addAll(retriever.GetMainCategories());
		
		EntryCategory allCats = new EntryCategory();
		allCats.id = -1;
		allCats.title = "Hepsi";
		allCats.parentReasonId = -1;
		
		mainCategories.add(0, allCats);

	    ArrayAdapter<EntryCategory> spnMainCategoriesArrayAdapter = new ArrayAdapter<EntryCategory>(this,android.R.layout.simple_spinner_item, mainCategories);

	    spnMainCategories.setAdapter(spnMainCategoriesArrayAdapter);  
	    
	    spnMainCategories.setSelection(0);
	    
	    
	    List<EntryCategory> subCategories = new LinkedList<EntryCategory>();
	    
	    EntryCategory allCatsSub = new EntryCategory();
		allCats.id = -1;
		allCats.title = "Hepsi";
		allCats.parentReasonId = -1;
		
		subCategories.add(allCatsSub);
		
		ArrayAdapter<EntryCategory> spnSubCategoriesArrayAdapter = new ArrayAdapter<EntryCategory>(this,android.R.layout.simple_spinner_item, subCategories);

		spnSubCategories.setAdapter(spnSubCategoriesArrayAdapter);  
		    
		spnSubCategories.setSelection(0);
		
		spnMainCategories.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				EntryCategory catSelected = (EntryCategory) arg0.getSelectedItem();
				
				PopulateSubCategoriesSpinner(catSelected.id);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		
	
		
		
		 map.setOnTouchListener(new View.OnTouchListener() {


				public boolean onTouch(View v, MotionEvent event) {
					
					if(event.getAction() != MotionEvent.ACTION_UP)
					{
						return false;
					}
					
					
					long newTapTime = System.currentTimeMillis();
					
					if (lastTappedTime == -1) {
						lastTappedTime = newTapTime;
						return false;
					}
					else {
						if (newTapTime - lastTappedTime > 200) {
							lastTappedTime = newTapTime;
							return false;
						}
					}
				
					GeoPoint p = null;

		            if (event.getAction() == MotionEvent.ACTION_UP) {
		                p = map.getProjection().fromPixels((int) event.getX(),
		                        (int) event.getY());
		               
		                List< Overlay > mapOverlays = map.getOverlays();
	                	
		                
		                Drawable drawable = appContext.getResources().getDrawable(R.drawable.tappedmarker);
		                
		                if (tappedLocationOverlay != null) {
		                	
		                	mapOverlays.remove(tappedLocationOverlay);
		                	
						}
		                
		                tappedLocationOverlay = new TappedLocationOverlay(drawable, appContext);
		                
		                tappedPoint = p;
		            
	    				OverlayItem overlayitem = new OverlayItem(p, "", "Kullanýcý tarafýndan seçilen lokasyon");
	    				
	    				tappedLocationOverlay.addOverlay(overlayitem);
		    			
		    			mapOverlays.add(tappedLocationOverlay);
		                
		    			map.invalidate();
		    			
		                return true;
		           
		            }
		            return false;
				}
		    });
	    

		RefreshMap();
		
	}
	
	
	protected void PopulateSubCategoriesSpinner(Integer parentCategoryId)
	{
		EntryCategoriesRetriever retriever = new EntryCategoriesRetriever();
		
		List<EntryCategory> subCategories =  new LinkedList<EntryCategory>();
		
		if (parentCategoryId != -1) {
			subCategories.addAll(retriever.GetSubCategories(parentCategoryId));
		}
		
		EntryCategory allCats = new EntryCategory();
		allCats.id = -1;
		allCats.title = "Hepsi";
		allCats.parentReasonId = -1;
		
		subCategories.add(0, allCats);

	    ArrayAdapter<EntryCategory> spnSubCategoriesArrayAdapter = new ArrayAdapter<EntryCategory>(this,android.R.layout.simple_spinner_item, subCategories);

	    spnSubCategories.setAdapter(spnSubCategoriesArrayAdapter);  
	    
	    spnSubCategories.setSelection(0);
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
	
		pd.setMessage("Sunucudan engeller listesi alýnýyor.");
		
		pd.show();
		
		String url = null;
		
		EntryCategory selectedSubCategory = (EntryCategory) spnSubCategories.getSelectedItem();
		
		if (selectedSubCategory.id != null && selectedSubCategory.id != -1) {
			url = ServiceHelper.GetListDataForCategoryUrl(selectedSubCategory.id);
		}
		else {
			url = ServiceHelper.GetListDataUrl();
		}
		
		ServiceConnector conn = new ServiceConnector(url);
		
		conn.SetPostRequest(false);
		
		conn.AddListener(new OnServiceConnectionFinishedEvent() {
			
			@Override
			public void ExceptionOccured() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						pd.dismiss();
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
						
						Type listType = new TypeToken<JSONDataContainer<ListEntry>>() {}.getType();
						
						JSONDataContainer<ListEntry> data = null;
						
						data = (JSONDataContainer<ListEntry>)JSONHelper.FromJson(result,listType);	
					
						DrawPOIsOnMap(data.data);
						
						pd.dismiss();
					}
				});
			
			}
		});
		
		conn.Connect();
	}
	
	private void DrawPOIsOnMap(List<ListEntry> data)
	{
		
		List< Overlay > mapOverlays = map.getOverlays();
		
		mapOverlays.clear();
		
		if (data.size() > 0) {
			Drawable drawable = this.getResources().getDrawable(R.drawable.marker);

			MyItemizedOverlay itemizedoverlay = new MyItemizedOverlay(drawable, this);

			for (ListEntry poiData : data) {
				GeoPoint point = LocationHelper.CreateGeoPoint(poiData.coordX, poiData.coordY);
				OverlayItem overlayitem = new OverlayItem(point, "", poiData.comment);
				
				itemizedoverlay.addOverlay(overlayitem);
			}
			
			mapOverlays.add(itemizedoverlay);
		}
		
		
		MyLocationOverlay myLoc = new MyLocationOverlay(this, map);
		
		myLoc.enableMyLocation();
		mapOverlays.add(myLoc);
		
		
		if (tappedLocationOverlay != null) {
			
  			mapOverlays.add(tappedLocationOverlay);
              
		}
		
		map.invalidate();
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
