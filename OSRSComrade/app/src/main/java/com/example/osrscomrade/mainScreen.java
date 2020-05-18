package com.example.osrscomrade;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osrscomrade.twitter.Twitter;
import com.example.osrscomrade.wiki.wikiMain;

import java.util.Objects;


public class mainScreen extends AppCompatActivity {


    //Version name to give the app a more real feel
    String versionName = "0.2.0";

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

    public void wikiActivity(View view) {
        startActivity(new Intent(this, wikiMain.class));
    }


    public void onBackPressed() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Closes the activity as oppose to navigating up.
        return false;
    }

}