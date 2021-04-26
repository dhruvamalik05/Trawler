package com.example.trawler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button Login_Butt;
    Button Register_Butt;
    Button Forgot_Butt;
    EditText uBox, pBox;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference datRef = database.getReference();
    DatabaseReference users = datRef.child("users");
    Intent i;
    Intent Reg;
    ArrayList<Integer> specCodes= new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 0);
        uBox = findViewById(R.id.Username_Box);
        pBox = findViewById(R.id.Password_Box);
        Login_Butt = findViewById(R.id.Login_Butt);
        i = new Intent(this, MainActivity.class);
        Reg = new Intent(this, RegisterActivity.class);
        Login_Butt.setOnClickListener((v) ->{
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User temp = snapshot.child(uBox.getText().toString()).getValue(User.class);
                    if(temp == null){
                        Toast.makeText(getApplicationContext(),  "Not valid user", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean isLoggedIn = temp.getPass().equals(pBox.getText().toString());
                    if(isLoggedIn){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Catches").child(temp.getuName());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                specCodes.clear();
                                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                    specCodes.add(0, snapshot1.child("fish_info").child("specCode").getValue(Integer.class));
                                }
                                i.putExtra("User", temp.getuName());
                                i.putIntegerArrayListExtra("Catches", specCodes);
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.i("Firebase", "error");
                            }
                        });
                        //i.putExtra("User", temp.getuName());
                        //i.putIntegerArrayListExtra("Catches", specCodes);
                        //startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        Register_Butt = findViewById(R.id.Register_Butt);
        Register_Butt.setOnClickListener((v)->{
            startActivity(Reg);
        });
    }

}