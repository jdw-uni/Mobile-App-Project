package com.example.osrscomrade.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.osrscomrade.R;

import java.util.ArrayList;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> {

    private ArrayList<ParseItem> parseItems;
    private Context context;

    ParseAdapter(ArrayList<ParseItem> parseItems, Context context) {
        this.parseItems = parseItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ParseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }


    private static String removeWords(String word, String remove) {
        return word.replace(remove,"...");
    }

    private static String replaceWords(String word, String replace) {
        return word.replace(replace,"'");
    }

    @Override
    public void onBindViewHolder(@NonNull ParseAdapter.ViewHolder holder, int position) {
        //Set position of each and display text/image
        ParseItem parseItem = parseItems.get(position);
        holder.textView.setText(parseItem.getTitle());
        holder.dateView.setText(parseItem.getDate());
        String remove = "\u0085 read more";
        String replace = "\u0092";
        String newInfo = (removeWords(parseItem.getInfo(), remove));
        holder.infoView.setText(replaceWords(newInfo, replace));
        Glide.with(context).load(parseItem.getImgUrl()).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        TextView dateView;
        TextView infoView;

        ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            textView = view.findViewById(R.id.textView);
            dateView = view.findViewById(R.id.DateView);
            infoView = view.findViewById(R.id.infoView);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
           //Parse selected card view
            int position = getAdapterPosition();
            ParseItem parseItem = parseItems.get(position);

            //get Url from selected position and create new intent to url
            String url = parseItem.getDetailUrl();
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.addCategory(Intent.CATEGORY_BROWSABLE);
            i.setData(Uri.parse(url));
            context.startActivity(i);

        }
    }

    public void setFilter(ArrayList<ParseItem> newList) {
        parseItems = new ArrayList<>();
        parseItems.addAll(newList);
        notifyDataSetChanged();
    }
}
