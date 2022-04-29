package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CalendarScreen extends AppCompatActivity {
    TextView tvEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_screen);


        tvEvent = (TextView) findViewById(R.id.tvEvent);




    }
}