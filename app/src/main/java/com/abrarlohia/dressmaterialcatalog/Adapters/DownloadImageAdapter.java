package com.abrarlohia.dressmaterialcatalog.Adapters;

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

import java.util.List;

public class DownloadImageAdapter extends FirestoreRecyclerAdapter<CatalogDetails, DownloadImageAdapter.DownloadImageViewHolder> {

    private List<DownloadUrl> urls;

    public DownloadImageAdapter(@NonNull FirestoreRecyclerOptions<CatalogDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DownloadImageViewHolder holder, int position, @NonNull CatalogDetails model) {
        urls = model.getImageLink();
    }

    @NonNull
    @Override
    public DownloadImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public List<DownloadUrl> getImageUrls() {
        return urls;
    }

    //Empty ViewHolder
    public class DownloadImageViewHolder extends RecyclerView.ViewHolder {
        public DownloadImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
