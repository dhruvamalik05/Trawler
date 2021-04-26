package com.example.trawler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;

public class MapFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean permissionDenied = false;

    private GoogleMap mMap;
    private UiSettings mUiSettings;

    // Filter values
    private static int range = 100; // Range in miles
    private static int timeSinceCatch = 48; // Only show catches within "timeSinceCatch" hours
    private static boolean favoritedOnly = false; // Whether to only show favorited fish
    private static boolean notYetCaught = false; // Whether to only show fish that the user has not caught before
    //TODO species filter

    private Button filterButton;
    private Intent filters;

    private ArrayList<Catch_Metadata> catches;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference datRef = database.getReference();

    FusedLocationProviderClient client;

    Location userLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        filters = new Intent(getActivity(), MapFilters.class);

        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        generateCatches();

        mUiSettings = mMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);

        onMyLocationButtonClick();

        filterButton = getView().findViewById(R.id.Filters_Butt);
        filterButton.setOnClickListener((v) -> {
            startActivity(filters);
            drawPins();
        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = client.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    userLocation = location;
                    drawPins();
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("LocationTask", e.getLocalizedMessage());
            }
        });
    }

    private double milesDistanceFromUser(LatLng loc) {
        //getLastLocation();
        if(userLocation == null) {
            return 0;
        }
        float [] results = new float[1];
        Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(), loc.latitude, loc.longitude, results);
        float distMeters = results[0];
        double miles = (double) distMeters * 0.000621371;
        return Math.abs(miles);
    }
    private boolean filterCompatible(Catch_Metadata catch_data) {
        boolean inRange = milesDistanceFromUser(catch_data.getLocation()) <= range;

        //TODO Update these
        boolean withinTimeMax = true;
        boolean favoritedFish = true;
        boolean userPreviouslyCaught = false;

        return inRange && withinTimeMax && favoritedFish && !userPreviouslyCaught;
    }
    private void drawPins() {
        mMap.clear();
        for(Catch_Metadata catch_data:catches) {
            if(filterCompatible(catch_data)) {
                mMap.addMarker(new MarkerOptions().position(catch_data.Location).title(catch_data.uID));
            }
        }
    }
    private void generateCatches() {
        catches = new ArrayList<>();

        datRef.child("Catches").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()) {
                    for(DataSnapshot s2 : s.getChildren()) {
                        String uID = (String) s2.child("uID").getValue();

                        double lat = (double) s2.child("location").child("latitude").getValue(Double.class);
                        double lon = (double) s2.child("location").child("longitude").getValue(Double.class);
                        Log.i("LatLong", lat + " " + lon);
                        LatLng loc = new LatLng(lat, lon);

                        String fishIMG = (String) s2.child("fish_image").getValue(String.class);
                        String time = (String) s2.child("time_of_catch").getValue(String.class);

                        Fish_Data fish = (Fish_Data) s2.child("fish_info").getValue(Fish_Data.class);
                        //LatLng sydney = new LatLng(-33.8688, 151.2093);
                        //Fish_Data fish1 = new Fish_Data("COMNAME", "transliteration", 1);
                        catches.add(new Catch_Metadata(uID, loc, fishIMG, time, fish));

                    }
                }
                drawPins();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Firebase", "error");
            }
        });


        //Some test values.
        //Fish_Data fish1 = new Fish_Data("COMNAME", "transliteration", 1);
        //Catch_Metadata catch1 = new Catch_Metadata("UID1", new LatLng(32.7357, -97.1081), "IMG", "TIME", fish1);
        //Catch_Metadata catch2 = new Catch_Metadata("UID2", new LatLng(40.7128, -74.0060), "IMG", "TIME", fish1);

        //catches.add(catch1);
        //catches.add(catch2);
    }

    private void enableMyLocation() {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if(mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if(PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            permissionDenied = true;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if(permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
    }
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getChildFragmentManager(), "dialog");
    }
    public static void setRange(int r) {
        range = r;
    }
    public static void setTimeSinceCatch(int t) {
        timeSinceCatch = t;
    }
    public static void setFavoritedOnly(boolean b) {
        favoritedOnly = b;
    }
    public static void setNotYetCaught(boolean b) {
        notYetCaught = b;
    }
    public static int getRange() {return range;}
    public static int getTimeSinceCatch() {return timeSinceCatch;}
    public static boolean isFavoritedOnly() {return favoritedOnly;}
    public static boolean isNotYetCaught() {return notYetCaught;}
}
