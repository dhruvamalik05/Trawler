package com.example.trawler;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Intent.getIntent;
import static com.example.trawler.MainActivity.uName;



public class ProfileFragment extends Fragment {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference datRef = database.getReference();
    //Intent i;

   // private static String fName;

    private TextView nameTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile , container, false);
        //fName = i.getStringExtra("lName"); // idk this is not working





        nameTextView = view.findViewById(R.id.username_text);
        nameTextView.setText(uName);

        nameTextView = view.findViewById(R.id.name_text);
        nameTextView.setText(uName);//change to fName + lName later




        return view;
    }
}
