package com.swe.fairurban.UI;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import com.google.android.maps.GeoPoint;
import com.google.gson.reflect.TypeToken;
import com.swe.fairurban.R;
import com.swe.fairurban.Data.POIData;
import com.swe.fairurban.Helpers.JSONHelper;
import com.swe.fairurban.Helpers.OnServiceConnectionFinishedEvent;
import com.swe.fairurban.Helpers.POIXMLParser;
import com.swe.fairurban.Helpers.ServiceConnector;
import com.swe.fairurban.Helpers.ServiceHelper;
import com.swe.fairurban.Helpers.UserHelper;
import com.swe.fairurban.JSONClasses.JSONDataContainer;
import com.swe.fairurban.JSONClasses.JSONEntityContainer;
import com.swe.fairurban.JSONClasses.ListEntry;

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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends Activity
{

	TextView lblCommentText, lblUsernameText, lblVote;

	ImageButton ibThumbUp, ibThumbDown;
	Context appContext;
	ImageView imgEntry;
	ProgressDialog pd;
	EditText txtExtraData;
	TextView lblExtraData;
	TextView lblCurrentStatus;
	ListEntry entry;

	Button btnSolved, btnSave;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		appContext = this;

		pd = new ProgressDialog(this);

		lblCommentText = (TextView) findViewById(R.id.lblCommentText);
		lblUsernameText = (TextView) findViewById(R.id.lblUsernameText);

		lblVote = (TextView) findViewById(R.id.lblVoteText);

		ibThumbUp = (ImageButton) findViewById(R.id.ibThumbUp);
		ibThumbDown = (ImageButton) findViewById(R.id.ibThumbDown);

		imgEntry = (ImageView) findViewById(R.id.imgEntry);

		Bundle b = getIntent().getExtras();

		entry = (ListEntry) b.get("entry");

		lblCommentText.setText(entry.comment);
		lblUsernameText.setText(entry.userName);

		lblExtraData = (TextView) findViewById(R.id.lblExtraData);
		
		lblCurrentStatus = (TextView) findViewById(R.id.lblCurrentStatus);

		txtExtraData = (EditText) findViewById(R.id.txtExtraData);

		btnSolved = (Button) findViewById(R.id.btnSolved);

		btnSave = (Button) findViewById(R.id.btnSave);
		
		// lblVote.setText(entry.upVoteCount + " / " + entry.downVoteCount);

		pd.setMessage("Ýçerik yükleniyor.");

		ServiceConnector conn = new ServiceConnector(ServiceHelper.GetEntiryDetailsUrl(entry.id));

		conn.SetPostRequest(false);

		conn.AddListener(new OnServiceConnectionFinishedEvent()
		{

			@Override
			public void ExceptionOccured()
			{
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable()
				{

					public void run()
					{
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast tConnectionException = Toast.makeText(appContext, "Baðlantý hatasý", 1000);
						tConnectionException.show();
					}
				});

			}

			@Override
			public void ConnectionFinished(final String result)
			{
				// TODO Auto-generated method stub
				// Parse data and show on map
				runOnUiThread(new Runnable()
				{

					public void run()
					{
						// TODO Auto-generated method stub

						Type listType = new TypeToken<JSONDataContainer<ListEntry>>()
						{
						}.getType();

						JSONDataContainer<ListEntry> data = null;

						data = (JSONDataContainer<ListEntry>) JSONHelper.FromJson(result, listType);

						entry = data.data.get(0);
						
						CreateForm();

						pd.dismiss();
					}
				});

			}
		});
		
		conn.Connect();
		
		pd.show();
	}
	
	private void CreateForm()
	{
		if (entry.fixed)
		{
			lblCurrentStatus.setText("Çözüldü");
		} 
		else
		{
			lblCurrentStatus.setText("Çözülmedi");
		}
		
		Thread thImage = new Thread(new Runnable()
		{

			public void run()
			{
				// TODO Auto-generated method stub
				try
				{
					URL url = new URL(ServiceHelper.IMAGES_FOLDER + entry.imageMeta);
					final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

					runOnUiThread(new Runnable()
					{
						public void run()
						{
							imgEntry.setImageBitmap(bmp);
						}
					});

				} catch (Exception e)
				{
					runOnUiThread(new Runnable()
					{
						public void run()
						{
							Toast tImageError = Toast.makeText(appContext, "Ýmaj bulunamadý.", 1000);
							tImageError.show();
						}
					});

				}
			}
		});

		thImage.start();

		ibThumbUp.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{
				ServiceConnector conn = new ServiceConnector(ServiceHelper.GetVotingUrl());

				conn.AddParameter("entryId", entry.id.toString());

				conn.AddParameter("up", "true");

				conn.AddListener(new OnServiceConnectionFinishedEvent()
				{

					@Override
					public void ExceptionOccured()
					{
						runOnUiThread(new Runnable()
						{
							public void run()
							{
								pd.dismiss();

								Toast tConnError = Toast.makeText(appContext, "Sunucu ile baðlantý kurulamadý.", 1000);
								tConnError.show();
							}
						});
					}

					@Override
					public void ConnectionFinished(String result)
					{
						runOnUiThread(new Runnable()
						{
							public void run()
							{
								pd.dismiss();

								Toast tConnSuccess = Toast.makeText(appContext, "Oyunuz baþarý ile eklendi.", 1000);
								tConnSuccess.show();

								RefreshVoteCount();
							}
						});
					}
				});

				conn.ConnectUsingJsonPost();

				pd.setMessage("Oyunuz kaydediliyor...");

				pd.show();
			}
		});

		ibThumbDown.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{
				ServiceConnector conn = new ServiceConnector(ServiceHelper.GetVotingUrl());

				conn.AddParameter("entryId", entry.id.toString());

				conn.AddParameter("up", "false");

				conn.AddListener(new OnServiceConnectionFinishedEvent()
				{

					@Override
					public void ExceptionOccured()
					{
						runOnUiThread(new Runnable()
						{
							public void run()
							{
								pd.dismiss();

								Toast tConnError = Toast.makeText(appContext, "Sunucu ile baðlantý kurulamadý.", 1000);
								tConnError.show();
							}
						});
					}

					@Override
					public void ConnectionFinished(String result)
					{
						runOnUiThread(new Runnable()
						{
							public void run()
							{
								pd.dismiss();

								Toast tConnSuccess = Toast.makeText(appContext, "Oyunuz baþarý ile eklendi.", 1000);
								tConnSuccess.show();

								RefreshVoteCount();
							}
						});
					}
				});

				conn.ConnectUsingJsonPost();

				pd.setMessage("Oyunuz kaydediliyor...");

				pd.show();
			}
		});

		if (entry.userName.equalsIgnoreCase(UserHelper.CurrentUser.Username))
		{
			btnSolved.setVisibility(View.VISIBLE);
		} else
		{
			btnSolved.setVisibility(View.INVISIBLE);
		}

		btnSolved.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				ServiceConnector conn = new ServiceConnector(ServiceHelper.GetMarFixedUrl());

				conn.AddParameter("entryId", entry.id.toString());

				conn.AddParameter("value", "true");

				conn.AddListener(new OnServiceConnectionFinishedEvent()
				{

					@Override
					public void ExceptionOccured()
					{
						runOnUiThread(new Runnable()
						{
							public void run()
							{
								pd.dismiss();

								Toast tConnError = Toast.makeText(appContext, "Sunucu ile baðlantý kurulamadý.", 1000);
								tConnError.show();
							}
						});
					}

					@Override
					public void ConnectionFinished(String result)
					{
						runOnUiThread(new Runnable()
						{
							public void run()
							{
								pd.dismiss();

								Toast tConnSuccess = Toast.makeText(appContext, "Çözüldü olarak iþaretlendi.", 1000);
								tConnSuccess.show();
								RefreshVoteCount();
							}
						});
					}
				});

				conn.ConnectUsingJsonPost();

				pd.setMessage("Kaydediliyor...");

				pd.show();
			}

		});
		
		btnSave.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				ServiceConnector conn = new ServiceConnector(ServiceHelper.GetEditEtryUrl());

				conn.AddParameter("entryId", entry.id.toString());
				
				String val = txtExtraData.getText().toString();

				conn.AddParameter("value", val);

				conn.AddListener(new OnServiceConnectionFinishedEvent()
				{

					@Override
					public void ExceptionOccured()
					{
						runOnUiThread(new Runnable()
						{
							public void run()
							{
								pd.dismiss();

								Toast tConnError = Toast.makeText(appContext, "Sunucu ile baðlantý kurulamadý.", 1000);
								tConnError.show();
							}
						});
					}

					@Override
					public void ConnectionFinished(String result)
					{
						runOnUiThread(new Runnable()
						{
							public void run()
							{
								pd.dismiss();

								Toast tConnSuccess = Toast.makeText(appContext, "Baþarýyla güncellendi.", 1000);
								tConnSuccess.show();
							}
						});
					}
				});

				conn.ConnectUsingJsonPost();

				pd.setMessage("Kaydediliyor...");

				pd.show();
			}

		});
		
		lblExtraData.setText(entry.category.extra.key);
		
		if (entry.extra != null)
		{
			txtExtraData.setText(entry.extra.value);
		}
		

		RefreshVoteCount();
	}

	private void RefreshVoteCount()
	{
		ServiceConnector conn = new ServiceConnector(ServiceHelper.GetEntiryDetailsUrl(entry.id));

		conn.SetPostRequest(false);

		conn.AddListener(new OnServiceConnectionFinishedEvent()
		{

			@Override
			public void ExceptionOccured()
			{
				runOnUiThread(new Runnable()
				{
					public void run()
					{
						pd.dismiss();

						Toast tConnError = Toast.makeText(appContext, "Oy durumu güncellenirken bir hata oluþtu.", 1000);
						tConnError.show();
					}
				});
			}

			@Override
			public void ConnectionFinished(final String result)
			{

				Type listType = new TypeToken<JSONDataContainer<ListEntry>>()
				{
				}.getType();

				JSONDataContainer<ListEntry> data = null;

				data = (JSONDataContainer<ListEntry>) JSONHelper.FromJson(result, listType);

				if (data.data.size() > 0)
				{
					final ListEntry entryUpdated = data.data.get(0);

					entry = entryUpdated;

					runOnUiThread(new Runnable()
					{
						public void run()
						{
							pd.dismiss();

							lblVote.setText(entryUpdated.upVoteCount + " / " + entryUpdated.downVoteCount);
							
							if (entry.fixed)
							{
								lblCurrentStatus.setText("Çözüldü");
							} 
							else
							{
								lblCurrentStatus.setText("Çözülmedi");
							}

							Toast tConnSuccess = Toast.makeText(appContext, "Oy durumu güncellendi.", 1000);
							tConnSuccess.show();
						}
					});
				}

			}
		});

		conn.Connect();

		pd.setMessage("Sunucudan son oy durumu sorgulanýyor...");

		pd.show();
	}

}
