package com.abrarlohia.dressmaterialcatalog.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abrarlohia.dressmaterialcatalog.R;

import org.w3c.dom.Text;

import java.util.List;

public class ImageDisplayAdapter extends RecyclerView.Adapter<ImageDisplayAdapter.ImageViewHolder> {

    public List<String> fileNameList;

    public ImageDisplayAdapter(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_selected_images_display, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String fileName = fileNameList.get(position);
        holder.fileNameText.setText(fileName);
    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ImageView imageView;
        public TextView fileNameText;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            imageView = itemView.findViewById(R.id.selected_image);
            fileNameText = itemView.findViewById(R.id.image_name);

        }
    }

}
