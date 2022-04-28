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
    ArrayList<String> comments = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);


        savedEventsList = (ListView) findViewById(R.id.savedeventslist);
        yourEvents = (TextView) findViewById(R.id.textView2);
        commenttv = (TextView) findViewById(R.id.commenttv);
        backbutton = (Button) findViewById(R.id.backToUserSceen);
        StartScreen ss = new StartScreen();
        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        String currentUserName = "";

        if (sharedPreferences != null) {

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();
            //credentials.credentialLoader(sharedPreferencesMap);
             currentUserName = sharedPreferences.getString("LoginUsername", "");
        }

        showListedEvents(currentUserName);

        savedEventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedString = savedEventsList.getItemAtPosition(i).toString();
                for(int k = 0; k <savedEventsList.getCount() ; k++){
                    commenttv.setText(comments.get(i));
                }

            }
        });
    }


    public void backToUserScreen(View v){
        Intent intent1 = new Intent(SavedEventsScreen.this, ProfileScreen.class);
        startActivity(intent1);
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

            while ((s=br.readLine())!=null){
                String[] help = s.split(";");
                s = help[1];
                comments.add(help[2]);
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
}