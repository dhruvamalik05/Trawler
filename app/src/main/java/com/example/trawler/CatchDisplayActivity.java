package com.example.trawler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class CatchDisplayActivity extends AppCompatActivity {

    //Catch info
    private String uID;
    private LatLng Location;
    private String fish_image;
    private String time_of_catch;
    private String comName, transliteration;
    private int specCode;

    private ImageView img;
    private TextView txt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_display);

        Intent intent = getIntent();
        uID = intent.getStringExtra("uID");
        Location = intent.getParcelableExtra("Location");
        fish_image = intent.getStringExtra("fish_image");
        time_of_catch = intent.getStringExtra("time_of_catch");
        comName = intent.getStringExtra("comName");
        transliteration = intent.getStringExtra("transliteration");
        specCode = intent.getIntExtra("specCode", 0);

        String displayMessage = transliteration + " caught by " + uID + " on " + time_of_catch
                + " at coordinates " + Location.latitude + ", " + Location.longitude;

        txt = findViewById(R.id.catch_display_txt);
        txt.setText(displayMessage);

        img = findViewById(R.id.fish_image);
        GlideApp.with(this).load(fish_image).error(R.drawable.placeholder_fish).into(img);
    }
}
