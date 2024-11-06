package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.athena.Interfaces.displayFragments;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.athena.Interfaces.Model;
import com.example.athena.R;
import com.example.athena.databinding.OrganizerMyEventsViewBinding;


public class viewMyCreatedEventsFragment extends Fragment{


    OrganizerMyEventsViewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.organizer_my_events_view, container, false);

        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;

    }




    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //This may or not be useless depending on our decided implementation
        /*
        binding.returnHomeEntrantAndOrganizers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_layout, new entrantAndOrganizerHomeFragment());
                transaction.commit();
            }
        });
        */
    }

}