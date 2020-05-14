package com.example.osrscomrade.news;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osrscomrade.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class NewsTimelineFragment extends Fragment {

    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public NewsTimelineFragment() {
    }

    public static NewsTimelineFragment newInstance() {

        Bundle args = new Bundle();

        NewsTimelineFragment fragment = new NewsTimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_timeline_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView(view);
        new Content().execute();


    }

    private void setUpRecyclerView(@NonNull View view) {
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ParseAdapter(parseItems, getActivity());
        recyclerView.setAdapter(adapter);
    }


    private class Content extends AsyncTask<Void, Void, Void> {


        //Loading animation
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Jsoup
            try {
                //Grab URL
                String url = "https://secure.runescape.com/m=news/archive?oldschool=1";

                //connect
                Document doc = Jsoup.connect(url).get();

                //select correct elements from html
                Elements data = doc.select("article.news-list-article");
                int size = data.size();
                //save data
                Log.d("doc", "doc: " + doc);
                Log.d("data", "data: " + data);
                Log.d("size", "" + size);
                for (int i = 0; i < size; i++) {

                    //IMAGE
                    String imgUrl = data.select("figure.news-list-article__figure")
                            .select("img")
                            .eq(i)
                            .attr("src");

                    //TITLE
                    String title = data.select("h4.news-list-article__title")
                            .select("a")
                            .eq(i)
                            .text();

                    //URL to news article
                    String detailUrl = data.select("figure.news-list-article__figure")
                            .select("a")
                            .eq(i)
                            .attr("href");

                    parseItems.add(new ParseItem(imgUrl, title, detailUrl));
                    Log.d("items", "img: " + imgUrl + " . title: " + title);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


}
