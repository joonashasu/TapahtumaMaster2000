package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class EventScreen extends AppCompatActivity {
    ScrollView descScrollView;
    TextView tv;
    TextView eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);

        descScrollView = (ScrollView) findViewById(R.id.descScrollView);
        eventName = (TextView) findViewById(R.id.eventName);
        descScrollView.removeAllViews();
        Intent i = getIntent();
        Event event = (Event) i.getSerializableExtra("sample");
        tv = new TextView(this);
        tv.setText(event.description.toString());
        descScrollView.addView(tv);
        System.out.println(event.description);
        String nameString = "Name: "+event.name;
        eventName.setText(nameString);


    }
}