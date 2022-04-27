package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class EventScreen extends AppCompatActivity {
    ScrollView descScrollView;
    TextView tv;
    TextView eventName;
    TextView eventLocation;
    TextView eventPrice;
    private DrawerLayout mDrawer;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView nvDrawer;




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

        descScrollView = (ScrollView) findViewById(R.id.descScrollView);
        eventName = (TextView) findViewById(R.id.eventName);
        eventLocation = (TextView) findViewById(R.id.eventLocation);
        eventPrice = (TextView) findViewById(R.id.eventPrice);
        descScrollView.removeAllViews();
        Intent i = getIntent();
        Event event = (Event) i.getSerializableExtra("sample");
        tv = new TextView(this);
        tv.setText(event.description.toString());
        descScrollView.addView(tv);
        System.out.println(event.description);
        String nameString = "Name: "+event.name;
        eventName.setText(nameString);
        String locationString = "Location: "+event.venue.location;
        eventLocation.setText(locationString);
        String priceString = "Price: "+event.price;
        eventPrice.setText(priceString);



    }

    public void goBack(View v){
        Intent intent1 = new Intent(EventScreen.this, BrowseScreen.class);
        startActivity(intent1);
    }

    public void saveEvent(View v){
        Intent i = getIntent();
        Event event = (Event) i.getSerializableExtra("sample");
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