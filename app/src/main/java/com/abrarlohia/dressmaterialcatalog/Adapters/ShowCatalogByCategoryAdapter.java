package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.abrarlohia.dressmaterialcatalog.R;
import com.abrarlohia.dressmaterialcatalog.ShowCatalogImageForClient;
import com.abrarlohia.dressmaterialcatalog.ShowCatalogImages;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ShowCatalogByCategoryAdapter extends FirestoreRecyclerAdapter<CatalogDetails, ShowCatalogByCategoryAdapter.ShowCatalogByCategoryVH> {

    private Context context;

    public ShowCatalogByCategoryAdapter(@NonNull FirestoreRecyclerOptions<CatalogDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShowCatalogByCategoryVH holder, int position, @NonNull final CatalogDetails model) {
        holder.catalogName.setText(model.getCatalogName());
        holder.costing.setText(model.getCosting());
        holder.categoryName.setText(model.getCatalogCategory());
        Picasso.with(context)
                .load(model.getImageLink().get(0))
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.mainImage);

        holder.mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowCatalogImageForClient.class);
                intent.putExtra("catalog-name", model.getCatalogName());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ShowCatalogByCategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_catalog, parent, false);
        return new ShowCatalogByCategoryVH(view);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ShowCatalogByCategoryVH extends RecyclerView.ViewHolder {

        public TextView catalogName, costing;
        public TextView categoryName;
        public ImageView mainImage;

        public ShowCatalogByCategoryVH(@NonNull View itemView) {
            super(itemView);

            catalogName = itemView.findViewById(R.id.catalog_name);
            costing = itemView.findViewById(R.id.costing);
            categoryName = itemView.findViewById(R.id.category_name);
            mainImage = itemView.findViewById(R.id.main_image);

        }
    }

}
