package com.example.trawler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class EncyclopediaFragment extends Fragment {
    public static EncyclopediaFragment newInstance() {
        EncyclopediaFragment fragment = new EncyclopediaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encyclopedia , container, false);
        return view;
    }
}
