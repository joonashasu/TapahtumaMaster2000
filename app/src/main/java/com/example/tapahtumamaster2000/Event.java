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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Handler;

public class Event {
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

    public String getJSON() throws IOException {
        Event[] list = null;
        String preresponse = null;
        String response = null;
        //Gson gson = new Gson();
        String site = "https://open-api.myhelsinki.fi/v2/activities";
        URL url = new URL(site);
        //URLConnection request = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        //http.setRequestMethod("GET");
        //http.setRequestProperty("-H ","accept: application/json");
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
        System.out.println(response);

        //JsonElement root  = jp.parse(reader);
        //JsonObject jOBJ = root.getAsJsonObject();
        //System.out.println(jOBJ.toString());
        //event.ID = jOBJ.get("id").getAsInt();

        //

        //event.ID = rootobj.get("id").getAsInt();
        //event.name =
        //event.




        //System.out.println(event.ID);

        return response;
    }
    public void addEvents() throws IOException {
        String json = getJSON();

        if (json != null){
            try{
                JSONArray jsonArray = new JSONArray(json);
                for(int i = 0; i<jsonArray.length();i++){
                    Event event = new Event();
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    event.ID = jObj.get("id").toString();
                    JSONObject price =(JSONObject) jObj.get("priceEUR");
                    event.price = price.get("from")+"-"+price.get("to")+"€".toString();
                    event.duration = jObj.get("duration")+jObj.get("durationType").toString();
                    JSONObject help = (JSONObject) jObj.get("descriptions"); //"help" object used to wrap around type mismatch errors
                    help = help.getJSONObject("en");
                    event.description = help.get("description").toString();
                    event.name = help.get("name").toString();
                    //event.name = jObj.get("descriptions").toString();
                    System.out.println("//////////////////////////////");
                    allEvents.add(event);
                    System.out.println(event.ID+" "+event.name+" "+event.duration+" "+event.price);
                    System.out.println(event.description);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }






    //public Date getDuration(Event e){

        //return
    //}

    public String getPrice(){
        return price;
    }
}
