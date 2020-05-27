package com.example.osrscomrade;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osrscomrade.combat.combat_level;
import com.example.osrscomrade.farm.farmTimer_main;
import com.example.osrscomrade.gePrice.priceSearchScreen;
import com.example.osrscomrade.playerStats.statSearchScreen;
import com.example.osrscomrade.twitter.Twitter;
import com.example.osrscomrade.wiki.wikiMain;
import com.example.osrscomrade.map.map_main;

import java.util.Objects;


public class mainScreen extends AppCompatActivity {


    //Version name to give the app a more real feel
    String versionName = "0.8.2.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.main_header);

        TextView textViewVersion = findViewById(R.id.textViewVersion);
        textViewVersion.setText(String.format("%s%s", getString(R.string.version), versionName));

    }

    public void newsActivity(View view) {
        startActivity(new Intent(this, Twitter.class));
    }

    public void wikiActivity(View view) { startActivity(new Intent(this, wikiMain.class)); }

    public void farmActivity(View view) {
        startActivity(new Intent(this, farmTimer_main.class));
    }

    public void mapActivity(View view) {  startActivity(new Intent(this, map_main.class));  }

    public void combatActivity(View view) {  startActivity(new Intent(this, combat_level.class));  }

    public void statsActivity(View view) {  startActivity(new Intent(this, statSearchScreen.class));  }

    public void priceActivity(View view) {  startActivity(new Intent(this, priceSearchScreen.class));  }


    public void onBackPressed() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Closes the activity as oppose to navigating up.
        return false;
    }

}