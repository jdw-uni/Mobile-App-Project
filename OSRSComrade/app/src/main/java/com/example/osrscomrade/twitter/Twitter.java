package com.example.osrscomrade.twitter;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.osrscomrade.R;
import com.example.osrscomrade.news.NewsTimelineFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class Twitter extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.twitter_view_pager);

        TabLayout tabLayout = findViewById(R.id.twitter_tab);

        //Setting tab over viewpager
        tabLayout.setupWithViewPager(viewPager);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.search_news);


        //Get tab array from string.xml
        String[] tabArray = getResources().getStringArray(R.array.tab_items);

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(OsrsTimelineFragment.newInstance(), tabArray[0]);
        adapter.addFrag(NewsTimelineFragment.newInstance(), tabArray[1]);
        viewPager.setAdapter(adapter);
    }

    public void onBackPressed() {
        finish();
    }

}
