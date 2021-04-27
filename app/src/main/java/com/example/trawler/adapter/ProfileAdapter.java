package com.example.trawler.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trawler.CatchDisplayActivity;
import com.example.trawler.Catch_Metadata;
import com.example.trawler.Fish;
import com.example.trawler.FishDetails;
import com.example.trawler.GlideApp;
import com.example.trawler.R;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private static ArrayList<Catch_Metadata> local;
    private static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.profile_catch_view_img);
            imageView.setOnClickListener(this);
        }
        public ImageView getImageView() {
            return imageView;
        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");
            int pos = this.getAdapterPosition();
            Catch_Metadata c = local.get(pos);

            Intent catchView = new Intent(context, CatchDisplayActivity.class);

            catchView.putExtra("uID", c.getuID());
            catchView.putExtra("Location", c.getLocation());
            catchView.putExtra("fish_image", c.getFish_image());
            catchView.putExtra("time_of_catch", c.getTime_of_catch());
            catchView.putExtra("comName", c.getFish_info().getComName());
            catchView.putExtra("transliteration", c.getFish_info().getTransliteration());
            catchView.putExtra("specCode", c.getFish_info().getSpecCode());
            context.startActivity(catchView);
        }
    }

    public ProfileAdapter(ArrayList<Catch_Metadata> data, Context context) {
        local = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_catch_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        // Here you can get elements and replace them with what they really need to be
        GlideApp.with(context).load(local.get(position).getFish_image()).error(R.drawable.placeholder_fish).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return local.size();
    }
}
