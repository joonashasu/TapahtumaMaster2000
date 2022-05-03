package com.example.tapahtumamaster2000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarScreen extends AppCompatActivity {
    TextView tvEvent;
    CalendarView calendarView;
    SharedPreferences credentialsSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_screen);


        tvEvent = (TextView) findViewById(R.id.tvEvent);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = new Calendar();
        SavedEventsScreen ses = SavedEventsScreen.getInstance();
        String un;

        credentialsSharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        credentialsSharedPreferences.getAll();
        un = credentialsSharedPreferences.getString("lastLoginUsername", "");
        System.out.println(un);
        Context context = this;

        //sets date format & current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);


        //displays current date's events on startup
        String displayed = calendar.displayCalendar(context,un,date);
        tvEvent.setText(displayed);


        //displays selected date's events when clicked on
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String day = "";
                String month = "";
                if(i2 < 10){
                    day = String.valueOf(i2);
                    day = ("0" + i2);
                }else {
                    day = String.valueOf(i2);
                }
                if(i1+1 < 10) {
                    month = String.valueOf(i1);
                    month = ("0" + (i1+1));
                }else {
                    month = String.valueOf(i1 + 1);
                }
                String year = String.valueOf(i);
                String date = day + "." + month + "." + year;
                System.out.println(date);

                String displayed = calendar.displayCalendar(context,un,date);
                tvEvent.setText(displayed);


            }
        });

    }

    public void backToProfile(View v){
        Intent intent1 = new Intent(this, ProfileScreen.class);
        startActivity(intent1);
    }
}