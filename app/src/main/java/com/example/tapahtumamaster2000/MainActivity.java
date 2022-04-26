package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.*;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {
    View v;
    Spinner spinner;
    Context context = this;
    CountDownLatch latch = new CountDownLatch(1);
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
                    latch.countDown();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        List<String> spList = new ArrayList<String>();
        for (int j = 0; j < e.allEvents.size(); j++){
            String name = e.allEvents.get(j).name;
            spList.add(name);
            System.out.println(spList.get(j).toString());
        }
        System.out.println("Pituus"+ spList.size());
        ArrayAdapter<String> aa = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner =(Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(aa);
        System.out.println("Populated");





    }


    public void readJSON(View v) {
        Gson gson = new Gson();

    }

    public void populateSpinner(){


    }
}


