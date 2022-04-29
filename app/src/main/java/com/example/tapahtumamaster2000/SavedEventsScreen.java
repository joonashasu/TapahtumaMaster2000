package com.example.tapahtumamaster2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class SavedEventsScreen extends AppCompatActivity {
    ListView savedEventsList;
    TextView yourEvents;
    TextView commenttv;
    Button backbutton;
    SharedPreferences sharedPreferences;
    public Credentials credentials;
    TextView moneyUsed;
    ArrayList<String> comments = new ArrayList<String>();
    int totalcost;

    String selected = "";

    public static SavedEventsScreen sesInstance = null;

    //https://stackoverflow.com/questions/3496269/how-do-i-put-a-border-around-an-android-textview borders for tv:s



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);


        savedEventsList = (ListView) findViewById(R.id.savedeventslist);
        yourEvents = (TextView) findViewById(R.id.textView2);
        commenttv = (TextView) findViewById(R.id.commenttv);
        backbutton = (Button) findViewById(R.id.backToUserSceen);
        moneyUsed = (TextView) findViewById(R.id.moneyUsed);
        StartScreen ss = new StartScreen();
        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        String currentUserName = "";

        if (sharedPreferences != null) {

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();
            //credentials.credentialLoader(sharedPreferencesMap);
             currentUserName = sharedPreferences.getString("lastLoginUsername", "");
        }

        showListedEvents(currentUserName);
        System.out.println(totalcost);
        String money = "Your saved events cost a total of "+totalcost+"€.";
        moneyUsed.setText(money);


        savedEventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedString = savedEventsList.getItemAtPosition(i).toString();
                for(int k = 0; k <savedEventsList.getCount() ; k++){
                    commenttv.setText(comments.get(i));
                    selected = selectedString;
                }

            }
        });
    }
    public String getcurrent(){
        String currentUserName = "";

        if (sharedPreferences != null) {

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();
            currentUserName = sharedPreferences.getString("LoginUsername", "");
        }
        return currentUserName;
    }

    public String getSelected(){
        return selected;
    }


    public void backToUserScreen(View v){
        Intent intent1 = new Intent(SavedEventsScreen.this, ProfileScreen.class);
        startActivity(intent1);
    }
    public static SavedEventsScreen getInstance(){
        if (sesInstance == null) {
            sesInstance = new SavedEventsScreen();
        }
        return sesInstance;
    }

    public void showListedEvents(String name){
        ArrayList<String> list = new ArrayList<String>();
        Context context = this;
        String read = name+".txt";

        try {
            InputStream ins = context.openFileInput(read);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            System.out.println("try sisäl");
            System.out.println(name);
            String s = "";
            totalcost = 0;

            while ((s=br.readLine())!=null){
                String[] help = s.split(";");
                s = help[1];
                comments.add(help[2]);
                String helpString = help[3];
                int addable = Integer.parseInt(helpString);
                totalcost = totalcost + addable;
                System.out.println(s);
                list.add(s);


            }
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
            savedEventsList.setAdapter(aa);
            ins.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteEvent(View v){
        String selectedString = getSelected();

        if (selectedString == ""){
            Toast.makeText(this, "No event selected", Toast.LENGTH_SHORT).show();
        } else {
            User u = new User();
            String username = getcurrent();
            Context context = getContext();
            u.deleteEvent(username, selectedString, context);
            showListedEvents(username);
        }
    }


    public Context getContext(){
        Context context = getApplicationContext();
        return context;
    }

    public void goToCalendar(View v){
        Intent intent1 = new Intent(SavedEventsScreen.this, CalendarScreen.class);
        startActivity(intent1);
    }
}