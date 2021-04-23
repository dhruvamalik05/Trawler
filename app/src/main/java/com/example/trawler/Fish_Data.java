package com.example.trawler;

public class Fish_Data {
    String ComName;
    String Transliteration;
    int SpecCode;

    public Fish_Data() {
    }

    public String getComName() {
        return ComName;
    }

    public Fish_Data(String comName, String transliteration, int specCode) {
        ComName = comName;
        Transliteration = transliteration;
        SpecCode = specCode;
    }

    public void setComName(String comName) {
        ComName = comName;
    }

    public String getTransliteration() {
        return Transliteration;
    }

    public void setTransliteration(String transliteration) {
        Transliteration = transliteration;
    }

    public int getSpecCode() {
        return SpecCode;
    }

    public void setSpecCode(int specCode) {
        SpecCode = specCode;
    }
}
