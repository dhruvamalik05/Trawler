package com.example.trawler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fish {
    String name;
    int specCode;
    String scientificName;
    String shortDescription;
    String biology;
    double size;
    double weight;
    String pictureURL;
    String size1;
    String weight1;



    public Fish() {}

    public Fish(JSONObject jsonObject) throws JSONException {
        name=jsonObject.getString("Species");
        specCode=jsonObject.getInt("SpecCode");
        //pictureURL="https://www.fishbase.se/images/species/"+jsonObject.getString("PicPreferredName");
        pictureURL=jsonObject.getString("PicPreferredName");
        size1=Double.toString(jsonObject.optDouble("Length"));
        weight1=Double.toString(jsonObject.optDouble("Weight"));
        biology=jsonObject.getString("Comments");
    }

    public static ArrayList<Fish> fromJsonArray(JSONArray fishArray) throws JSONException {
        ArrayList<Fish> encyclopedia = new ArrayList<>();
        for(int i=0 ; i<fishArray.length() ; i++) {
            encyclopedia.add(new Fish(fishArray.getJSONObject(i)));
        }
        return encyclopedia;
    }

    public String getSize1() {
        return size1;
    }

    public String getWeight1() {
        return weight1;
    }
    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public double getWeight() {
        return weight;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getSpecCode() {
        return specCode;
    }

    public String getBiology() {
        return biology;
    }

    public void setBiology(String biology) {
        this.biology = biology;
    }

    public String getPictureURL() {
        return "https://www.fishbase.se/images/species/"+pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

