package com.example.trawler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a viewpager, which holds our fragments in a page layout that can be swiped
        viewPager = findViewById(R.id.viewPager);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        // Set the default page to 2, which is our camera fragment. Fragments are 0 = Profile,
        // 1 = Map, 2 = Camera, 3 = Encyclopedia, 4 = Settings
        viewPager.setCurrentItem(2);

        viewPager.setOnPageChangeListener(listener);
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

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //resetButtons();

        }

        @Override
        public void onPageSelected(int position) {
            resetButtons();
            ImageButton btn;
            switch(position) {
                case 0:
                    btn = (ImageButton) findViewById(R.id.imageButton0);
                    btn.setImageResource(R.drawable.profile_selected);
                    viewPager.setCurrentItem(0);
                    return;
                case 1:
                    btn = (ImageButton) findViewById(R.id.imageButton1);
                    btn.setImageResource(R.drawable.map_selected);
                    viewPager.setCurrentItem(1);
                    return;
                case 2:
                    btn = (ImageButton) findViewById(R.id.imageButton2);
                    btn.setImageResource(R.drawable.camera_selected);
                    viewPager.setCurrentItem(2);
                    return;
                case 3:
                    btn = (ImageButton) findViewById(R.id.imageButton3);
                    btn.setImageResource(R.drawable.encyclopedia_selected);
                    viewPager.setCurrentItem(3);
                    return;
                case 4:
                    btn = (ImageButton) findViewById(R.id.imageButton4);
                    btn.setImageResource(R.drawable.settings_selected);
                    viewPager.setCurrentItem(4);
                    return;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //resetButtons();
        }
    };

    public void resetButtons() { //reset all buttons to the non-pressed state
        ImageButton btn;

        btn = (ImageButton)findViewById(R.id.imageButton0);
        btn.setImageResource(R.drawable.profile_unselected);

        btn = (ImageButton)findViewById(R.id.imageButton1);
        btn.setImageResource(R.drawable.map_unselected);

        btn = (ImageButton)findViewById(R.id.imageButton2);
        btn.setImageResource(R.drawable.camera_unselected);

        btn = (ImageButton)findViewById(R.id.imageButton3);
        btn.setImageResource(R.drawable.encyclopedia_unselected);

        btn = (ImageButton)findViewById(R.id.imageButton4);
        btn.setImageResource(R.drawable.settings_unselected);
    }

    //Each of the below will set the button of the selected fragment to be selected and all others
    //cleared

    public void profileButton(View view) {
        listener.onPageSelected(0);
    }
    public void mapButton(View view) {
        listener.onPageSelected(1);
    }
    public void cameraButton(View view) {
        listener.onPageSelected(2);
    }
    public void encyclopediaButton(View view) {
        listener.onPageSelected(3);
    }
    public void settingsButton(View view) {
        listener.onPageSelected(4);
    }
}