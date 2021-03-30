package com.example.trawler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a viewpager, which holds our fragments in a page layout that can be swiped
        ViewPager viewPager = findViewById(R.id.viewPager);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        // Set the default page to 2, which is our camera fragment. Fragments are 0 = Profile,
        // 1 = Map, 2 = Camera, 3 = Encyclopedia, 4 = Settings
        viewPager.setCurrentItem(2);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private final int NUM_PAGES = 5;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) { // Display the proper fragment
            switch(position) {
                case 0:
                    return ProfileFragment.newInstance();
                case 1:
                    return MapFragment.newInstance();
                case 2:
                    return CameraFragment.newInstance();
                case 3:
                    return EncyclopediaFragment.newInstance();
                case 4:
                    return SettingsFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}