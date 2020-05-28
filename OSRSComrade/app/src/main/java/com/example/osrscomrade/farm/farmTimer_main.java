package com.example.osrscomrade.farm;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osrscomrade.R;

import java.util.Objects;


public class farmTimer_main extends AppCompatActivity {
    String[] PlantType = {"Herb", "Tree", "Fruit Tree"};

    String[] HerbType = {"Guam leaf", "Marrentill", "Tarromin", "Harralander", "Ranarr", "Toadflax", "Irit leaf", "Avantoe", "Kwuarm", "Snapdragon", "Cadantine", "Lantadyme", "Dwarf weed", "Torstol"};

    String[] TreeType = {"Acorn", "Willow", "Maple", "Yew", "Magic"};

    String[] FruitType = {"Apple", "Banana", "Orange", "Curry", "Pineapple", "Papaya", "Palm tree", "Dragonfruit"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmtimer_main);

        final Spinner spinnerPlant = findViewById(R.id.spinnerPlant);
        final Spinner spinnerType = findViewById(R.id.spinnerType);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.farm_timer);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, PlantType);

        spinnerPlant.setAdapter(countryAdapter);

        spinnerPlant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                switch (position) {
                    case 0:
                        ArrayAdapter<String> countryCodeAdapterHerb = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, HerbType);
                        spinnerType.setAdapter(countryCodeAdapterHerb);
                        break;
                    case 1:
                        ArrayAdapter<String> countryCodeAdapterTree = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, TreeType);
                        spinnerType.setAdapter(countryCodeAdapterTree);
                        break;
                    case 2:
                        ArrayAdapter<String> countryCodeAdapterFruit = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, FruitType);
                        spinnerType.setAdapter(countryCodeAdapterFruit);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //spinnerPlant.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


    }


    public void onBackPressed() {
        finish();
    }

}
