package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class StartScreen extends AppCompatActivity {

    private EditText etUsername = (EditText) findViewById(R.id.etUsername);
    private EditText etPassword = (EditText) findViewById(R.id.etPassword);
    private Button bLogin = (Button) findViewById(R.id.bLogin);
    private Button bRegister = (Button) findViewById(R.id.bLogin);

    boolean validation = false;
    public Credentials credentials;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // Login to already existing account
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        credentials = new Credentials();

        if(sharedPreferences != null){

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();

            if(sharedPreferencesMap.size() != 0){
                credentials.credentialLoader(sharedPreferencesMap);
            }

            String savedUsername = sharedPreferences.getString("Username",  "");
            String savedPassword = sharedPreferences.getString("Password", "");
        }

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(StartScreen.this, "Please provide both a username and a password", Toast.LENGTH_SHORT).show();
                }else{
                    validation = CheckCredentials(username, password);
                    if(!validation){
                        Toast.makeText(StartScreen.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(StartScreen.this, "Successful login", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(StartScreen.this, HomePage.class);
                        startActivity(intent);
                    }
                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }

    private boolean CheckCredentials(String username, String password){
        return credentials.CheckCredentials(username, password);
    }
}