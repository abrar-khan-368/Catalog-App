package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.Models.Category;
import com.abrarlohia.dressmaterialcatalog.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CategoryAdapter extends FirestoreRecyclerAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter(@NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull Category model) {
        String name = model.getName();
        if(name.length() > 8)
            name = name.substring(0, 8) + "...";

        holder.categoryText.setText(name);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_category_admin, parent, false);
        return new CategoryViewHolder(view);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryText;
        public ImageButton editButton;
        public ImageButton deleteButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.category_name);
            editButton = itemView.findViewById(R.id.edit_category);
            deleteButton = itemView.findViewById(R.id.remove_category);
        }
    }

}
