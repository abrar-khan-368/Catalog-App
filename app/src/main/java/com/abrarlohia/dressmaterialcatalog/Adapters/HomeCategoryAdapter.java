package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Models.Category;
import com.abrarlohia.dressmaterialcatalog.R;
import com.abrarlohia.dressmaterialcatalog.ShowCatalogByCategory;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class HomeCategoryAdapter extends FirestoreRecyclerAdapter<Category, HomeCategoryAdapter.HomeCategoryViewHolder> {

    private Context context;

    public HomeCategoryAdapter(@NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeCategoryViewHolder holder, int position, @NonNull final Category model) {
        holder.categoryName.setText(model.getName());
        holder.categoryGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowCatalogByCategory.class);
                intent.putExtra("category", model.getName());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_category_home, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryName;
        public LinearLayout categoryGrid;

        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryGrid = itemView.findViewById(R.id.category_grid);
        }
    }

}
