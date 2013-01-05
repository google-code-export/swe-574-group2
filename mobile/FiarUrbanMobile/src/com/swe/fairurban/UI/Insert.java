package com.swe.fairurban.UI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import com.google.android.maps.GeoPoint;
import com.swe.fairurban.R;
import com.swe.fairurban.Data.POIData;
import com.swe.fairurban.Helpers.BitmapWorkerTask;
import com.swe.fairurban.Helpers.EntryCategoriesRetriever;
import com.swe.fairurban.Helpers.OnServiceConnectionFinishedEvent;
import com.swe.fairurban.Helpers.POIXMLParser;
import com.swe.fairurban.Helpers.ServiceConnector;
import com.swe.fairurban.Helpers.ServiceHelper;
import com.swe.fairurban.Helpers.UserHelper;
import com.swe.fairurban.JSONClasses.EntryCategory;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Insert extends Activity {

	EditText txtComment;
	EditText txtExtraData;
	TextView lblExtraData;
	Button btnInsert, btnSelectPhoto, btnTakePhoto;
	Context appContext;

	ProgressDialog pdUpload;
	String selectedImageFilePath = null;
	double latitude, longitude;
	
	Spinner spnMainCategories, spnSubCategories;
	
	private static final int ACTIVITY_RESULT_CODE_PICK_IMAGE = 1;
	

	private Uri imageUri;
	
	private final static int TAKE_PICTURE = 1337;
	
	ImageView imgPhoto;
 	
 	Bitmap bmpSelected;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        appContext = this;
        txtComment = (EditText) findViewById(R.id.txtComment);
		btnInsert = (Button) findViewById(R.id.btnInsert);
	
		lblExtraData = (TextView) findViewById(R.id.lblExtraData);
		
		txtExtraData = (EditText) findViewById(R.id.txtExtraData);
		
		lblExtraData.setVisibility(View.INVISIBLE);
		
		txtExtraData.setVisibility(View.INVISIBLE);
		
		btnSelectPhoto = (Button) findViewById(R.id.btnSelectPhoto);
		
		btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
		
		Bundle b = getIntent().getExtras();
		
		latitude = b.getDouble("lat");
		
		longitude = b.getDouble("lng");
		
		spnMainCategories = (Spinner) findViewById(R.id.spnMainCategories);
		
		spnSubCategories = (Spinner) findViewById(R.id.spnSubCategories);
		
		btnTakePhoto.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				takePhoto();
			}
		});
		
		
		btnInsert.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (selectedImageFilePath == null) {
					Toast tLoginError = Toast.makeText(appContext, "Lütfen upload etmek istediðiniz imajý seçin.",1000);
					tLoginError.show();
					return;
				}
				
				EntryCategory selectedSubCategory = (EntryCategory) spnSubCategories.getSelectedItem();
				
				if (selectedSubCategory.id == null || selectedSubCategory.id == -1) {
					Toast tLoginError = Toast.makeText(appContext, "Lütfen kategori seçiminizi yapýn.",1000);
					tLoginError.show();
					return;
				}
				
				String catId = selectedSubCategory.id.toString();
				
				String comment = txtComment.getText().toString();
				
				String extra = txtExtraData.getText().toString();
				
				pdUpload = ProgressDialog.show(appContext, "Kayýt","Sunucuya baðlanýlýyor.", true);
				
				
				ServiceConnector conn = new ServiceConnector(ServiceHelper.GetInsertDataUrl());
				
				conn.FileParameterName = "file";
				
				conn.FilePathToBeUploaded = selectedImageFilePath;
				
				conn.AddParameter("category", catId);
				
				conn.AddParameter("coordX", Double.toString(latitude));
				
				conn.AddParameter("coordY", Double.toString(longitude));
				
				conn.AddParameter("comment", comment);
				
				conn.AddParameter("value", extra);
				
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
		
		
		EntryCategoriesRetriever retriever = new EntryCategoriesRetriever();
		
		List<EntryCategory> mainCategories =  new LinkedList<EntryCategory>();
		
		mainCategories.addAll(retriever.GetMainCategories());
		
		EntryCategory allCats = new EntryCategory();
		allCats.id = -1;
		allCats.title = "Ana Kategori Seçiniz";
		allCats.parentReasonId = -1;
		
		mainCategories.add(0, allCats);

	    ArrayAdapter<EntryCategory> spnMainCategoriesArrayAdapter = new ArrayAdapter<EntryCategory>(this,android.R.layout.simple_spinner_item, mainCategories);

	    spnMainCategories.setAdapter(spnMainCategoriesArrayAdapter);  
	    
	    spnMainCategories.setSelection(0);
	    
	    
	    List<EntryCategory> subCategories = new LinkedList<EntryCategory>();
	    
	    EntryCategory allCatsSub = new EntryCategory();
	    allCatsSub.id = -1;
	    allCatsSub.title = "Alt Kategori Seçiniz";
	    allCatsSub.parentReasonId = -1;
		
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
		
		spnSubCategories.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
					EntryCategory subCatSelected = (EntryCategory) arg0.getSelectedItem();
					if (subCatSelected.id > 0) {
						lblExtraData.setText(subCatSelected.extra.key);
						
						lblExtraData.setVisibility(View.VISIBLE);
						
						txtExtraData.setVisibility(View.VISIBLE);
					}
					else {
						lblExtraData.setVisibility(View.INVISIBLE);
						
						txtExtraData.setVisibility(View.INVISIBLE);
					}
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
    }
    
    
    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
       
        startActivityForResult(intent, TAKE_PICTURE);
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
		allCats.title = "Alt Kategori Seçiniz";
		allCats.parentReasonId = -1;
		
		subCategories.add(0, allCats);

	    ArrayAdapter<EntryCategory> spnSubCategoriesArrayAdapter = new ArrayAdapter<EntryCategory>(this,android.R.layout.simple_spinner_item, subCategories);

	    spnSubCategories.setAdapter(spnSubCategoriesArrayAdapter);  
	    
	    spnSubCategories.setSelection(0);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {
        if (width > height) {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        } else {
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }
    }
    return inSampleSize;
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
		            final String filePath = cursor.getString(columnIndex);
		            cursor.close();
		            selectedImageFilePath = filePath;
	
		           
