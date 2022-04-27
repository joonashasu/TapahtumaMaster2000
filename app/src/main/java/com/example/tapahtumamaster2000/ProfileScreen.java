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

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

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

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if (sharedPreferences != null) {

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();
        }

        //tName.setText(getString());





    }
}