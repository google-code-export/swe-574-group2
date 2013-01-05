package com.swe.fairurban.UI;

import org.json.JSONObject;

import com.swe.fairurban.R;
import com.swe.fairurban.Data.User;
import com.swe.fairurban.Helpers.OnServiceConnectionFinishedEvent;
import com.swe.fairurban.Helpers.ServiceConnector;
import com.swe.fairurban.Helpers.ServiceHelper;
import com.swe.fairurban.Helpers.UserHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	EditText txtUsername;
	EditText txtPassword;
	Button btnLogin;
	Context appContext;
	ProgressDialog pd;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appContext = this;
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);

		txtUsername.setText(UserHelper.Username);
		txtPassword.setText(UserHelper.Password);
		
		
		pd = new ProgressDialog(Login.this);
		
		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				
				runOnUiThread(new Runnable() {
					
					public void run() {
				
						pd.setMessage("Sunucuyla iletiþime geçiliyor.");
						pd.setCancelable(false);
						pd.show();
					}
				});
				
				
				
				final String username = txtUsername.getText().toString();
				final String password = txtPassword.getText().toString();
				
				ServiceConnector sc = new ServiceConnector(ServiceHelper.GetLoginUrl());
				
				sc.AddParameter("username", username);
				sc.AddParameter("password", password);
				
				sc.AddListener(new OnServiceConnectionFinishedEvent() {
					
					@Override
					public void ExceptionOccured() {
					
						runOnUiThread(new Runnable() {
							public void run() {
								pd.dismiss();
								Toast tConnectionException = Toast.makeText(appContext, "Sunucuyla baðlantý kurulamadý.",1000);
								tConnectionException.show();
							}
						});
					}
					
					@Override
					public void ConnectionFinished(final String result) {
						
						
							
							runOnUiThread(new Runnable() {
								public void run() {
									try {
										pd.setMessage("Sunucudan gelen cevap iþleniyor.");
										
										JSONObject jObj = new JSONObject(result);
										
										if (jObj.getBoolean("loggedIn")) {
											//Login successful
											
											User loggedInUser = new User();
											
											loggedInUser.Username = username;
											loggedInUser.Password = password;
											
											UserHelper.CurrentUser = loggedInUser;
											
											Intent mapIntent = new Intent(Login.this, Map.class);
							                startActivity(mapIntent);
										}
										else {
											Toast tLoginError = Toast.makeText(appContext, "Kullanýcý Adý / Þifre Hatalý",1000);
											tLoginError.show();
										}
										
										pd.dismiss();
									} catch (Exception e) {
										Toast tLoginError = Toast.makeText(appContext, "Beklenmeyen bir hata oluþtu.",1000);
										tLoginError.show();
										
										pd.dismiss();
									}
								}
							});
							
							
						
						
					}
				});
				
				sc.Connect();
				
				pd.setMessage("Baðlantý kuruluyor.");
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}
