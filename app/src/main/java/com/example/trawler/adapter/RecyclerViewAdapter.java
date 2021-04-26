package com.example.trawler.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trawler.Fish;
import com.example.trawler.FishDetails;
import com.example.trawler.GlideApp;
import com.example.trawler.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Fish> encyclopedia;

    public RecyclerViewAdapter(Context context, List<Fish> encyclopedia) {
        this.context = context;
        this.encyclopedia = encyclopedia;
    }

    // Where to get the single card as viewholder Object
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    // What will happen after we create the viewholder object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Fish fish = encyclopedia.get(position);

        holder.name.setText(fish.getName());
        if(fish.getSize1()!="NaN"){
            holder.size.setText("Size: " + Double.parseDouble(fish.getSize1()) + " cm");
        }
        else {
            holder.size.setText("Size: " + "??" + " cm");
        }

        if(fish.getWeight1()!="NaN"){
            holder.weight.setText("Weight: " + Double.parseDouble(fish.getWeight1())/1000 + " kg");
        }
        else {
            holder.weight.setText("Weight: " + "??" + " kg");
        }

        holder.check.setImageResource(R.drawable.ic_baseline_check_circle_24);
        if(!fish.isCaught()){
            holder.check.setImageResource(android.R.color.transparent);
        }
        GlideApp.with(context).load(fish.getPictureURL()).override(120, 120).error(R.drawable.ic_launcher_foreground).into(holder.iconButton);
        //Picasso.get().load(fish.getPictureURL()).into(holder.iconButton);
        Log.i("Recycler", fish.getPictureURL());
    }

    // How many items?
    @Override
    public int getItemCount() {
        return encyclopedia.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView size;
        public TextView weight;
        public ImageView iconButton;
        public ImageView check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.name);
            size = itemView.findViewById(R.id.size);
            weight = itemView.findViewById(R.id.weight);
            iconButton = itemView.findViewById(R.id.imageView);
            check = itemView.findViewById(R.id.check);

            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");
            int pos=this.getAdapterPosition();
            Fish fish=encyclopedia.get(pos);
            String name = fish.getName();
            String url = fish.getPictureURL();
            String size = fish.getSize1();
            String weight = fish.getWeight1();
            String biology = fish.getBiology();
            int specCode = fish.getSpecCode();
            boolean caught = fish.isCaught();

            // Toast.makeText(context, "Item clicked", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, FishDetails.class);
            intent.putExtra("FishName", name);
            intent.putExtra("URL", url);
            intent.putExtra("Size", size);
            intent.putExtra("Weight", weight);
            intent.putExtra("Biology", biology);
            intent.putExtra("Code", specCode);
            intent.putExtra("Caught", caught);
            context.startActivity(intent);
        }
    }
}

