/*
 * This fragment is responsible for displaying a list of events to the admin in the application.
 * It retrieves event data from Firebase and populates a ListView with event details such as event name, image, and ID.
 */

package com.example.athena.AdminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athena.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class browseAppImages extends Fragment {

    private RecyclerView recyclerView;
    private imageGridAdapter adapter;
    private List<String> userImages = new ArrayList<>();
    private List<String> eventImages = new ArrayList<>();
    private List<String> userDocumentIds = new ArrayList<>();
    private List<String> eventDocumentIds = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_view_images, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3)); // 3 columns

        // Pass both lists to the adapter
        adapter = new imageGridAdapter(getContext(), userImages, eventImages, userDocumentIds, eventDocumentIds);
        recyclerView.setAdapter(adapter);

        // Load images
        loadUserImages();
        loadEventImages();

        return view;
    }

    private void loadUserImages() {
        FirebaseFirestore.getInstance().collection("Users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String imageUrl = doc.getString("imageURL");
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            userImages.add(imageUrl);
                            userDocumentIds.add(doc.getId());
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void loadEventImages() {
        FirebaseFirestore.getInstance().collection("Events")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String imageUrl = doc.getString("imageURL");
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            eventImages.add(imageUrl);
                            eventDocumentIds.add(doc.getId());
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}