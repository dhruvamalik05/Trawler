package com.example.trawler.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trawler.Fish;
import com.example.trawler.FishDetails;
import com.example.trawler.R;

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
        holder.size.setText("Size: " + Double.toString(fish.getSize()) + " ft");
        holder.weight.setText("Weight: "+ Double.toString(fish.getWeight()) + " lbs");
        holder.iconButton.setImageResource(R.drawable.ic_launcher_foreground);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            name = itemView.findViewById(R.id.name);
            size = itemView.findViewById(R.id.size);
            weight = itemView.findViewById(R.id.weight);
            iconButton = itemView.findViewById(R.id.imageView);

            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");
            int pos=this.getAdapterPosition();
            Fish fish=encyclopedia.get(pos);
            String name = fish.getName();
            // Toast.makeText(context, "Item clicked", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, FishDetails.class);
            intent.putExtra("FishName", name);
            context.startActivity(intent);
        }
    }
}

