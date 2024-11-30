package com.example.athena.EntrantAndOrganizerFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.athena.Models.Event;
import com.example.athena.R;
/// Deprecated no longer used

/**
 * DialogFragment that prompts the user to accept or decline an event invitation.
 * It communicates with the calling fragment to handle the user's response.
 */
public class userViewNotisChose extends DialogFragment {
    public interface acceptDeclineListener{
        /**
         * declines the invite
         * @param position the position in eventList
         */
        void declineInvite(int position);
        /**
         * accepts the invite
         * @param position the position in eventList
         */
        void acceptInvite(int position);
    }

    private acceptDeclineListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            listener = (acceptDeclineListener) getTargetFragment();
        } catch (ClassCastException e){
            throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.invited_list_content,null);
        Bundle args = getArguments();
        String eventName = args.getString("name");
        String eventDescription = args.getString("description");
        int pos = args.getInt("pos");
        TextView eventTitle = view.findViewById(R.id.accept_event_title);
        TextView eventDesc = view.findViewById(R.id.accept_discription);
        eventTitle.setText(eventName);
        eventDesc.setText(String.format("you have been invited to %s. Do you wish to accept or decline the invite?", eventDescription));


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setView(view)
                .setTitle("YOU'RE INVITED!")
                .setNeutralButton("Cancel", null)
                .setNegativeButton("Decline",(dialog, which) -> listener.declineInvite(pos))
                .setPositiveButton("Accept", (dialog, which) -> listener.acceptInvite(pos))
                .create();
    }
}
