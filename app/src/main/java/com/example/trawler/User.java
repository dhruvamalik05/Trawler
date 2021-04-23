package com.example.trawler;

import android.provider.ContactsContract;

public class User {
    String uName;
    String pass;
    String fName;
    String lName;
    String email;



    public User(){
        uName = "";
        pass = "";
        fName = "";
        lName = "";
        email = "";
    }

    public User(String uName, String pass, String fName, String lName, String email) {
        this.uName = uName;
        this.pass = pass;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
