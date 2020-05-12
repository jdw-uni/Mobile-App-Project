package com.example.osrscomrade.twitter;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.osrscomrade.R;
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
        return inflater.inflate(R.layout.osrs_timeline_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSwipeRefreshLayout(view);
        setUpRecyclerView(view);
        loadUserTimeline();
    }

    //Setup recycler view
    private void setUpRecyclerView(@NonNull View view) {
        userTimelineRecyclerView = view.findViewById(R.id.user_timeline_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);//it should be Vertical only
        userTimelineRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * method to load user timeline over recycler view
     */
    //Load user timeline (@OldSchoolRS)
    private void loadUserTimeline() {
        //Build UserTimeline
        UserTimeline userTimeline = new UserTimeline.Builder()
                //screen name of the user to show tweets for
                .screenName("OldSchoolRS")

                //Whether to include replies. Defaults to false.
                .includeReplies(false)

                //Whether to include re-tweets. Defaults to true.
                .includeRetweets(true)

                //Max number of items to return per request
                .maxItemsPerRequest(50)
                .build();


        //Build adapter for recycler view
        adapter = new TweetTimelineRecyclerViewAdapter.Builder(context)
                //Set the created timeline
                .setTimeline(userTimeline)

                //Action callback to listen when user like/unlike the tweet
                .setOnActionCallback(new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        //Do something on success response
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        //Do something on failure response
                    }
                })
                //Set tweet view style
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build();

        //Finally set the created adapter to recycler view
        userTimelineRecyclerView.setAdapter(adapter);
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
