package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.athena.ArrayAdapters.EventArrayAdapter;
import com.example.athena.Firebase.eventsDB;
import com.example.athena.Firebase.userDB;
import com.example.athena.Models.Event;
import com.example.athena.Interfaces.displayFragments;
import com.example.athena.R;
import com.example.athena.WaitList.UserInviteArrayAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class userViewAttendingEventsFragment extends Fragment implements userViewNotisChose.acceptDeclineListener {

    private ListView invites;
    private UserInviteArrayAdapter adapter;
    private ArrayList<Event> events;
    private String deviceID;
    private userDB userDB;
    private eventsDB eventsDB;
    @Override
    public void acceptInvite(int position) {
        events.get(position).moveUser(deviceID, "accepted");
        eventsDB.changeStatusAccepted(events.get(position).getEventID(), deviceID);
        userDB.changeEventStatusAccepted(events.get(position).getEventID(), deviceID);
        adapter.remove(events.get(position));
        adapter.notifyDataSetChanged();

    }
    @Override
    public void declineInvite(int position) {
        Log.d("updateEvent", "onItemClick: " + events.get(position).getWaitList().getInvited());
        events.get(position).moveUser(deviceID, "declined");
        eventsDB.changeStatusDeclined(events.get(position).getEventID(), deviceID);
        userDB.changeEventStatusDeclined(events.get(position).getEventID(), deviceID);
        adapter.remove(events.get(position));
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.user_my_notis_fragment, container, false);
        invites = view.findViewById(R.id.user_notifications_listview);
        Bundle bundle = getArguments();
        assert bundle != null;
        this.deviceID = bundle.getString("deviceID");
        super.onCreate(savedInstanceState);
        ///Inflates the layout for the fragment
        return view;
    }
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        userDB = new userDB();
        eventsDB = new eventsDB();
        events = new ArrayList<>();

        Task getUserEvents = userDB.getUserEvents(deviceID);
        Task getEventList = eventsDB.getEventsList();

        Task eventsLoaded = Tasks.whenAll(getUserEvents, getEventList);
        eventsLoaded.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                adapter = new UserInviteArrayAdapter(getContext(), events);
                invites.setAdapter(adapter);


                if (task.isSuccessful()) {
                    QuerySnapshot userEvents = (QuerySnapshot) getUserEvents.getResult();
                    List<String> userEventList = new ArrayList<>();

                    for (Iterator<DocumentSnapshot> it = userEvents.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        if(Objects.equals(document.getString("status"), "invited")) {
                            userEventList.add(document.getId());
                        }
                    }

                    QuerySnapshot eventsList = (QuerySnapshot) getEventList.getResult();
                    for (Iterator<DocumentSnapshot> it = eventsList.getDocuments().iterator(); it.hasNext(); ) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                        if (userEventList.contains(document.getId())) {
                            String eventName = document.getString("eventName");
                            String imageURL = document.getString("imageURL");
                            String eventID = document.getString("eventID");
                            Event currentEvent = new Event(eventName, imageURL, eventID);
                            events.add(currentEvent);
                        }
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Exception e = task.getException();
                }
            }
        });
//    Bundle args = getArguments();
//    userID = args.getString("userID");

        invites.setClickable(Boolean.TRUE);
        invites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task getEventUserList = eventsDB.getEventUserList(events.get(position).getEventID());
                Task gotEventUserList = Tasks.whenAll(getEventUserList);
                gotEventUserList.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        ArrayList<String> accepted = new ArrayList<>();
                        ArrayList<String> declined = new ArrayList<>();
                        ArrayList<String> pending = new ArrayList<>();
                        ArrayList<String> invited = new ArrayList<>();
                        if(task.isSuccessful()){

                            QuerySnapshot userList = (QuerySnapshot) getEventUserList.getResult();
                            for (Iterator<DocumentSnapshot> it = userList.getDocuments().iterator(); it.hasNext(); ) {
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) it.next();
                                String status = document.getString("status");
                                if(Objects.equals(status, "invited")){
                                    invited.add(document.getId());
                                }
                                if(Objects.equals(status, "pending")){
                                    pending.add(document.getId());
                                }
                                if(Objects.equals(status, "accepted")){
                                    accepted.add(document.getId());
                                }
                                if(Objects.equals(status, "declined")){
                                    declined.add(document.getId());
                                }
                            }

                            events.get(position).getWaitList().setInvited(invited);
                            events.get(position).getWaitList().setWaiting(pending);
                            events.get(position).getWaitList().setAccepted(accepted);
                            events.get(position).getWaitList().setDeclined(declined);
                        } else {
                            Exception e = task.getException();
                        }
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString("name",events.get(position).getEventName());
                bundle.putString("description", events.get(position).getDescription());
                bundle.putInt("pos",position);
                showDialog(bundle);
            }
        });


    }
    private void showDialog(Bundle bundle) {
        FragmentManager fm = getParentFragmentManager();
        userViewNotisChose frag = new userViewNotisChose();
        frag.setArguments(bundle);
        frag.setTargetFragment(this,0);
        frag.show(fm,"accept_decline_dialog");
    }
}

