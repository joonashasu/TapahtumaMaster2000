package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EventScreen extends AppCompatActivity {
    ScrollView descScrollView;
    TextView tv;
    TextView eventName;
    TextView eventLocation;
    TextView eventPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);

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
}