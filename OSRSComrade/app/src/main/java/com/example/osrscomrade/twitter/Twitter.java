package com.example.osrscomrade.twitter;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.osrscomrade.R;
import com.example.osrscomrade.news.NewsTimelineFragment;
import com.google.android.material.tabs.TabLayout;

public class Twitter extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.twitter_view_pager);

        tabLayout = findViewById(R.id.twitter_tab);

        //Setting tab over viewpager
        tabLayout.setupWithViewPager(viewPager);

        //Get tab array from string.xml
        String[] tabArray = getResources().getStringArray(R.array.tab_items);

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(OsrsTimelineFragment.newInstance(), tabArray[0]);
        adapter.addFrag(NewsTimelineFragment.newInstance(), tabArray[1]);
        viewPager.setAdapter(adapter);
    }

}
