package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Models.CatalogDetails;
import com.abrarlohia.dressmaterialcatalog.R;
import com.abrarlohia.dressmaterialcatalog.ShowCatalogImages;
import com.abrarlohia.dressmaterialcatalog.UpdateCatalog;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;


public class ShowCatalogDetailsAdapter extends FirestoreRecyclerAdapter<CatalogDetails, ShowCatalogDetailsAdapter.ShowCatalogDetailViewHolder> {

    private Context context;

    public ShowCatalogDetailsAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShowCatalogDetailViewHolder holder, int position, @NonNull final CatalogDetails model) {
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
                Intent intent = new Intent(context, ShowCatalogImages.class);
                intent.putExtra("catalog-name", model.getCatalogName());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, UpdateCatalog.class);
                intent.putExtra("catalog-name", model.getCatalogName());
                intent.putExtra("catalog-category", model.getCatalogName());
                intent.putExtra("catalog-costing", model.getCatalogName());
                context.startActivity(intent);
                return false;
            }
        });

    }

    @NonNull
    @Override
    public ShowCatalogDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_catalog, parent, false);
        return new ShowCatalogDetailViewHolder(view);
    }

    public void deleteDocument(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public  class ShowCatalogDetailViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView catalogName, costing;
        public TextView categoryName;
        public ImageView mainImage;

        public ShowCatalogDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            catalogName = itemView.findViewById(R.id.catalog_name);
            costing = itemView.findViewById(R.id.costing);
            categoryName = itemView.findViewById(R.id.category_name);
            mainImage = itemView.findViewById(R.id.main_image);

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            menu.add(0, itemView.getId(), 0, "Edit");
            menu.add(0, itemView.getId(), 0, "Remove");
        }



    }

}
