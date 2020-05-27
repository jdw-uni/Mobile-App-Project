package com.example.osrscomrade.combat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.osrscomrade.R;
import com.example.osrscomrade.ServiceHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Objects;

import static java.lang.StrictMath.floor;

public class combat_level extends AppCompatActivity {

    private ProgressDialog pDialog;

    TextView textResult;
    String username, jsonURL;
    Button buttonSearch;

    String stringattackValue, stringstrengthValue, stringdefenceValue, stringhitpointsValue,
            stringrangedValue, stringmagicValue, stringprayerValue;

    EditText playerName, attackValue, strengthValue, defenceValue, hitpointsValue,
             rangedValue, magicValue, prayerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combat_level);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.combatlevelstring);

        textResult = findViewById(R.id.textResult);
        playerName = findViewById(R.id.playerName);
        attackValue = findViewById(R.id.attackValue);
        strengthValue = findViewById(R.id.strengthValue);
        defenceValue = findViewById(R.id.defenceValue);
        hitpointsValue = findViewById(R.id.hitpointsValue);
        rangedValue = findViewById(R.id.rangedValue);
        magicValue = findViewById(R.id.magicValue);
        prayerValue = findViewById(R.id.prayerValue);

        buttonSearch = findViewById(R.id.buttonSearch);

        buttonSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

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
            if (playerName.getText().toString().trim().length() == 0) {
                Toast.makeText(getApplicationContext(), "Please enter an item", Toast.LENGTH_SHORT).show();
            } else {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(buttonSearch.getWindowToken(), 0);

                username = playerName.getText().toString();
                username = username.toLowerCase();

                new combat_level.GetData().execute();

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
            pDialog = new ProgressDialog(combat_level.this);
            pDialog.setMessage("Searching Hiscores...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        //During the background operation
        @Override
        protected Void doInBackground(Void... arg0) {

            // URL to get JSON
            try {
                jsonURL = "https://bustus.000webhostapp.com/?username=" + URLEncoder.encode(username, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Creating service handler class
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(jsonURL, ServiceHandler.GET);

            //Log for debug, can be deleted later
            Log.d("Response: ", "> " + jsonStr);

            //Check to see if JSON string is not empty
            if (jsonStr != null) {
                try {
                    //Create a new JSON object
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting the 'attack' object from the JSON object
                    JSONObject attack = jsonObj.getJSONObject("attack");

                    stringattackValue = attack.getString("level");

                    // Getting the 'defence' object from the JSON object
                    JSONObject defence = jsonObj.getJSONObject("defence");

                    stringdefenceValue = defence.getString("level");

                    // Getting the 'strength' object from the JSON object
                    JSONObject strength = jsonObj.getJSONObject("strength");

                    stringstrengthValue = strength.getString("level");

                    // Getting the 'constitution' object from the JSON object
                    JSONObject hitpoints = jsonObj.getJSONObject("hitpoints");

                    stringhitpointsValue = hitpoints.getString("level");

                    // Getting the 'ranged' object from the JSON object
                    JSONObject ranged = jsonObj.getJSONObject("ranged");

                    stringrangedValue = ranged.getString("level");

                    // Getting the 'prayer' object from the JSON object
                    JSONObject prayer = jsonObj.getJSONObject("prayer");

                    stringprayerValue = prayer.getString("level");

                    // Getting the 'magic' object from the JSON object
                    JSONObject magic = jsonObj.getJSONObject("magic");

                    stringmagicValue = magic.getString("level");


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

            if(stringattackValue != null) {

                attackValue.setText(stringattackValue);
                strengthValue.setText(stringstrengthValue);
                defenceValue.setText(stringdefenceValue);
                hitpointsValue.setText(stringhitpointsValue);
                rangedValue.setText(stringrangedValue);
                magicValue.setText(stringmagicValue);
                prayerValue.setText(stringprayerValue);

            }else {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(getApplicationContext(), username + " was not found in the Overall table", Toast.LENGTH_SHORT).show();
            }

            //After we have obtained the JSON data
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    public void calculateResults(View view)
    {

        Double Base = 0.25 * (Double.parseDouble(defenceValue.getText().toString()) + Double.parseDouble(hitpointsValue.getText().toString()) + floor(Double.parseDouble(prayerValue.getText().toString()) ) / 2);

        Double Melee = 0.325 * (Double.parseDouble(attackValue.getText().toString()) + Double.parseDouble(strengthValue.getText().toString()));

        Double Range = 0.325 * (floor(3 * Double.parseDouble(rangedValue.getText().toString()) / 2 ));

        Double Magic = 0.325 * (floor(3 * Double.parseDouble(magicValue.getText().toString()) / 2 ));

        Double result1 = (Base + Double.max(Melee, Double.max(Range, Magic)));

        DecimalFormat format = new DecimalFormat("0.##");

        textResult.setText("Your combat level is " + format.format(result1));

    }

    public void onBackPressed() {
        finish();
    }

}
