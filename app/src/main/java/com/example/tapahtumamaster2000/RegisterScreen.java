package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.progressindicator.BaseProgressIndicator;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                credentials.locationLoader(sharedPreferencesMap);
                credentials.nameLoader(sharedPreferencesMap);
            }
        }

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputName = etName.getText().toString();
                String inputUsername = etUsername.getText().toString();
                String inputPassword = etPassword.getText().toString();
                String inputLocation = etLocation.getText().toString();

                if(CheckCredentials(inputUsername,inputPassword)){

                    if(credentials.CheckUsername(inputUsername)){
                        Toast.makeText(RegisterScreen.this, "Username already in use, please chose another one.", Toast.LENGTH_SHORT).show();
                    }else{

                    // Adding inputs to hashmaps
                    credentials.addUser(inputUsername, inputPassword);
                    credentials.addLocation(inputUsername, inputLocation);
                    credentials.addName(inputUsername, inputName);

                    // Adding name, password and location to database
                    sharedPreferencesEditor.putString(inputUsername, inputPassword);
                    sharedPreferencesEditor.putString(inputUsername, inputLocation);
                    sharedPreferencesEditor.putString(inputUsername, inputName);

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

    private boolean CheckCredentials(String un, String pw) {

        boolean lowerCase = false;
        boolean UpperCase = false;
        boolean digit = false;
        boolean specialCharacter = false;

        // Special character specifier
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher m = p.matcher(pw);

        // Goes through password characters by character and checks if it meets the
        // requirements
        for (char i : pw.toCharArray()) {
            if (Character.isLowerCase(i))
                lowerCase = true;
            if (Character.isUpperCase(i))
                UpperCase = true;
            if (Character.isDigit(i))
                digit = true;
            if (!m.matches())
                specialCharacter = true;
        }

        if (lowerCase && UpperCase && digit && specialCharacter && pw.length() > 12) {
            return true;
        } else {
            Toast.makeText(RegisterScreen.this, "Enter username and a password that matches the following requirement:\n " +
                    "Password: Lowercase, upper, digit, special character", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}