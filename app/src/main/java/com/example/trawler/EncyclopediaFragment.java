package com.example.trawler;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.trawler.adapter.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class EncyclopediaFragment extends Fragment {

    ArrayList<Fish> encyclopedia=new ArrayList<>();
    //ArrayList<Fish> encyclopedia2=new ArrayList<>();
    Context context;
    public static final String URL="https://fishbase.ropensci.org/species?limit=100";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encyclopedia , container, false);
        RecyclerView rvFish=view.findViewById(R.id.rvFish);
        context=getActivity();

        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(context, encyclopedia);
        rvFish.setAdapter(recyclerViewAdapter);
        rvFish.setLayoutManager(new LinearLayoutManager(context));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i("Encyclopedia", "onSuccess");

                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("data");
                    Log.i("Encyclopedia", "here");
                    /*
                    for(int j=0 ; i<results.length() ; j++) {
                        Fish fish1 = new Fish(results.getJSONObject(j));
                        encyclopedia.add(fish1);
                    }

                     */

                    encyclopedia.addAll(Fish.fromJsonArray(results));
                    recyclerViewAdapter.notifyDataSetChanged();
                    Log.i("Encyclopedia", encyclopedia.toString());
                } catch (JSONException e) {
                    Log.e("Encyclopedia", "Hit JSON exception");
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e("Encyclopedia", "onFailure");
            }
        });

/*
        Fish fish1= new Fish();
        fish1.setName("Fish1");
        fish1.setSize(8.4);
        fish1.setWeight(124.6);

        Fish fish2= new Fish();
        fish2.setName("Fish2");
        fish2.setSize(8.4);
        fish2.setWeight(124.6);

        Fish fish3= new Fish();
        fish3.setName("Fish3");
        fish3.setSize(8.4);
        fish3.setWeight(124.6);

        Fish fish4= new Fish();
        fish4.setName("Fish4");
        fish4.setSize(8.4);
        fish4.setWeight(124.6);

        Fish fish5= new Fish();
        fish5.setName("Fish5");
        fish5.setSize(8.4);
        fish5.setWeight(124.6);

        Fish fish6= new Fish();
        fish6.setName("Fish6");
        fish6.setSize(8.4);
        fish6.setWeight(124.6);

        Fish fish7= new Fish();
        fish7.setName("Fish7");
        fish7.setSize(8.4);
        fish7.setWeight(124.6);

        Fish fish8= new Fish();
        fish8.setName("Fish8");
        fish8.setSize(8.4);
        fish8.setWeight(124.6);

        Fish fish9= new Fish();
        fish9.setName("Fish9");
        fish9.setSize(8.4);
        fish9.setWeight(124.6);

        Fish fish10= new Fish();
        fish10.setName("Fish10");
        fish10.setSize(8.4);
        fish10.setWeight(124.6);

        /*
        encyclopedia=new ArrayList<>();
        encyclopedia.add(fish1);
        encyclopedia.add(fish2);
        encyclopedia.add(fish3);
        encyclopedia.add(fish4);
        encyclopedia.add(fish5);
        encyclopedia.add(fish6);
        encyclopedia.add(fish7);
        encyclopedia.add(fish8);
        encyclopedia.add(fish9);
        encyclopedia.add(fish10);

        ArrayList<Fish> en2 = new ArrayList<>();
        for(int i=0 ; i<encyclopedia.size() ; i++){
            en2.add(encyclopedia.get(i));
        }
        Log.i("Encyc", en2.toString());

 */


        return view;
    }
}