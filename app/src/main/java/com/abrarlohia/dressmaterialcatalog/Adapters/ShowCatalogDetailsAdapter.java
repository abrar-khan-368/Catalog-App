package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.abrarlohia.dressmaterialcatalog.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ShowCatalogDetailsAdapter extends FirestoreRecyclerAdapter<CatalogDetails, ShowCatalogDetailsAdapter.ShowCatalogDetailViewHolder> {

    public ShowCatalogDetailsAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShowCatalogDetailViewHolder holder, int position, @NonNull CatalogDetails model) {
        holder.catalogName.setText(model.getCatalogName());
        holder.costing.setText(model.getCosting());
    }

    @NonNull
    @Override
    public ShowCatalogDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_catalog, parent, false);
        return new ShowCatalogDetailViewHolder(view);
    }


    public  class ShowCatalogDetailViewHolder extends RecyclerView.ViewHolder {

        public TextView catalogName, costing;

        public ShowCatalogDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            catalogName = itemView.findViewById(R.id.catalog_name);
            costing = itemView.findViewById(R.id.costing);

        }
    }

}
