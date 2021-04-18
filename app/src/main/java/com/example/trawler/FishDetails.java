package com.example.trawler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FishDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_details);

        Intent intent = getIntent();
        String name=intent.getStringExtra("FishName");
        String url=intent.getStringExtra("URL");
        String size1=intent.getStringExtra("Size");
        String weight1=intent.getStringExtra("Weight");
        String description1=intent.getStringExtra("Description");

        ImageView img1 = findViewById(R.id.img1);
        TextView fishName = findViewById(R.id.fishName);
        TextView size = findViewById(R.id.size1);
        TextView weight = findViewById(R.id.weight1);
        TextView details = findViewById(R.id.Details);
        TextView description = findViewById(R.id.Description);

        fishName.setText(name);
        if(description1.equals("null")){
            description.setText("No description provided for this fish.");
        }
        else{
            description.setText(description1);
        }
        details.setText("Description");

        if(!size1.equals("NaN")){
            size.setText("Length: " + Double.parseDouble(size1) + " cm");
        }
        else {
            size.setText("Length: " + "??" + " cm");
        }

        if(!weight1.equals("NaN")){
            weight.setText("Weight: " + Double.parseDouble(weight1)/1000 + " kg");
        }
        else {
            weight.setText("Weight: " + "??" + " kg");
        }


        //img1.setImageResource(R.drawable.ic_launcher_foreground);
        //GlideApp.with(this).load(url).override(120, 120).error(R.drawable.ic_launcher_foreground).dontAnimate().into(img1);
        Picasso.get().load(url).error(R.drawable.ic_launcher_foreground).into(img1);
        Log.i("FishDetails", url);
    }
}