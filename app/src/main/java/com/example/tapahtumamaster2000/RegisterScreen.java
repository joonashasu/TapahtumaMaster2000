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

public class RegisterScreen extends AppCompatActivity {

    EditText etName;
    EditText etUsername;
    EditText etPassword;
    EditText etLocation;
    Button bRegister;

    public Credentials credentials;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        credentials = new Credentials();
        etName = (EditText) findViewById(R.id.etPersonName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etLocation = (EditText) findViewById(R.id.etLocation);
        bRegister = (Button) findViewById(R.id.bRegister);

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();

            if(sharedPreferencesMap.size() != 0){
                credentials.credentialLoader(sharedPreferencesMap);
            }
        }

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputUsername = etUsername.getText().toString();
                String inputPassword = etPassword.getText().toString();

                if(CheckCredentials(inputUsername,inputPassword)){

                    if(credentials.CheckUsername(inputUsername)){
                        Toast.makeText(RegisterScreen.this, "Username already in use, please chose another one.", Toast.LENGTH_SHORT).show();
                    }else{

                    credentials.addUser(inputUsername, inputPassword );

                    sharedPreferencesEditor.putString(inputUsername, inputPassword);

                    // Adding the new change to database
                    sharedPreferencesEditor.apply();

                    Intent intent = new Intent(RegisterScreen.this, StartScreen.class);
                    Toast.makeText(RegisterScreen.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    }
                }

            }
        });
    }

    private boolean CheckCredentials(String un, String pw){

        if(un.isEmpty() || pw.length() < 12){
            Toast.makeText(RegisterScreen.this, "Enter username and a password longer than 12 characters", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

}