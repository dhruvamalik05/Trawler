package com.example.trawler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button Login_Butt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login_Butt = findViewById(R.id.Login_Butt);
        Login_Butt.setOnClickListener((v) ->{
            Toast.makeText(getApplicationContext(),  "YOUR_MESSAGE_HERE", Toast.LENGTH_SHORT).show();
        });
    }

}