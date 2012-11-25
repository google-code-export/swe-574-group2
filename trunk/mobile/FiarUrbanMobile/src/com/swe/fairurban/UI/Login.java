package com.swe.fairurban.UI;

import com.swe.fairurban.R;
import com.swe.fairurban.Helpers.UserHelper;

import android.os.Bundle;
import android.app.Activity;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appContext = this;
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);

		btnLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				// TODO Auto-generated method stub
				String username = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();
				
				if (username.equalsIgnoreCase(UserHelper.Username) && password.equalsIgnoreCase(UserHelper.Password)) {
					//Login successful
					Intent mapIntent = new Intent(view.getContext(), Map.class);
	                startActivity(mapIntent);
	              
				}
				else {
					Toast tLoginError = Toast.makeText(appContext, "Kullanýcý Adý / Þifre Hatalý",1000);
					tLoginError.show();
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}
