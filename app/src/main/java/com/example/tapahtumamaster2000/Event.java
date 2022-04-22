package com.example.tapahtumamaster2000;

import android.graphics.pdf.PdfRenderer;
import android.renderscript.ScriptGroup;
import android.util.JsonReader;
import android.util.JsonToken;
import android.webkit.HttpAuthHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;
import com.google.gson.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Handler;

public class Event {
    int ID;
    String name;
    String description;
    Venue venue;
    Date openHoursStart;
    Date openHoursEnd;
    double price;
    URL url;

    ArrayList<Event> allEvents = new ArrayList<Event>();

    public void addEvents() throws IOException {
        Event[] list = null;
        Gson gson = new Gson();
        String site = "https://open-api.myhelsinki.fi/v2/activities";
        URL url = new URL(site);
        URLConnection request = url.openConnection();
        request.connect();
        Event event = new Event();
        JsonParser jp = new JsonParser();
        InputStreamReader reader = new InputStreamReader(request.getInputStream());

        JsonElement root  = jp.parse(reader);
        JsonObject jOBJ = root.getAsJsonObject();
        System.out.println(jOBJ.toString());
        //event.ID = jOBJ.get("id").getAsInt();

        //

        //event.ID = rootobj.get("id").getAsInt();
        //event.name =
        //event.




        System.out.println(event.ID);


    }



    //public Date getDuration(Event e){

        //return
    //}

    public double getPrice(){
        return price;
    }
}
