package com.swe.fairurban.UI;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import com.google.android.maps.GeoPoint;
import com.swe.fairurban.R;
import com.swe.fairurban.Data.POIData;
import com.swe.fairurban.Helpers.OnServiceConnectionFinishedEvent;
import com.swe.fairurban.Helpers.POIXMLParser;
import com.swe.fairurban.Helpers.ServiceConnector;
import com.swe.fairurban.Helpers.ServiceHelper;
import com.swe.fairurban.Helpers.UserHelper;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Insert extends Activity {

	EditText txtComment;
	EditText txtPassword;
	Button btnInsert, btnSelectPhoto;
	Context appContext;
	ImageView imgPhoto;
	ProgressDialog pdUpload;
	String selectedImageFilePath = null;
	double latitude, longitude;
	
	private static final int ACTIVITY_RESULT_CODE_PICK_IMAGE = 1;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        appContext = this;
        txtComment = (EditText) findViewById(R.id.txtComment);
		btnInsert = (Button) findViewById(R.id.btnInsert);
		imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
		btnSelectPhoto = (Button) findViewById(R.id.btnSelectPhoto);
		
		Bundle b = getIntent().getExtras();
		
		latitude = b.getDouble("lat");
		
		longitude = b.getDouble("lng");
		
		
		btnInsert.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (selectedImageFilePath == null) {
					Toast tLoginError = Toast.makeText(appContext, "Lütfen upload etmek istediðiniz imajý seçin.",1000);
					tLoginError.show();
					return;
				}
				
				String comment = txtComment.getText().toString();
				
				
			
				
				
				pdUpload = ProgressDialog.show(appContext, "Kayýt","Sunucuya baðlanýlýyor.", true);
				
				
				ServiceConnector conn = new ServiceConnector(ServiceHelper.GetInsertDataUrl());
				
				conn.FileParameterName = "file";
				
				conn.FilePathToBeUploaded = selectedImageFilePath;
				
				conn.AddParameter("category", "1");
				
				conn.AddParameter("coordX", Double.toString(latitude));
				
				conn.AddParameter("coordY", Double.toString(longitude));
				
				conn.AddParameter("comment", comment);
				
				
				conn.AddListener(new OnServiceConnectionFinishedEvent() {
					
					@Override
					public void ExceptionOccured() {
						// TODO Auto-generated method stub
						runOnUiThread(new Runnable() {
							
							public void run() {
								// TODO Auto-generated method stub
								pdUpload.dismiss();
								
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
								pdUpload.dismiss();
								setResult(RESULT_OK);
								finish();
							}
						});
					
					}
				});
				
				conn.MultipartConnect();
				
				
				pdUpload.setMessage("Dosya upload ediliyor.");
			}
		});
		
		
		btnSelectPhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Resmi Seç"), ACTIVITY_RESULT_CODE_PICK_IMAGE);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case ACTIVITY_RESULT_CODE_PICK_IMAGE:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getContentResolver().query(
	                               selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();
	            selectedImageFilePath = filePath;

	            Bitmap bmpSelected = BitmapFactory.decodeFile(filePath);
	            
	            imgPhoto.setImageBitmap(bmpSelected);
	        }
	    }
	}
    
}
