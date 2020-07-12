package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Models.DownloadUrl;
import com.abrarlohia.dressmaterialcatalog.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageToShowAdapter extends RecyclerView.Adapter<ImageToShowAdapter.ImageToShowViewHolder> {

    private List<DownloadUrl> urls;
    private Context context;

    public ImageToShowAdapter(List<DownloadUrl> urls) {
        this.urls = urls;
    }

    @NonNull
    @Override
    public ImageToShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_catalog_images, parent, false);
        return new ImageToShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageToShowViewHolder holder, int position) {
        String urlLink = urls.get(position).getUrl();
        Log.d("URLLINK", urlLink);
        Picasso.with(context)
                .load(urlLink)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.catalogImg);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ImageToShowViewHolder extends RecyclerView.ViewHolder {

        public ImageView catalogImg;

        public ImageToShowViewHolder(@NonNull View itemView) {
            super(itemView);

            catalogImg = itemView.findViewById(R.id.catalog_images_list);

        }
    }

}
