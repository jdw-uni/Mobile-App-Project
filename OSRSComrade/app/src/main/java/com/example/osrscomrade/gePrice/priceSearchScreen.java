package com.example.osrscomrade.gePrice;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osrscomrade.R;

import java.util.Objects;

public class priceSearchScreen extends AppCompatActivity {

    EditText searchText;
    Button buttonSearch;

    String search, searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pricesearchscreen);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search_ge_header);

        searchText = findViewById(R.id.searchText);
        buttonSearch = findViewById(R.id.buttonSearch);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    buttonSearch.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    public void itemSearch(View view) {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (searchText.getText().toString().trim().length() == 0) {
                Toast.makeText(getApplicationContext(), "Please enter an item", Toast.LENGTH_SHORT).show();
            } else {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);

                searchTerm = searchText.getText().toString();
                search = searchTerm.toLowerCase();
                Intent intent = new Intent(getApplicationContext(), priceListingScreen.class);
                intent.putExtra("search", search);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        finish();
    }
}