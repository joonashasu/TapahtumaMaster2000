package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.*;
import org.json.JSONException;
import org.w3c.dom.Text;

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
    TextView eventDetails;
    Button saveButton;
    ArrayList<Event> mainList = new ArrayList<Event>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Event e = new Event();
        saveButton = (Button) findViewById(R.id.saveButton);

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
            mainList.add(e.allEvents.get(j));
            System.out.println(spList.get(j).toString());
        }
        System.out.println("Pituus"+ spList.size());
        ArrayAdapter<String> aa = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner =(Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(aa);
        System.out.println("Populated");

        eventDetails = (TextView) findViewById(R.id.eventDetails);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id){
                for(int i = 0; i < e.allEvents.size(); i++){
                    if(spinner.getSelectedItem().toString().equals(e.allEvents.get(i).name)){
                        Event selected = e.allEvents.get(i);
                        String formatted = e.formatDetails(selected);
                        eventDetails.setText(formatted);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    public void readJSON(View v) {
        Gson gson = new Gson();

    }

    public void populateSpinner(){


    }

    public void saveButtonClick(View v){
        User u = new User();
        for(int i = 0; i < mainList.size(); i++) {
            if (spinner.getSelectedItem().toString().equals(mainList.get(i).name)) {
                Event selected = mainList.get(i);
                u.saveEvent(selected);
            }
        }
        System.out.println("läpi");
    }

}


