package com.example.trawler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trawler.Catch_Metadata;
import com.example.trawler.GlideApp;
import com.example.trawler.R;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    ArrayList<Catch_Metadata> local;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.profile_catch_view_img);
        }
        public ImageView getImageView() {
            return imageView;
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
        //GlideApp.with(context).load(local.get(position).getFish_image()).error(R.drawable.placeholder_fish).into(holder.getImageView());
        GlideApp.with(context).load(local.get(position).getFish_image()).error(R.drawable.placeholder_fish).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return local.size();
    }
}
