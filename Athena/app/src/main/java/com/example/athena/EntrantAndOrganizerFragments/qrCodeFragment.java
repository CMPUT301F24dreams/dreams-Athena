package com.example.athena.EntrantAndOrganizerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.athena.R;

public class qrCodeFragment extends Fragment {

    public qrCodeFragment() {
        // something
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.qr_code_fragment, container, false);

    }
}
