package com.example.trawler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class FishDetails extends AppCompatActivity {

    public static final String URL = "https://fishbase.ropensci.org/morphdat?fields=AddChars%2CSpeccode&Speccode=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_details);

        Intent intent = getIntent();
        String name=intent.getStringExtra("FishName");
        String url=intent.getStringExtra("URL");
        String size1=intent.getStringExtra("Size");
        String weight1=intent.getStringExtra("Weight");
        String biology1=intent.getStringExtra("Biology");
        int specCode=intent.getIntExtra("Code", -1);

        ImageView img1 = findViewById(R.id.img1);
        TextView fishName = findViewById(R.id.fishName);
        TextView size = findViewById(R.id.size1);
        TextView weight = findViewById(R.id.weight1);
        TextView details = findViewById(R.id.Details);
        TextView description = findViewById(R.id.Description);
        TextView header2 = findViewById(R.id.header2);
        TextView biology = findViewById(R.id.Biology);

        fishName.setText(name);
        if(biology1.equals("null")){
            biology.setText("No biology details provided for this fish.");
        }
        else{
            biology.setText(biology1);
        }
        details.setText("Description");
        header2.setText("Biology");

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

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL+specCode, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i("Encyclopedia", "onSuccess");

                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("data");
                    Log.i("Encyclopedia", "here");
                    String desc=results.getJSONObject(0).getString("AddChars");
                    if(!desc.equals("null")){
                        description.setText(desc);
                    }
                    else{
                        description.setText("Description not found for this fish.");
                    }
                    // Log.i("Encyclopedia", encyclopedia.toString());
                } catch (JSONException e) {
                    Log.e("FishDetails", "Hit JSON exception");
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e("FishDetails", "onFailure");
            }
        });




        //img1.setImageResource(R.drawable.ic_launcher_foreground);
        //GlideApp.with(this).load(url).override(120, 120).error(R.drawable.ic_launcher_foreground).dontAnimate().into(img1);
        Picasso.get().load(url).error(R.drawable.ic_launcher_foreground).into(img1);
        Log.i("FishDetails", url);
    }
}