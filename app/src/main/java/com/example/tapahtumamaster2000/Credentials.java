package com.example.tapahtumamaster2000;

import java.util.HashMap;
import java.util.Map;

public class Credentials{

    private HashMap<String, String> credentialsHashmap = new HashMap<String, String>();
    private HashMap<String, String> locationsHashmap = new HashMap<String, String>();
    private HashMap<String, String> nameHashmap = new HashMap<String, String>();


    public void addUser(String username, String password){
        credentialsHashmap.put(username, password);
    }

    public void addLocation(String username, String location){
        locationsHashmap.put(username, location);
    }

    public void addName(String username, String name){
        locationsHashmap.put(username, name);
    }

    public boolean CheckUsername(String username){
        return credentialsHashmap.containsKey(username);
    }

    // Takes in username and password, compares them and returns true/false
    // depending on the if the credentials match
    public boolean CheckCredentials(String username, String password){

        if(credentialsHashmap.containsKey(username)){
            if(password.equals(credentialsHashmap.get(username))){
                return true;
            }
        }
        return false;
    }

    // Loads credentials from credentials hashmap with (username, password) as (key, value)
    public void credentialLoader (Map<String, ?> preferencesMap){
        for(Map.Entry<String, ?> entries: preferencesMap.entrySet()){
            credentialsHashmap.put(entries.getKey(), entries.getValue().toString());
        }
    }

    public String locationLoader(String username) {
            return locationsHashmap.get(username+"loc");
    }

    public String nameLoader(String username) {
        return nameHashmap.get(username+"realName");
    }
}
