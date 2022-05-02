package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreen extends AppCompatActivity {

    EditText etName;
    EditText etUsername;
    EditText etPassword;
    EditText etLocation;
    Button bRegister;
    ImageButton ibBack;

    public Credentials credentials;

    SharedPreferences credentialsSharedPreferences;
    SharedPreferences locationSharedPreferences;
    SharedPreferences NameSharedPreferences;
    SharedPreferences.Editor credentialsSharedPreferencesEditor;
    SharedPreferences.Editor locationSharedPreferencesEditor;
    SharedPreferences.Editor NameSharedPreferencesEditor;


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
        ibBack = (ImageButton) findViewById((R.id.ibBack));

        // Creating SharedPreferences and their editors
        credentialsSharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        locationSharedPreferences = getApplicationContext().getSharedPreferences("LocationDataBase", MODE_PRIVATE);
        NameSharedPreferences = getApplicationContext().getSharedPreferences("NameDataBase", MODE_PRIVATE);
        credentialsSharedPreferencesEditor = credentialsSharedPreferences.edit();
        locationSharedPreferencesEditor = locationSharedPreferences.edit();
        NameSharedPreferencesEditor = NameSharedPreferences.edit();

        //  Checking that credentialsSharedPreferences is not empty
        if(credentialsSharedPreferences != null){

            Map<String, ?> sharedPreferencesMap = credentialsSharedPreferences.getAll();

            if(sharedPreferencesMap.size() != 0){
                credentials.credentialLoader(sharedPreferencesMap);
            }
        }

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputName = etName.getText().toString();
                String inputUsername = etUsername.getText().toString();
                String inputPassword = etPassword.getText().toString();
                String inputLocation = etLocation.getText().toString();

                // Checking that username is not already in use
                if(credentials.CheckUsername(inputUsername)){
                    Toast.makeText(RegisterScreen.this, "Username already in use, please chose another one.", Toast.LENGTH_SHORT).show();
                }else{
                    // Checking the password meets the requirements and add new information to account
                    if(checkPassword(inputPassword)) {

                        // Adding inputs to hashmaps
                        credentials.addUser(inputUsername, inputPassword);
                        credentials.addLocation(inputUsername, inputLocation);
                        credentials.addName(inputUsername, inputName);

                        // Adding name, password and location to database
                        credentialsSharedPreferencesEditor.putString(inputUsername, inputPassword);
                        locationSharedPreferencesEditor.putString(inputUsername, inputLocation);
                        NameSharedPreferencesEditor.putString(inputUsername, inputName);


                        // Adding the new change to database
                        credentialsSharedPreferencesEditor.apply();
                        locationSharedPreferencesEditor.apply();
                        NameSharedPreferencesEditor.apply();


                        Intent intent = new Intent(RegisterScreen.this, StartScreen.class);
                        Toast.makeText(RegisterScreen.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterScreen.this, StartScreen.class);
                startActivity(intent);
            }
        });
    }

    // Takes in password and checks that it meets the requirements and returns true/false
    public boolean checkPassword(String password) {

        boolean lowerCase = false;
        boolean UpperCase = false;
        boolean digit = false;
        boolean specialCharacter = false;

        // Special character specifier
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher m = p.matcher(password);

        // Goes through password characters by character and checks if it meets the
        // requirements
        for (char i : password.toCharArray()) {
            if (Character.isLowerCase(i))
                lowerCase = true;
            if (Character.isUpperCase(i))
                UpperCase = true;
            if (Character.isDigit(i))
                digit = true;
            if (m.find())
                specialCharacter = true;
        }

        if (lowerCase && UpperCase && digit && specialCharacter && password.length() > 12) {
            return true;
        } else {
            Toast.makeText(RegisterScreen.this, "Enter username that is not in use and a password that matches the following requirement:\n " +
                    "Password: Lowercase, upper, digit, special character", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}