package com.example.trawler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class LoginActivity extends AppCompatActivity {

    Button Login_Butt;
    Button Register_Butt;
    Button Forgot_Butt;
    EditText uBox, pBox;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference datRef = database.getReference();
    DatabaseReference users = datRef.child("users");
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uBox = findViewById(R.id.Username_Box);
        pBox = findViewById(R.id.Password_Box);
        Login_Butt = findViewById(R.id.Login_Butt);
        i = new Intent(this, MainActivity.class);
        Login_Butt.setOnClickListener((v) ->{
            Toast.makeText(getApplicationContext(),  uBox.getText(), Toast.LENGTH_SHORT).show();
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Integer uId = snapshot.child("uNames").child(uBox.getText().toString()).getValue(Integer.class);
                    boolean isLoggedIn = snapshot.child("Pass").child(uId.toString()).getValue(String.class).equals(pBox.getText().toString());
                    if(isLoggedIn){
                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

}