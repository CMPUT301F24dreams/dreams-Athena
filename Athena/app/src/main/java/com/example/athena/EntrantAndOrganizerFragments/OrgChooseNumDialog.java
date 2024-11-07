package com.example.athena.EntrantAndOrganizerFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.athena.R;

public class OrgChooseNumDialog extends DialogFragment {

    interface numOfEntListener{
        void choseEntrants(int num);
    }

    private numOfEntListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            listener = (numOfEntListener) getTargetFragment();
        } catch (ClassCastException e){
            throw new ClassCastException("Calling Fragment must implement numOfEntListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.choose_num_dialog,null);
        EditText numEntrant = view.findViewById(R.id.num_entrants_input_dialog);

        int num = Integer.parseInt(numEntrant.getText().toString());
        if(num == Integer.parseInt(null)){
            num = 0;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        int finalNum = num;
        return builder.setView(view)
                .setTitle("Chose Number of Entrants")
                .setNeutralButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) -> listener.choseEntrants(finalNum))
                .create();
    }
}

