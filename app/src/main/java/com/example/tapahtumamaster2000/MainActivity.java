package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import com.google.gson.*;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Event e = new Event();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    e.addEvents();
                    System.out.println("Pääsimme sisään");

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
    }


    public void readJSON(View v) {
        Gson gson = new Gson();

    }
}


