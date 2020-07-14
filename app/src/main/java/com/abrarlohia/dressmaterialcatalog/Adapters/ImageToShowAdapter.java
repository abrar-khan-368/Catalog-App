package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.abrarlohia.dressmaterialcatalog.Models.DownloadUrl;
import com.abrarlohia.dressmaterialcatalog.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageToShowAdapter extends FirestoreRecyclerAdapter<CatalogDetails, ImageToShowAdapter.ImageToShowViewHolder> {

    private List<String> urls;
    private Context context;

    public ImageToShowAdapter(@NonNull FirestoreRecyclerOptions<CatalogDetails> options) {
        super(options);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ImageToShowViewHolder holder, int position, @NonNull CatalogDetails model) {
        urls = model.getImageLink();
        Picasso.with(context)
                .load(urls.get(position))
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.catalogImg);
    }

    @NonNull
    @Override
    public ImageToShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_catalog_images, parent, false);
        return new ImageToShowViewHolder(view);
    }

    public class ImageToShowViewHolder extends RecyclerView.ViewHolder {

        public ImageView catalogImg;

        public ImageToShowViewHolder(@NonNull View itemView) {
            super(itemView);

            catalogImg = itemView.findViewById(R.id.catalog_images_list);

        }
    }

}
