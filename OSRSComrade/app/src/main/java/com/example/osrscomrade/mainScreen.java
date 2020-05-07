package com.example.osrscomrade;


import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public class mainScreen extends AppCompatActivity {

    String versionName = "1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.main_header);

        TextView textViewVersion = findViewById(R.id.textViewVersion);
        textViewVersion.setText(String.format("%s%s", getString(R.string.version), versionName));

    }

    public void onBackPressed() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Closes the activity as oppose to navigating up.
        return false;
    }







}