package com.example.tapahtumamaster2000;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Credentials{

    private HashMap<String, String> credentialsHashmap = new HashMap<String, String>();
    private HashMap<String, String> locationsHashmap = new HashMap<String, String>();

    public void addUser(String username, String password){
        credentialsHashmap.put(username, password);
    }

    public void addLocation(String username, String location){
        locationsHashmap.put(username, location);
    }

    public boolean CheckUsername(String username){
        return credentialsHashmap.containsKey(username);
    }

    public boolean CheckCredentials(String username, String password){

        if(credentialsHashmap.containsKey(username)){
            if(password.equals(credentialsHashmap.get(username))){
                return true;
            }
        }
        return false;
    }
    public void credentialLoader (Map<String, ?> preferencesMap){
        for(Map.Entry<String, ?> entries: preferencesMap.entrySet()){
            credentialsHashmap.put(entries.getKey(), entries.getValue().toString());
        }
    }

    public void locationLoader(Map<String, ?> locationMap ) {
        for(Map.Entry<String, ?> entries: locationMap.entrySet()){
            locationsHashmap.put(entries.getKey(), entries.getValue().toString());
        }
    }
}
