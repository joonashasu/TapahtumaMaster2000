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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Handler;

public class Event implements Serializable {
    String ID;
    String name;
    String description;
    Venue venue;
    String duration;
    Date openHoursStart;
    Date openHoursEnd;
    String price;
    URL url;

    ArrayList<Event> allEvents = new ArrayList<Event>();
    private static Event single_instance = null;

    public static Event getInstance(){
        {
            if (single_instance == null)
                single_instance = new Event();

            return single_instance;
        }
    }

    public String getJSON() throws IOException {

        //gets json file from myhelsinki open api
        Event[] list = null;
        String preresponse = null;
        String response = null;

        String site = "https://open-api.myhelsinki.fi/v2/activities";
        URL url = new URL(site);

        HttpURLConnection http = (HttpURLConnection)url.openConnection();

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        InputStream in = new BufferedInputStream(http.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine())!= null){
            sb.append(line).append("\n");
        }
        in.close();
        preresponse = sb.toString();
        response = preresponse.substring(31,preresponse.length());




        return response;
    }
    public void addEvents() throws IOException {
        String json = getJSON();

        if (json != null){
            try{
                //searches through the json and appends event objects to arraylist
                JSONArray jsonArray = new JSONArray(json);
                for(int i = 0; i<jsonArray.length();i++){
                    Event event = new Event();
                    Venue venue = new Venue();
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    event.ID = jObj.get("id").toString();
                    event.duration = jObj.get("duration")+jObj.get("durationType").toString();

                    JSONObject price =(JSONObject) jObj.get("priceEUR");
                    event.price = price.get("from")+"-"+price.get("to")+"€".toString();

                    JSONObject help = (JSONObject) jObj.get("descriptions"); //"help" objects used to wrap around type mismatch errors
                    help = help.getJSONObject("en");
                    event.description = help.get("description").toString();
                    event.name = help.get("name").toString();


                    JSONObject venueHelp = (JSONObject) jObj.get("address");
                    venue.location = (venueHelp.get("streetName")+", "+venueHelp.get("postalCode")+" "+venueHelp.get("city")).toString();

                    event.venue = venue;

                    String urlHelp = jObj.get("siteUrl").toString();
                    event.url = new URL(urlHelp);


                    allEvents.add(event);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("PITUUS:"+allEvents.size());

    }







    public String formatDetails(Event e){ //gets details of event to be previewed
        String formatted = (e.name+"\n"
                +"Location: "+e.venue.location+"\n"
                +"Price: "+e.price+"\n"
                +"Description: "+e.description+"\n");

        return formatted;
    }

    public String getPrice(){
        return price;
    }
}
