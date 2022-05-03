package com.example.tapahtumamaster2000;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Calendar {
    String username;
    Event event = new Event();
    Date datetime;




    public String displayCalendar(Context context, String un, String date){
        //returns events that are on said day

        String eventsfordate = "";
        try {
            //help gotten from https://stackoverflow.com/questions/5800603/delete-specific-line-from-java-text-file
            System.out.println(context.getFilesDir()+"/"+un+".txt");
            File inputFile = new File(context.getFilesDir()+"/"+un+".txt");
            System.out.println("Date:"+date);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String s = "";
            while ((s=reader.readLine())!=null) {
                if (s.contains(date)) {
                    String[] help = s.split(";");
                    s = help[1];
                    String helpString = help[3];
                    eventsfordate = (eventsfordate + s + " - " + helpString + "€\n");
                }
            }


            reader.close();


        } catch (FileNotFoundException e){
            Log.e("FileNotFoundException","eilöyvy");
        }
        catch(IOException e){
            Log.e("IOException","Error in input");
        }
        return eventsfordate;
    }

}
