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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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
    Context context = this;
    CountDownLatch latch = new CountDownLatch(1);
    TextView eventDetails;
    Button saveButton;
    ImageButton searchButton;
    TextInputEditText searchBar;
    ListView eventList;
    SearchView SV;
    ArrayList<Event> mainList = new ArrayList<Event>();

    private DrawerLayout mDrawer;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView nvDrawer;

    public String clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_screen);
        Event e = new Event();
        saveButton = (Button) findViewById(R.id.checkEventButton);
        eventList = (ListView) findViewById(R.id.eventList);
        SV = (SearchView) findViewById(R.id.searchView);


        //Toolbar functions under here:
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);


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
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, spList);
        eventList.setAdapter(aa);
        System.out.println("Populated");

        eventDetails = (TextView) findViewById(R.id.eventDetails);

        SV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String searchable = SV.getQuery().toString();
                System.out.println(searchable);
                spList.clear();
                    for (int i = 0; i < e.allEvents.size(); i++) {
                        if (e.allEvents.get(i).name.contains(searchable)) {
                            spList.add(e.allEvents.get(i).name);
                        }
                        else if (e.allEvents.get(i).venue.location.contains(searchable)) {
                            spList.add(e.allEvents.get(i).name);
                        }
                        else if (e.allEvents.get(i).description.contains(searchable)) {
                            spList.add(e.allEvents.get(i).name);
                        }
                    }
                    ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, spList);
                    eventList.setAdapter(aa);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });



        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedString = eventList.getItemAtPosition(i).toString();
                for(int k = 0; k < e.allEvents.size(); k++){
                    if(selectedString.equals(e.allEvents.get(k).name)){
                        Event selected = e.allEvents.get(k);
                        String formatted = e.formatDetails(selected);
                        eventDetails.setText(formatted);
                    }
                }

            }
        });





    }
    public Context getActivity(){
        return BrowseScreen.this;
    }


    public void readJSON(View v) {
        Gson gson = new Gson();

    }

    public void populateSpinner(){


    }
    //Toolbar functions under here, from: https://guides.codepath.com/android/fragment-navigation-drawer
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent;
        switch(menuItem.getItemId()) {
            case R.id.nav_browsing_screen:
                intent = new Intent(BrowseScreen.this, BrowseScreen.class);
                break;
            case R.id.nav_user_profile:
                intent = new Intent(BrowseScreen.this, ProfileScreen.class);
                break;
            case R.id.nav_log_out:
                intent = new Intent(BrowseScreen.this, StartScreen.class);
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
            default:
                intent = new Intent(BrowseScreen.this, BrowseScreen.class);
        }


        startActivity(intent);
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
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
            if (eventList.getSelectedItem().toString().equals(mainList.get(i).name)) {
                Event selected = mainList.get(i);
                u.saveEvent(selected);
            }
        }
        System.out.println("läpi");
    }

    public Event getChosenEvent(){
        Event event = null;
        String helpString = eventDetails.getText().toString();
        String[] hsar = helpString.split("\n");
        helpString = hsar[0];

        for(int i = 0; i < mainList.size(); i++) {
            if (helpString.equals(mainList.get(i).name)) {
                Event selected = mainList.get(i);
                System.out.println(selected.name);

                return selected;
            }

            }


        return event;
    }

    public void goToEvent(View v){
        Intent intent1 = new Intent(BrowseScreen.this, EventScreen.class);
        Event selected = getChosenEvent();
        intent1.putExtra("sample", selected);
        startActivity(intent1);


    }

}