//		            Thread th = new Thread(new Runnable() {
//						
//						public void run() {
//							// TODO Auto-generated method stub
//							 try {
//					            	
//				            	  final BitmapFactory.Options options = new BitmapFactory.Options();
//				                  options.inJustDecodeBounds = true;
//				               
//				                 
//				                  // Calculate inSampleSize
//				                  options.inSampleSize = calculateInSampleSize(options, 50, 50);
//			
//				                  // Decode bitmap with inSampleSize set
//				                  options.inJustDecodeBounds = false;
//				            	
//				            	imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
//				            	
//				            	bmpSelected = BitmapFactory.decodeFile(filePath, options);
//				            
//			
//				            	imgPhoto.setImageResource(0);
//				            	
//				            	runOnUiThread(new  Runnable() {
//									public void run() {
//										imgPhoto.setImageBitmap(bmpSelected);
//									}
//								});
//				            	
//				            	
//				            	
//					          
//				                
//							} catch (Exception e) {
//								// TODO: handle exception
//							}
//						}
//					});
//		            
//		            th.start();
		            imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
		            BitmapWorkerTask task = new BitmapWorkerTask(imgPhoto);
		            task.execute(filePath);
		           
	                
		           
	
		            
		        }
		        break;
		    case TAKE_PICTURE:
	            if (resultCode == Activity.RESULT_OK) {
	            	
	            	 Toast.makeText(this, "Çekmiþ olduðunuz imajý seçerek devam edebilirsiniz.", Toast.LENGTH_SHORT)
	                 .show();
	            }
	            break;
        }
	}
	
    
}
