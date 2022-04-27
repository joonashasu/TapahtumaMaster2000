package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;


import java.io.Serializable;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BrowseScreen extends AppCompatActivity {
    View v;
    Spinner spinner;
    Context context = this;
    CountDownLatch latch = new CountDownLatch(1);
    TextView eventDetails;
    Button saveButton;
    ImageButton searchButton;
    TextInputEditText searchBar;
    ArrayList<Event> mainList = new ArrayList<Event>();

    private DrawerLayout mDrawer;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_screen);
        Event e = new Event();
        saveButton = (Button) findViewById(R.id.checkEventButton);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);



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

    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchButtonClick(View v){
        String criteria = searchBar.getText().toString();
        List<String> spList = new ArrayList<String>();
        Event e;
        if (criteria == null){

        }else{
            for(int j = 0; j < mainList.size(); j++){

                if(mainList.get(j).name.contains(criteria)){
                    String name = mainList.get(j).name;
                    spList.add(name);
                    System.out.println(spList.get(j).toString());
                }
            }
        }

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

    public Event getSelected(){
        Event event = null;
        for(int i = 0; i < mainList.size(); i++) {
            if (spinner.getSelectedItem().toString().equals(mainList.get(i).name)) {
                Event selected = mainList.get(i);

                return selected;
            }
            else{
                return event;
            }
        }

        return event;
    }

    public void goToEvent(View v){
        Intent intent1 = new Intent(BrowseScreen.this, EventScreen.class);
        Event selected;
        for(int i = 0; i < mainList.size(); i++) {
            if (spinner.getSelectedItem().toString().equals(mainList.get(i).name)) {
                selected = mainList.get(i);
                intent1.putExtra("sample", selected);
                startActivity(intent1);
            }
        }

    }

}


