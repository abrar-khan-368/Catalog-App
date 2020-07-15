package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.abrarlohia.dressmaterialcatalog.Models.DownloadUrl;
import com.abrarlohia.dressmaterialcatalog.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.snapshot.StringNode;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class DownloadImageAdapter extends RecyclerView.Adapter<DownloadImageAdapter.DownloadImageViewHolder>{

    private List<String> urls;
    private Context context;

    public DownloadImageAdapter(List<String> urls, Context context) {
        this.urls = urls;
        this.context = context;
        Log.d("URLS FOUND", urls.size()+"");
    }

    @NonNull
    @Override
    public DownloadImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_catalog_images, parent, false);
        return new DownloadImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadImageViewHolder holder, int position) {



        Picasso.with(context)
                .load(urls.get(position))
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    //Empty ViewHolder
    public class DownloadImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public DownloadImageViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.catalog_images_list);

        }
    }

}
