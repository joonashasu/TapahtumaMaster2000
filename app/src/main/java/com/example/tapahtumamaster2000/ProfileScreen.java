package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Map;

public class ProfileScreen extends AppCompatActivity {

    private TextView tName;
    private TextView tUsername;
    private TextView tLocation;
    private EditText etPassword1;
    private EditText etPassword2;
    private EditText etLocationChanger;
    private Button bUploadImage;
    private Button bChangePassword;
    private Button bChangeLocation;

    public Credentials credentials;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        tName = (TextView) findViewById(R.id.tName);
        tUsername = (TextView) findViewById(R.id.tUsername);
        tLocation = (TextView) findViewById(R.id.tLocation);
        etPassword1 = (EditText) findViewById(R.id.etPassword1);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        etLocationChanger = (EditText) findViewById(R.id.etLocationChanger);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bChangeLocation = (Button) findViewById(R.id.bChangeLocation);
        String name, username, location;

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();


            name = sharedPreferences.getString("LoginName", "");
            username = sharedPreferences.getString("LoginUsername", "");
            location = sharedPreferences.getString("LoginLocation", "");

            tName.setText(name);
            tUsername.setText(username);
            tLocation.setText(location);
        }

        bChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etPassword1 = (EditText) findViewById(R.id.etPassword1);
                etPassword2 = (EditText) findViewById(R.id.etPassword2);

                String password = sharedPreferences.getString("LoginPassword", "");

                String newPassword1 = etPassword1.getText().toString();
                String newPassword2 = etPassword2.getText().toString();

                if(newPassword1.equals(newPassword2) && !newPassword1.equals(password)){
                    Toast.makeText(ProfileScreen.this, "Passwords changed successfully", Toast.LENGTH_SHORT).show();
                    sharedPreferencesEditor.putString("LoginPassword", newPassword1);
                    sharedPreferencesEditor.apply();
                }else if (newPassword1.equals(newPassword2) && newPassword1.equals(password)) {
                    Toast.makeText(ProfileScreen.this, "Passwords already in use", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileScreen.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etLocationChanger = (EditText) findViewById(R.id.etLocationChanger);

                String newLocation = etLocationChanger.getText().toString();

                Toast.makeText(ProfileScreen.this, "Location changed successfully", Toast.LENGTH_SHORT).show();
                sharedPreferencesEditor.putString("LoginLocation", newLocation);
                sharedPreferencesEditor.apply();
            }
        });
    }
}