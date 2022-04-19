package com.example.tapahtumamaster2000;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

    public boolean testPassword(String pw) {

        boolean lowerCase = false;
        boolean UpperCase = false;
        boolean digit = false;
        boolean number = false;
        boolean specialCharacter = false;

        // Special character specifier
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher m = p.matcher(pw);

        // Goes throught password characher by character and checks if it meets the
        // requirements
        for (char i : pw.toCharArray()) {
            if (Character.isLowerCase(i))
                lowerCase = true;
            if (Character.isUpperCase(i))
                UpperCase = true;
            if (Character.isDigit(i))
                digit = true;
            if (m.find(i))
                specialCharacter = true;
        }

        if (lowerCase && UpperCase && digit && number && specialCharacter && pw.length() > 12) {
            System.out.println("Password accepted!");
            return true;
        } else {
            System.out.println("Password is too weak");
            return false;
        }
    }

    public void changeLocation(BusStop b, User u) {
        // changes the desired BusStop where user's trips start
        u.location = b;
    }

    public void changePassword(User u, String pass) {
        // encoding with e.g. hash here
        u.password = pass;
    }

    public void saveEvent(User u, Event ev) {
        if (u.savedEvents.size() == 0) {

        } else {
            u.savedEvents.add(ev);
            System.out.println("Event added");
        }
    }

    public void deleteEvent(User u, Event ev) {
        // seeks the position of desired event and deletes it from list
        for (int i = 0; i < savedEvents.size(); i++) {
            if (ev.ID == savedEvents.get(i).ID) {
                savedEvents.remove(i);
                System.out.println("Event deleted");
                break;
            }
        }
    }

    public void logIn(String un, String pw) {
        // asks for username, if not in list displays error message
        for (int i = 0; i < users.size(); i++) {
            if (un.equals(users.get(i).userName)) {
                if (pw.equals(users.get(i).password)) {
                    // Use decrypting
                    // log in
                }
            } else {
                System.out.println("No such user exists");
            }
        }

    }

    public User CreateAccount() {
        User u = new User();
        // ask for all necessary data
        users.add(u);
        return u;
    }

    public void deleteAccount(User u) {
        for (int i = 0; i < users.size(); i++) {
            if (u.ID == users.get(i).ID) {
                users.remove(i);
                System.out.println("Account deleted");
            }
        }
    }

    public void logOut(User u) {

    }

    public void changeUser(User u) {
        // logOut(u);
        // logIn()

    }

}
