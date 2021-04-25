package com.example.trawler;

import com.google.android.gms.maps.model.LatLng;

public class Catch_Metadata {
    String uID;
    LatLng Location;
    String fish_image;
    String time_of_catch;
    Fish_Data fish_info;

    public Catch_Metadata(){
        uID = "";
        Location = new LatLng(0,0);
        fish_image = "";
        time_of_catch = "";
        fish_info = new Fish_Data();
    }

    public Catch_Metadata(String uID, LatLng location, String fish_image, String time_of_catch, Fish_Data fish_info) {
        this.uID = uID;
        Location = location;
        this.fish_image = fish_image;
        this.time_of_catch = time_of_catch;
        this.fish_info = fish_info;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public LatLng getLocation() {
        return Location;
    }

    public void setLocation(LatLng location) {
        Location = location;
    }

    public String getFish_image() {
        return fish_image;
    }

    public void setFish_image(String fish_image) {
        this.fish_image = fish_image;
    }

    public String getTime_of_catch() {
        return time_of_catch;
    }

    public void setTime_of_catch(String time_of_catch) {
        this.time_of_catch = time_of_catch;
    }

    public Fish_Data getFish_info() {
        return fish_info;
    }

    public void setFish_info(Fish_Data fish_info) {
        this.fish_info = fish_info;
    }
}
