package com.example.trawler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MapFilters extends AppCompatActivity {

    Button applyButton, backButton, resetToDefaultsButton;
    EditText rangeBox, timeBox;
    CheckBox favChk, nycChk;

    // Filter values
    private int range; // Range in miles
    private int timeSinceCatch; // Only show catches within "timeSinceCatch" hours
    private boolean favoritedOnly; // Whether to only show favorited fish
    private boolean notYetCaught; // Whether to only show fish that the user has not caught before
    //TODO species filter

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_filters);
        rangeBox = findViewById(R.id.Range_Box);
        timeBox = findViewById(R.id.Time_Box);
        favChk = findViewById(R.id.Favorite_Check);
        nycChk = findViewById(R.id.NYC_Check);

        this.range = MapFragment.getRange();
        this.timeSinceCatch = MapFragment.getTimeSinceCatch();
        this.favoritedOnly = MapFragment.isFavoritedOnly();
        this.notYetCaught = MapFragment.isNotYetCaught();

        updateFields();
    }
    public void updateFields() {
        rangeBox.setText("" + range, TextView.BufferType.EDITABLE);
        timeBox.setText("" + timeSinceCatch, TextView.BufferType.EDITABLE);
        favChk.setChecked(favoritedOnly);
        nycChk.setChecked(notYetCaught);
    }
    public void favoriteCheck(View view) {
        favoritedOnly = ((CheckBox)view).isChecked();
    }
    public void nycCheck(View view) {
        notYetCaught = ((CheckBox)view).isChecked();
    }
    public void applyButtonClicked(View view) {
        MapFragment.setRange(Integer.parseInt(String.valueOf(rangeBox.getText())));
        MapFragment.setTimeSinceCatch(Integer.parseInt(String.valueOf(timeBox.getText())));
        MapFragment.setFavoritedOnly(favoritedOnly);
        MapFragment.setNotYetCaught(notYetCaught);
        Toast.makeText(this, "Filters Applied", Toast.LENGTH_SHORT).show();
        super.finish();
    }
    public void backButtonClicked(View view) {
        super.finish();
    }
    public void resetButtonClicked(View view) {
        range = 100;
        timeSinceCatch = 48;
        favoritedOnly = false;
        notYetCaught = false;
        Toast.makeText(this, "Filters Reset", Toast.LENGTH_SHORT).show();
        updateFields();
    }
}
