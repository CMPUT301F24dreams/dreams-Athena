package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.athena.ArrayAdapters.userArrayAdapter;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.User;
import com.example.athena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Fragment for browsing users associated with a particular event.
 * */
public class ProfileBrowseOrg extends Fragment {
    private String deviceID;
    public userDB userDB;
    public eventsDB eventsDB;
    private ArrayList<User> users;
    private ListView listView;
    private ArrayList<String> usersID;
    private String status;
    private String eventID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_view, container, false);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        eventID = bundle.getString("eventID");
        status = bundle.getString("status");
        this.deviceID = bundle.getString("deviceID");
        eventID = bundle.getString("eventID");
        status = bundle.getString("status");
        userDB = new userDB();
        users = new ArrayList<>();
        usersID = new ArrayList<>();
        eventsDB = new eventsDB();

        listView = view.findViewById(R.id.myEventList);

        Task getUserEventList = eventsDB.getEventUserList(eventID,status);
        Task getUserList = userDB.getUserList();
        Task gotInfo = Tasks.whenAll(getUserEventList,getUserList);
        gotInfo.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    userArrayAdapter userAdapter = new userArrayAdapter(getContext(), users);
                    listView.setAdapter(userAdapter);
                    ArrayList<String> listOfUsersID = new ArrayList<>();
                    QuerySnapshot userList = (QuerySnapshot) getUserEventList.getResult();
                    for (Iterator<DocumentSnapshot> it = userList.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        listOfUsersID.add(document.getId());
                    }
                    QuerySnapshot listOfUsers = (QuerySnapshot) getUserList.getResult();
                    for (Iterator<DocumentSnapshot> it = listOfUsers.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        if (listOfUsersID.contains(document.getId())) {
                            String name = document.getString("name");
                            String email = document.getString("email");
                            String phone = document.getString("phone");
                            String imageURL = document.getString("imageURL");
                            User user = new User(name, email, phone, imageURL);
                            users.add(user);
                            usersID.add(document.getId());
                        }
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }
        });


    }
    public void displayChildFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

}

