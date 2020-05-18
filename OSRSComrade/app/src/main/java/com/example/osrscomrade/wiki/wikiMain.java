package com.example.osrscomrade.wiki;

import android.annotation.SuppressLint;
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
    public ArrayList<Integer> itemListIDs = new ArrayList<>();

    String Rank1, Rank2, Rank3, Rank4, Rank5, Rank6, Rank7, Rank8, Rank9, Rank10, Rank11, Rank12, Rank13, Rank14, Rank15, Rank16, Rank17, Rank18, Rank19, Rank20;
    String URL1, URL2, URL3, URL4, URL5, URL6, URL7, URL8, URL9, URL10, URL11, URL12, URL13, URL14, URL15, URL16, URL17, URL18, URL19, URL20;

    //Set a progress dialog to show loading of data
    private ProgressDialog pDialog;


    String search, searchTerm, getURL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiki_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.search_wiki);

        searchText2 = findViewById(R.id.searchText2);
        buttonSearch2 = findViewById(R.id.buttonSearch2);

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
                getURL = "https://bustuswiki.000webhostapp.com/?username=" + URLEncoder.encode(search, "UTF-8");
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
                    itemListIDs.add(0);

                    Rank2 = name.getString("1");
                    URL2 = url.getString("1");
                    itemListNames.add(Rank2);
                    itemListURLs.add(URL2);
                    itemListIDs.add(1);

                    Rank3 = name.getString("2");
                    URL3 = url.getString("2");
                    itemListNames.add(Rank3);
                    itemListURLs.add(URL3);
                    itemListIDs.add(2);

                    Rank4 = name.getString("3");
                    URL4 = url.getString("3");
                    itemListNames.add(Rank4);
                    itemListURLs.add(URL4);
                    itemListIDs.add(3);

                    Rank5 = name.getString("4");
                    URL5 = url.getString("4");
                    itemListNames.add(Rank5);
                    itemListURLs.add(URL5);
                    itemListIDs.add(4);

                    Rank6 = name.getString("5");
                    URL6 = url.getString("5");
                    itemListNames.add(Rank6);
                    itemListURLs.add(URL6);
                    itemListIDs.add(5);

                    Rank7 = name.getString("6");
                    URL7 = url.getString("6");
                    itemListNames.add(Rank7);
                    itemListURLs.add(URL7);
                    itemListIDs.add(6);

                    Rank8 = name.getString("7");
                    URL8 = url.getString("7");
                    itemListNames.add(Rank8);
                    itemListURLs.add(URL8);
                    itemListIDs.add(7);

                    Rank9 = name.getString("8");
                    URL9 = url.getString("8");
                    itemListNames.add(Rank9);
                    itemListURLs.add(URL9);
                    itemListIDs.add(8);

                    Rank10 = name.getString("9");
                    URL10 = url.getString("9");
                    itemListNames.add(Rank10);
                    itemListURLs.add(URL10);
                    itemListIDs.add(9);

                    Rank11 = name.getString("10");
                    URL11 = url.getString("10");
                    itemListNames.add(Rank11);
                    itemListURLs.add(URL11);
                    itemListIDs.add(10);

                    Rank12 = name.getString("11");
                    URL12 = url.getString("11");
                    itemListNames.add(Rank12);
                    itemListURLs.add(URL12);
                    itemListIDs.add(11);

                    Rank13 = name.getString("12");
                    URL13 = url.getString("12");
                    itemListNames.add(Rank13);
                    itemListURLs.add(URL13);
                    itemListIDs.add(12);

                    Rank14 = name.getString("13");
                    URL14 = url.getString("13");
                    itemListNames.add(Rank14);
                    itemListURLs.add(URL14);
                    itemListIDs.add(13);

                    Rank15 = name.getString("14");
                    URL15 = url.getString("14");
                    itemListNames.add(Rank15);
                    itemListURLs.add(URL15);
                    itemListIDs.add(14);

                    Rank16 = name.getString("15");
                    URL16 = url.getString("15");
                    itemListNames.add(Rank16);
                    itemListURLs.add(URL16);
                    itemListIDs.add(15);

                    Rank17 = name.getString("16");
                    URL17 = url.getString("16");
                    itemListNames.add(Rank17);
                    itemListURLs.add(URL17);
                    itemListIDs.add(16);

                    Rank18 = name.getString("17");
                    URL18 = url.getString("17");
                    itemListNames.add(Rank18);
                    itemListURLs.add(URL18);
                    itemListIDs.add(17);

                    Rank19 = name.getString("18");
                    URL19 = url.getString("18");
                    itemListNames.add(Rank19);
                    itemListURLs.add(URL19);
                    itemListIDs.add(18);

                    Rank20 = name.getString("19");
                    URL20 = url.getString("19");
                    itemListNames.add(Rank20);
                    itemListURLs.add(URL20);
                    itemListIDs.add(19);


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
                exampleList.add(new wikiItem(R.drawable.wiki, itemListNames.get(i), itemListURLs.get(i)));
            }
        }


    }

    public void onBackPressed() {
    }

}