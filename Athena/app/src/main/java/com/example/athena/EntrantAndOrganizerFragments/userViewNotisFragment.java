package com.example.athena.EntrantAndOrganizerFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.athena.Models.Event;
import com.example.athena.R;
import com.example.athena.WaitList.UserInviteArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class userViewNotisFragment extends Fragment implements userViewNotisChose.acceptDeclineListener {

    private ListView invites;
    private UserInviteArrayAdapter adapter;
    private ArrayList<Event> events;
    private String userID;

    @Override
    public void acceptInvite(int position) {
        adapter.getItem(position).setEventName("YIPPE");
        adapter.notifyDataSetChanged();
//        events.get(position).moveUser(userID,"accepted");
    }

    @Override
    public void declineInvite(int position) {
        adapter.getItem(position).setEventName("AWWW");
        adapter.notifyDataSetChanged();
//        events.get(position).moveUser(userID,"declined");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_my_notis_fragment, container, false);
        invites = view.findViewById(R.id.user_notifications_listview);
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;


        }
public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);
//    Bundle args = getArguments();
//    userID = args.getString("userID");
    events = new ArrayList<Event>();
    events.add(new Event("someEvent","description", "12213", Boolean.FALSE, 21, "we", "2020","2021","2023"));
    events.add(new Event("someEvent","description", "12213", Boolean.FALSE, 21, "we", "2020","2021","2023"));
    events.add(new Event("someEvent","description", "12213", Boolean.FALSE, 21, "we", "2020","2021","2023"));
    events.add(new Event("someEvent","description", "12213", Boolean.FALSE, 21, "we", "2020","2021","2023"));
    adapter = new UserInviteArrayAdapter(getContext(),events);
    invites.setAdapter(adapter);


    invites.setClickable(Boolean.TRUE);
    invites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Bundle bundle = new Bundle();
            bundle.putString("name",events.get(position).getEventName());
            bundle.putString("description", events.get(position).getDescription());
            bundle.putInt("pos",position);
            showDialog(bundle);

        }
    });


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

    private void showDialog(Bundle bundle) {
        FragmentManager fm = getParentFragmentManager();
        userViewNotisChose frag = new userViewNotisChose();
        frag.setArguments(bundle);
        frag.setTargetFragment(this,0);
        frag.show(fm,"accept_decline_dialog");
    }
}

