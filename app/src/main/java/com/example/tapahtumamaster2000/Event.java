package com.example.tapahtumamaster2000;

import java.net.URL;
import java.util.Date;

public class Event {
    int ID;
    String name;
    String description;
    Venue venue;
    Date openHoursStart;
    Date openHoursEnd;
    double price;
    URL url;



    public Date getDuration(Event e){

        //return
    }

    public double getPrice(){
        return price;
    }
}
