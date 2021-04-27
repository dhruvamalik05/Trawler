package com.example.trawler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trawler.adapter.ProfileAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private TextView usernameText;

    private ArrayList<Catch_Metadata> userCatches;
    private RecyclerView rvProfile;
    private ProfileAdapter adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference datRef = database.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile , container, false);

        usernameText = view.findViewById(R.id.username_text);
        usernameText.setText(MainActivity.uName);

        rvProfile = view.findViewById(R.id.rvProfile);

        Fish_Data fish1 = new Fish_Data("FISH", "FISH", 0);
        Catch_Metadata catch1 = new Catch_Metadata("Foobar", new LatLng(0, 0), getString(R.string.default_fish_image), "4/27/21 at 12:26:00 pm", fish1);
        Catch_Metadata catch2 = new Catch_Metadata("Foobar", new LatLng(0, 0), getString(R.string.default_fish_image), "4/27/21 at 12:26:00 pm", fish1);
        Catch_Metadata catch3 = new Catch_Metadata("Foobar", new LatLng(0, 0), getString(R.string.default_fish_image), "4/27/21 at 12:26:00 pm", fish1);



        generateCatches();

        return view;
    }
    private void generateCatches() {
        userCatches = new ArrayList<>();

        datRef.child("Catches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()) {
                    for(DataSnapshot s2 : s.getChildren()) {
                        String uID = (String) s2.child("uID").getValue();

                        if(uID.equals(MainActivity.uName)) {

                            double lat = (double) s2.child("location").child("latitude").getValue(Double.class);
                            double lon = (double) s2.child("location").child("longitude").getValue(Double.class);
                            Log.i("LatLong", lat + " " + lon);
                            LatLng loc = new LatLng(lat, lon);

                            String fishIMG = (String) s2.child("fish_image").getValue(String.class);
                            String time = (String) s2.child("time_of_catch").getValue(String.class);

                            Fish_Data fish = (Fish_Data) s2.child("fish_info").getValue(Fish_Data.class);

                            userCatches.add(new Catch_Metadata(uID, loc, fishIMG, time, fish));
                        }
                    }
                }
                updateRecycler();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Firebase", "error");
            }
        });

        Log.i("MAP", "len: " + userCatches.size());
        //Some test values.
        //Fish_Data fish1 = new Fish_Data("COMNAME", "transliteration", 1);
        //Catch_Metadata catch1 = new Catch_Metadata("UID1", new LatLng(32.7357, -97.1081), "IMG", "TIME", fish1);
        //Catch_Metadata catch2 = new Catch_Metadata("UID2", new LatLng(40.7128, -74.0060), "IMG", "TIME", fish1);

        //catches.add(catch1);
        //catches.add(catch2);
        //leaving this for testing
    }
    private void updateRecycler() {
        Log.i("PROFILE", "len: " + userCatches.size());
        adapter = new ProfileAdapter(userCatches, getActivity());
        rvProfile.setAdapter(adapter);
        rvProfile.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
}
