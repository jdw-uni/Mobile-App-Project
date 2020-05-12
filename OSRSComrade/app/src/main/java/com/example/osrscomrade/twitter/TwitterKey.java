package com.example.osrscomrade.twitter;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

public class TwitterKey extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initiate Twitter config
        //https://developer.twitter.com/en/apps
        com.twitter.sdk.android.core.TwitterConfig config = new com.twitter.sdk.android.core.TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))

                //pass Twitter API Key and Secret
                .twitterAuthConfig(new TwitterAuthConfig("h835hmmGNYsKqFxEts6keGvfC", "kefJbnzbspZksOxMgHFUBChHssSS3205SLGW2R2nWMKndJzLzW"))
                .debug(true)
                .build();
        Twitter.initialize(config);

    }
}
