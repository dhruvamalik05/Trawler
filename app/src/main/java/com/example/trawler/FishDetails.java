package com.example.trawler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FishDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_details);

        Intent intent = getIntent();
        String name=intent.getStringExtra("FishName");

        ImageView img1 = findViewById(R.id.img1);
        TextView fishName = findViewById(R.id.fishName);
        TextView size = findViewById(R.id.size1);
        TextView weight = findViewById(R.id.weight1);
        TextView details = findViewById(R.id.Details);
        TextView description = findViewById(R.id.Description);

        fishName.setText(name);
        details.setText("Description");
        description.setText("Dorsal spines (total): 0; Dorsal soft rays (total): 21-24; Anal spines: 0; Anal soft rays: 19 - 22; Vertebrae: 75 - 80. This species with is distinguished by the following characters: body elongate, circular in cross-section; D 21-24 with anterior rays forming a relatively high lobe, 5.4-10.6 body length (excluding the head and caudal fin); dorsal fin origin about equal with or slightly in front to anal fin origin; A 19-22 with anterior rays forming a relatively high lobe, in 5.5-8.0 in BL; pectoral-fin rays 13 to 15 (usually 14 or 15); 270-340 predorsal scales; 75-80 vertebrae; jaws extremely long, forming a stout beak armed with very sharp teeth; no gill rakers absent; caudal fin deeply emarginate, the lower lobe much longer than the upper one and the caudal peduncle with a distinct, black lateral keel; body colour dark bluish green above, silvery below; juveniles (to 20 cm body length) with elevated black lobe in posterior part of dorsal fin which is lost with growth; scales and bones green (Ref. 9682, 90102).");
        size.setText("Size: " + Double.toString(8.2) + " ft");
        weight.setText("Weight: "+ Double.toString(82.3) + " lbs");
        img1.setImageResource(R.drawable.ic_launcher_foreground);
    }
}