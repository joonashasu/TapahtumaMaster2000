package com.example.tapahtumamaster2000;

import java.io.Serializable;
import java.util.Date;

public class Venue implements Serializable {
    int ID;
    String name;
    String location;
    String locationLat;
    String locationLong;
    Date openHoursStart;
    Date openHoursEnd;

    public Venue getVenueData(Venue ven){
        return ven;
    }
}
