package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.content.Context;
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
import com.shashank.sony.fancytoastlib.FancyToast;

public class CategoryAdapter extends FirestoreRecyclerAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    private Context context;

    public CategoryAdapter(@NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position, @NonNull Category model) {
        String name = model.getName();
        if(name.length() > 8)
            name = name.substring(0, 8) + "...";

        holder.categoryText.setText(name);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSnapshots().getSnapshot(position).getReference().delete();
                FancyToast.makeText(context, "Category Deleted", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
        });

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_category_admin, parent, false);
        return new CategoryViewHolder(view);
    }

    public void setContext(Context context) { this.context = context; }

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
