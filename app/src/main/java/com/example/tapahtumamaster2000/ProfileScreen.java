package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Map;

public class ProfileScreen extends AppCompatActivity {

    private TextView tName;
    private TextView tUsername;
    private TextView tLocation;
    private Button bUploadImage;
    private Button bChangePassword;
    private Button bChangeLocation;

    public Credentials credentials;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        tName = (TextView) findViewById(R.id.tName);
        tUsername = (TextView) findViewById(R.id.tUsername);
        tLocation = (TextView) findViewById(R.id.tLocation);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bChangeLocation = (Button) findViewById(R.id.bChangeLocation);
        String name, username, password, location;

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);

        if(sharedPreferences != null){

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();


            name = sharedPreferences.getString("LoginName", "");
            username = sharedPreferences.getString("LoginUsername", "");
            password = sharedPreferences.getString("LoginPassword", "");
            location = sharedPreferences.getString("LoginLocation", "");

            tName.setText(name);
            tUsername.setText(username);
            tLocation.setText(location);
        }
    }
}