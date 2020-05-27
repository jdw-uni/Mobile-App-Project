package com.example.osrscomrade.gePrice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.osrscomrade.R;
import com.example.osrscomrade.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class priceListingScreen extends AppCompatActivity {

    /*

    IMPORTANT, THE BELOW ARE FOR THE TREE MENU SYSTEM

     */

    int itemID, itemPrice;

    String search, getURL;

    LinearLayout itemListView;
    //Set a progress dialog to show loading of data
    private ProgressDialog pDialog;

    public ArrayList<String> itemListNames = new ArrayList<>();
    public ArrayList<Integer> itemListIDs = new ArrayList<>();
    public ArrayList<Integer> itemListPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pricelistingscreen);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        search = extras.getString("search");

        //Load Views
        itemListView = findViewById(R.id.itemListingView);

        // URL to get JSON
        getURL = "http://ahdesigns.coolpage.biz/items.json";

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(search);

        new GetData().execute();
    }

    //Get data class to get json data
    @SuppressLint("StaticFieldLeak")
    private class GetData extends AsyncTask<Void, Void, Void> {

        //Before data will be looked up
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(priceListingScreen.this);
            pDialog.setMessage("Searching Item List...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        //During the background operation
        @Override
        protected Void doInBackground(Void... arg0) {

            // Creating service handler class
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(getURL, ServiceHandler.GET);

            //Log for debug, can be deleted later
            Log.d("Response: ", "> " + jsonStr);

            //Check to see if JSON string is not empty
            if (jsonStr != null) {
                try {

                    //Create a new JSON object
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting the 'item' array from the JSON object
                    JSONArray itemArray = jsonObj.getJSONArray("items");

                    //Create a loop for the array length
                    for (int p = 0; p < itemArray.length(); p++) {

                        //Get the current JSON object
                        JSONObject itemData = itemArray.getJSONObject(p);
                        //Get the items name and ID and add to the arrays

                        itemListIDs.add(itemData.getInt("id"));
                        itemListNames.add(itemData.getString("name"));
                        itemListPrices.add(itemData.getInt("value"));

                    }

                    //Catch any errors
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //JSON was empty, there was an error on URL
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            addItemsToList();

            if (itemListView.getChildCount() == 0) {
                Toast.makeText(getApplicationContext(), "No items found", Toast.LENGTH_SHORT).show();
                finish();
            }


            //After we have obtained the JSON data
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }

    }

    void addItemsToList() {
        for (int i = 0; i < itemListNames.size(); i++) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 20, 20, 20);
            TextView itemTextView = new TextView(this);
            itemTextView.setLayoutParams(params);

            if ((itemListNames.get(i)).toLowerCase().contains(search)) {
                itemTextView.setText(itemListNames.get(i));
                itemTextView.setId(itemListIDs.get(i));
                itemTextView.setTag(itemListPrices.get(i));
                itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.page_text_size));
                itemTextView.setTextColor(Color.parseColor("#f4f5f8"));
                itemTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToItemPage(v);
                    }
                });
                itemListView.addView(itemTextView);
            }
        }
    }

    void goToItemPage(View view) {
        itemID = Integer.parseInt(String.valueOf(view.getId()));
        itemPrice = Integer.parseInt(view.getTag().toString());
        Intent intent = new Intent(this, priceDetailsScreen.class);
        intent.putExtra("itemID", itemID);
        intent.putExtra("itemPrice", itemPrice);
        startActivity(intent);
    }

    public void onBackPressed() {
        finish();
    }
}