package com.example.osrscomrade.twitter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.osrscomrade.R;
import com.example.osrscomrade.news.NewsTimelineFragment;
import com.example.osrscomrade.wiki.wikiMain;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;


public class OsrsTimelineFragment extends Fragment {

    private Context context;
    private RecyclerView userTimelineRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar2;
    private ProgressDialog pDialog;
    private TweetTimelineRecyclerViewAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public OsrsTimelineFragment() {
    }

    public static OsrsTimelineFragment newInstance() {

        Bundle args = new Bundle();

        OsrsTimelineFragment fragment = new OsrsTimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.twitter_timeline_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Content().execute();
        setUpSwipeRefreshLayout(view);
        setUpRecyclerView(view);


        // loadUserTimeline();
    }

    //Setup recycler view
    private void setUpRecyclerView(@NonNull View view) {
        //progressBar2 = view.findViewById(R.id.progressBar2);
        userTimelineRecyclerView = view.findViewById(R.id.user_timeline_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);//it should be Vertical only
        userTimelineRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("StaticFieldLeak")
    private class Content extends AsyncTask<Void, Void, Void> {

        //Loading animation
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Finding Tweets...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            userTimelineRecyclerView.setAdapter(adapter);
            //After we have obtained the JSON data
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


        }

        //Load user timeline (@OldSchoolRS)
        protected Void doInBackground(Void... voids) {
            //Build UserTimeline
            try {
                UserTimeline userTimeline = new UserTimeline.Builder()
                        //screen name of the user to show tweets for
                        .screenName("OldSchoolRS")

                        //Whether to include replies. Defaults to false.
                        .includeReplies(false)


                        //Whether to include re-tweets. Defaults to true.
                        .includeRetweets(true)

                        //Max number of items to return per request
                        .maxItemsPerRequest(1000)
                        .build();


                //Build adapter for recycler view
                adapter = new TweetTimelineRecyclerViewAdapter.Builder(context)
                        //Set the created timeline
                        .setTimeline(userTimeline)

                        //Action callback to listen when user like/unlike the tweet
                        .setOnActionCallback(new Callback<Tweet>() {
                            @Override
                            public void success(Result<Tweet> result) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void failure(TwitterException exception) {
                                Toast.makeText(getActivity(), "No Tweets Found", Toast.LENGTH_SHORT).show();
                            }
                        })
                        //Set tweet view style
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();

                //Finally set the created adapter to recycler view

            } catch (Exception e) {
                e.printStackTrace();
            }
            return  null;
        }

    }



    //Swipe refresh layout
    private void setUpSwipeRefreshLayout(View view) {

        //Find the id of swipe refresh layout
        swipeRefreshLayout = view.findViewById(R.id.user_swipe_refresh_layout);

        //Implement refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //Return if adapter is null
                if (adapter == null)
                    return;

                //Make set refreshing true
                swipeRefreshLayout.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {

                        //On success response make refreshing false
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(context, "Tweets refreshed.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException exception) {

                        // Toast or some other action
                        Toast.makeText(context, "Failed to refresh tweets.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
