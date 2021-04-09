package com.example.trawler;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference datRef = database.getReference();
    DatabaseReference users = datRef.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button reg = findViewById(R.id.RegButt);
        reg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String uName = ((EditText)findViewById(R.id.UserNameReg)).getText().toString();
                String passName = ((EditText)findViewById(R.id.EnterPass)).getText().toString();
                String fName = ((EditText)findViewById(R.id.FirstName)).getText().toString();
                String lName = ((EditText)findViewById(R.id.LastName)).getText().toString();
                String email = ((EditText)findViewById(R.id.email)).getText().toString();
                if(uName == "" || passName == "" || fName == "" || lName == "" || email == ""){
                    Toast.makeText(getApplicationContext(), "Please Fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passName.equals(((EditText)findViewById(R.id.PassConf)).getText().toString())){
                    Toast.makeText(getApplicationContext(),  "Make sure the passwords match", Toast.LENGTH_SHORT).show();
                    return;
                }
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User temp = snapshot.child(((EditText)findViewById(R.id.UserNameReg)).getText().toString()).getValue(User.class);
                        if(temp == null){
                            User newUse = new User(uName, passName, fName, lName, email);
                            users.child(uName).setValue(newUse);
                            Toast.makeText(getApplicationContext(),  "User Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),  "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}