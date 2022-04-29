package com.example.tapahtumamaster2000;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class EventScreen extends AppCompatActivity {
    ScrollView descScrollView;
    TextView tv;
    TextView eventName;
    TextView eventLocation;
    TextView eventPrice;
    TextView textViewDate;
    TextInputEditText comment;
    private DrawerLayout mDrawer;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView nvDrawer;
    Context context = null;
    SharedPreferences sharedPreferences;
    public Credentials credentials;
    Date current = new Date();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        context = EventScreen.this;
        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);


        comment = (TextInputEditText) findViewById(R.id.comment);
        descScrollView = (ScrollView) findViewById(R.id.descScrollView);
        eventName = (TextView) findViewById(R.id.eventName);
        eventLocation = (TextView) findViewById(R.id.eventLocation);
        eventPrice = (TextView) findViewById(R.id.eventPrice);
        descScrollView.removeAllViews();
        textViewDate = (TextView) findViewById(R.id.editTextDate);
        Intent i = getIntent();
        Event event = (Event) i.getSerializableExtra("sample");
        tv = new TextView(this);
        tv.setText(event.description.toString());
        descScrollView.addView(tv);
        //System.out.println(event.description);
        String nameString = "Name: "+event.name;
        eventName.setText(nameString);
        String locationString = "Location: "+event.venue.location;
        eventLocation.setText(locationString);
        String priceString = "Price: "+event.price;
        eventPrice.setText(priceString);



        comment.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    saveEvent(v);
                    return true;
                }
                return false;
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.home_navigation_drawer_open, R.string.home_navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void goBack(View v){
        Intent intent1 = new Intent(EventScreen.this, BrowseScreen.class);
        startActivity(intent1);
    }

    public void saveEvent(View v){
        Intent i = getIntent();
        Event event = (Event) i.getSerializableExtra("sample");
        User u = new User();
        String cm = comment.getText().toString();
        String currentUserName = "jorma";
        if (sharedPreferences != null) {

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();
            //credentials.credentialLoader(sharedPreferencesMap);
            currentUserName = sharedPreferences.getString("LoginUsername", "");
        }
        try {
            String user = currentUserName;
            String fileName = user+".txt";
            System.out.println("/////////////"+user);
            OutputStreamWriter ow = new OutputStreamWriter(context.openFileOutput(fileName, MODE_APPEND));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.mm.yyyy");
            String date = textViewDate.getText().toString();
            if(date.equals("")){
                LocalDateTime now = LocalDateTime.now();
                date = dtf.format(now);
            }

            if (cm.equals("")){
                cm = "No comment added";
            }
            String price;
            String[] priceHelp = event.price.split("-");
            price = priceHelp[1].replace("€", "");
            if(price.equals("null")){
                price = "0";
            }

            String s = event.ID + ";" + event.name + ";" + cm+";"+price+";"+date+"\n";
            System.out.println(s);
            ow.append(s);
            ow.close();
            Toast.makeText(this, "Event added successfully!", Toast.LENGTH_SHORT).show();
        } catch(IOException e){
            Log.e("IOException","Error in input");
        }


    }


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
                intent = new Intent(this, BrowseScreen.class);
                break;
            case R.id.nav_user_profile:
                intent = new Intent(this, ProfileScreen.class);
                break;
            case R.id.nav_log_out:
                intent = new Intent(this, StartScreen.class);
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
            default:
                intent = new Intent(this, EventScreen.class);
        }


        startActivity(intent);
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
}