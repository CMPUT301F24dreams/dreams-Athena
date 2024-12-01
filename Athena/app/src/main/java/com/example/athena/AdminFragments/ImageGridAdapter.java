package com.example.athena.AdminFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.athena.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ViewHolder> {

    // lists to store urls and IDs
    private final Context context;
    private final List<String> userImages;
    private final List<String> eventImages;
    private final List<String> userDocumentIds;
    private final List<String> eventDocumentIds;

    
    public ImageGridAdapter(Context context, List<String> userImages, List<String> eventImages,
                            List<String> userDocumentIds, List<String> eventDocumentIds) {
        this.context = context;
        this.userImages = userImages;
        this.eventImages = eventImages;
        this.userDocumentIds = userDocumentIds;
        this.eventDocumentIds = eventDocumentIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl;
        String label;

        // Determine if the item is from Users or Events
        if (position < userImages.size()) {
            imageUrl = userImages.get(position);
            label = "User Image";
        } else {
            int eventPosition = position - userImages.size(); // Offset for event images
            imageUrl = eventImages.get(eventPosition);
            label = "Event Image";
        }

        // Load image
        Glide.with(context).load(imageUrl).into(holder.imageView);

        // Set label
        holder.labelView.setText(label);

        // Click on image for deletion
        holder.imageView.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Delete Image")
                    .setMessage("Are you sure you want to delete this image?")
                    .setPositiveButton("Yes", (dialog, which) -> deleteImage(position))
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void deleteImage(int position) {
        if (position < userImages.size()) { // Users and Events are split
            // User image deletion
            String documentId = userDocumentIds.get(position);
            FirebaseFirestore.getInstance().collection("Users")
                    .document(documentId)
                    .update("imageURL", "")
                    .addOnSuccessListener(aVoid -> {
                        userImages.remove(position);
                        userDocumentIds.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "User image removed successfully", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Event image deletion
            int eventPosition = position - userImages.size();
            String documentId = eventDocumentIds.get(eventPosition);
            FirebaseFirestore.getInstance().collection("Events")
                    .document(documentId)
                    .update("imageURL", "")
                    .addOnSuccessListener(aVoid -> {
                        eventImages.remove(eventPosition);
                        eventDocumentIds.remove(eventPosition);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Event image removed successfully", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    public int getItemCount() {
        return userImages.size() + eventImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView labelView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gridImageView);
            labelView = itemView.findViewById(R.id.imageLabel);
        }
    }
}