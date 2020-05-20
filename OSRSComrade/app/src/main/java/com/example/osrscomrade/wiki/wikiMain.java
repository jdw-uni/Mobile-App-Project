package com.example.osrscomrade.wiki;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osrscomrade.R;
import com.example.osrscomrade.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

public class wikiMain extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private wikiAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    EditText searchText2;
    Button buttonSearch2;

    public ArrayList<String> itemListNames = new ArrayList<>();
    public ArrayList<String> itemListURLs = new ArrayList<>();


    String Rank1, Rank2, Rank3, Rank4, Rank5, Rank6, Rank7, Rank8, Rank9, Rank10, Rank11, Rank12, Rank13, Rank14, Rank15, Rank16, Rank17, Rank18, Rank19, Rank20, Rank21, Rank22, Rank23, Rank24, Rank25, Rank26, Rank27, Rank28, Rank29, Rank30;
    String URL1, URL2, URL3, URL4, URL5, URL6, URL7, URL8, URL9, URL10, URL11, URL12, URL13, URL14, URL15, URL16, URL17, URL18, URL19, URL20, URL21, URL22, URL23, URL24, URL25, URL26, URL27, URL28, URL29, URL30;

    //Set a progress dialog to show loading of data
    private ProgressDialog pDialog;


    String search, searchTerm, getURL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiki_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search_wiki);

        searchText2 = findViewById(R.id.searchText2);
        buttonSearch2 = findViewById(R.id.buttonAdd);

        searchText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    buttonSearch2.performClick();
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
            if (searchText2.getText().toString().trim().length() == 0) {
                Toast.makeText(getApplicationContext(), "Please enter an item", Toast.LENGTH_SHORT).show();
            } else {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(searchText2.getWindowToken(), 0);

                searchTerm = searchText2.getText().toString();
                search = searchTerm.toLowerCase();

                new wikiMain.GetData().execute();

            }
        } else {
            Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetData extends AsyncTask<Void, Void, Void> {

        //Before data will be looked up
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(wikiMain.this);
            pDialog.setMessage("Searching Wiki...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        //During the background operation
        @Override
        protected Void doInBackground(Void... arg0) {

            // URL to get JSON
            try {
                getURL = "https://bustus.000webhostapp.com/wiki.php?username=" + URLEncoder.encode(search, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

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
                    JSONObject name = jsonObj.getJSONObject("name");

                    JSONObject url = jsonObj.getJSONObject("url");

                    itemListNames.clear();
                    itemListURLs.clear();

                    Rank1 = name.getString("0");
                    URL1 = url.getString("0");
                    itemListNames.add(Rank1);
                    itemListURLs.add(URL1);

                    Rank2 = name.getString("1");
                    URL2 = url.getString("1");
                    itemListNames.add(Rank2);
                    itemListURLs.add(URL2);

                    Rank3 = name.getString("2");
                    URL3 = url.getString("2");
                    itemListNames.add(Rank3);
                    itemListURLs.add(URL3);

                    Rank4 = name.getString("3");
                    URL4 = url.getString("3");
                    itemListNames.add(Rank4);
                    itemListURLs.add(URL4);

                    Rank5 = name.getString("4");
                    URL5 = url.getString("4");
                    itemListNames.add(Rank5);
                    itemListURLs.add(URL5);

                    Rank6 = name.getString("5");
                    URL6 = url.getString("5");
                    itemListNames.add(Rank6);
                    itemListURLs.add(URL6);

                    Rank7 = name.getString("6");
                    URL7 = url.getString("6");
                    itemListNames.add(Rank7);
                    itemListURLs.add(URL7);

                    Rank8 = name.getString("7");
                    URL8 = url.getString("7");
                    itemListNames.add(Rank8);
                    itemListURLs.add(URL8);

                    Rank9 = name.getString("8");
                    URL9 = url.getString("8");
                    itemListNames.add(Rank9);
                    itemListURLs.add(URL9);

                    Rank10 = name.getString("9");
                    URL10 = url.getString("9");
                    itemListNames.add(Rank10);
                    itemListURLs.add(URL10);

                    Rank11 = name.getString("10");
                    URL11 = url.getString("10");
                    itemListNames.add(Rank11);
                    itemListURLs.add(URL11);

                    Rank12 = name.getString("11");
                    URL12 = url.getString("11");
                    itemListNames.add(Rank12);
                    itemListURLs.add(URL12);

                    Rank13 = name.getString("12");
                    URL13 = url.getString("12");
                    itemListNames.add(Rank13);
                    itemListURLs.add(URL13);

                    Rank14 = name.getString("13");
                    URL14 = url.getString("13");
                    itemListNames.add(Rank14);
                    itemListURLs.add(URL14);

                    Rank15 = name.getString("14");
                    URL15 = url.getString("14");
                    itemListNames.add(Rank15);
                    itemListURLs.add(URL15);

                    Rank16 = name.getString("15");
                    URL16 = url.getString("15");
                    itemListNames.add(Rank16);
                    itemListURLs.add(URL16);

                    Rank17 = name.getString("16");
                    URL17 = url.getString("16");
                    itemListNames.add(Rank17);
                    itemListURLs.add(URL17);

                    Rank18 = name.getString("17");
                    URL18 = url.getString("17");
                    itemListNames.add(Rank18);
                    itemListURLs.add(URL18);

                    Rank19 = name.getString("18");
                    URL19 = url.getString("18");
                    itemListNames.add(Rank19);
                    itemListURLs.add(URL19);

                    Rank20 = name.getString("19");
                    URL20 = url.getString("19");
                    itemListNames.add(Rank20);
                    itemListURLs.add(URL20);

                    Rank21 = name.getString("20");
                    URL21 = url.getString("20");
                    itemListNames.add(Rank21);
                    itemListURLs.add(URL21);

                    Rank22 = name.getString("21");
                    URL22 = url.getString("21");
                    itemListNames.add(Rank22);
                    itemListURLs.add(URL22);

                    Rank23 = name.getString("22");
                    URL23 = url.getString("22");
                    itemListNames.add(Rank23);
                    itemListURLs.add(URL23);

                    Rank24 = name.getString("23");
                    URL24 = url.getString("23");
                    itemListNames.add(Rank24);
                    itemListURLs.add(URL24);

                    Rank25 = name.getString("24");
                    URL25 = url.getString("24");
                    itemListNames.add(Rank25);
                    itemListURLs.add(URL25);

                    Rank26 = name.getString("25");
                    URL26 = url.getString("25");
                    itemListNames.add(Rank26);
                    itemListURLs.add(URL26);

                    Rank27 = name.getString("26");
                    URL27 = url.getString("26");
                    itemListNames.add(Rank27);
                    itemListURLs.add(URL27);

                    Rank28 = name.getString("27");
                    URL28 = url.getString("27");
                    itemListNames.add(Rank28);
                    itemListURLs.add(URL28);

                    Rank29 = name.getString("28");
                    URL29 = url.getString("28");
                    itemListNames.add(Rank29);
                    itemListURLs.add(URL29);

                    Rank30 = name.getString("29");
                    URL30 = url.getString("29");
                    itemListNames.add(Rank30);
                    itemListURLs.add(URL30);


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

            //After we have obtained the JSON data
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }

    }

    void addItemsToList() {
        ArrayList<wikiItem> exampleList = new ArrayList<>();

        exampleList.clear();


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new wikiAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new wikiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String url = itemListURLs.get(position);

                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        for (int i = 0; i < itemListNames.size(); i++) {

            if ((itemListNames.get(i)).toLowerCase().contains(search)) {
                exampleList.add(new wikiItem(R.drawable.wikiicon, itemListNames.get(i), itemListURLs.get(i)));
            }
        }


    }

    public void onBackPressed() {
        finish();
    }

}