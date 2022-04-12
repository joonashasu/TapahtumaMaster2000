package com.example.tapahtumamaster2000;

import java.util.ArrayList;

public class User {
    int ID;
    String userName;
    String password;
    BusStop location;
    ArrayList<Event> savedEvents;
    String transportMode;
    String language;
    double moneyUsed;

    ArrayList<User> users;



    public void changeLocation(BusStop b, User u){
        //changes the desired BusStop where user's trips start
        u.location = b;
    }

    public void changePassword(User u, String pass){
        //encoding with e.g. hash here
        u.password = pass;
    }

    public void saveEvent(User u, Event ev){
        if(u.savedEvents.size() == 0){

        }else{
            u.savedEvents.add(ev);
            System.out.println("Event added");
        }
    }

    public void deleteEvent(User u, Event ev){
        //seeks the position of desired event and deletes it from list
        for(int i = 0; i < savedEvents.size();i++){
            if(ev.ID == savedEvents.get(i).ID){
                savedEvents.remove(i);
                System.out.println("Event deleted");
                break;
            }
        }
    }
    public void logIn(String un, String pw){
        //asks for username, if not in list displays error message
        for(int i = 0; i < users.size();i++){
            if(un.equals(users.get(i).userName)){
                if(pw.equals(users.get(i).password)){
                    //Use decrypting
                    //log in
                }
            }else{
                System.out.println("No such user exists");
            }
        }

    }
    public User CreateAccount(){
        User u = new User();
        //ask for all necessary data
        users.add(u);
        return u;
    }

    public void deleteAccount(User u){
        for(int i = 0; i < users.size();i++){
            if(u.ID == users.get(i).ID){
                users.remove(i);
                System.out.println("Account deleted");
            }
        }
    }

    public void logOut(User u){

    }
    public void changeUser(User u){
        //logOut(u);
        //logIn()

    }

}
