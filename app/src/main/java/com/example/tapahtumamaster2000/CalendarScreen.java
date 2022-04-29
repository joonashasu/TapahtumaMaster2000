package com.example.tapahtumamaster2000;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarScreen extends AppCompatActivity {
    TextView tvEvent;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_screen);


        tvEvent = (TextView) findViewById(R.id.tvEvent);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = new Calendar();
        SavedEventsScreen ses = SavedEventsScreen.getInstance();
        String un = ses.getcurrent();
        Context context = this;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.mm.yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        calendar.displayCalendar(context,un,date);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

            }
        });

    }
}