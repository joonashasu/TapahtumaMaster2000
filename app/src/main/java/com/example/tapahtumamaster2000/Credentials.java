package com.example.tapahtumamaster2000;

import java.util.HashMap;
import java.util.Map;

public class Credentials{

    HashMap<String, String> credetialsHashmap = new HashMap<String, String>();

    public void addUser(String username, String password){
        credetialsHashmap.put(username, password);
    }

    public boolean CheckUsername(String username){
        return credetialsHashmap.containsKey(username);
    }

    public boolean CheckCredentials(String username, String password){

        if(credetialsHashmap.containsKey(username)){
            if(password.equals(credetialsHashmap.get(username))){
                return true;
            }
        }
        return false;
    }

    public void credentialLoader (Map<String, ?> preferencesMap){
        for(Map.Entry<String, ?> entries: preferencesMap.entrySet()){
            credetialsHashmap.put(entries.getKey(), entries.getValue().toString());
        }
    }
}