package com.example.trawler;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.trawler.adapter.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class EncyclopediaFragment extends Fragment {

    ArrayList<Fish> encyclopedia=new ArrayList<>();
    //ArrayList<Fish> encyclopedia2=new ArrayList<>();
    Context context;
    RecyclerView rvFish;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Fish> searchEncyclopedia = new ArrayList<>();
    ArrayList<Fish> tempEncyclopedia = new ArrayList<>();
    public static final String URL="https://fishbase.ropensci.org/species?limit=100";
    public static final String URL2="https://fishbase.ropensci.org/species?Species=";
    public static final String URL3="https://fishbase.ropensci.org/species?SpecCode=";
    int count = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encyclopedia , container, false);

        context=getActivity();
        setHasOptionsMenu(true);
        rvFish=view.findViewById(R.id.rvFish);
        recyclerViewAdapter=new RecyclerViewAdapter(context, encyclopedia);
        rvFish.setAdapter(recyclerViewAdapter);
        rvFish.setLayoutManager(new LinearLayoutManager(context));
        encyclopedia.clear();
        //((AppCompatActivity) context).getSupportActionBar().setTitle("Fishidex: Your Catches");
        //((MainActivity) getActivity()).setActionBarTitle("Fishidex: Your Catches");
        //getActivity().getActionBar().setTitle("Fishidex: Your Catches");

        //Log.i("EncyclopediaFirebase", specCodes.toString());


            for (int item1 : MainActivity.specCode) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL3 + item1, new JsonHttpResponseHandler() {
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

    @Override
        public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL2+query, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Headers headers, JSON json) {
                        Log.i("Encyclopedia2", "onSuccess");

                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("data");
                            Log.i("Encyclopedia2", "here");
                    /*
                    for(int j=0 ; i<results.length() ; j++) {
                        Fish fish1 = new Fish(results.getJSONObject(j));
                        encyclopedia.add(fish1);
                    }

                     */
                            if(count==0) {
                                count=1;
                                Log.i("Encylcopedia2", Integer.toString(count));
                                tempEncyclopedia.addAll(encyclopedia);
                            }
                            searchEncyclopedia.clear();
                            searchEncyclopedia.addAll(Fish.fromJsonArray(results));
                            encyclopedia.clear();
                            encyclopedia.addAll(searchEncyclopedia);
                            //recyclerViewAdapter= new RecyclerViewAdapter(context, searchEncyclopedia);
                            recyclerViewAdapter.notifyDataSetChanged();
                            Log.i("Encyclopedia2", searchEncyclopedia.toString());
                        } catch (JSONException e) {
                            Log.e("Encyclopedia2", "Hit JSON exception");
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                        Log.e("EncyclopediaSearch", "onFailure");
                        Toast.makeText(context, "Couldn't find fish in database", Toast.LENGTH_LONG).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.i("Encyclopedia2", newText);
                if (count == 1) {
                    if (searchView.getQuery().length()==0) {
                        Log.i("Encyclopedia2", "No Text");
                        encyclopedia.clear();
                        encyclopedia.addAll(tempEncyclopedia);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

/*
    void firebasefunc(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Catches").child(MainActivity.uName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                specCodes.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    specCodes.add((Long) snapshot1.child("fish_info").child("specCode").getValue(Long.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Firebase", "error");
            }
        });

    }

 */


}