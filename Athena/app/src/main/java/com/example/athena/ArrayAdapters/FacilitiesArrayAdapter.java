package com.example.athena.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.athena.Models.Event;
import com.example.athena.Models.Facility;
import com.example.athena.Models.User;
import com.example.athena.R;

import java.util.ArrayList;

/**
 * ArrayAdapter for displaying a list of facilities.
 */
public class FacilitiesArrayAdapter extends ArrayAdapter<Facility> {

    /**
     * Constructor for the FacilitiesArrayAdapter.
     *
     * @param context The context in which the adapter is used.
     * @param facilities The list of facilities to display.
     */
    public FacilitiesArrayAdapter(Context context, ArrayList<Facility> facilities) {
        super(context, 0, facilities);
    }

    /**
     * Provides a view for an AdapterView.
     *
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.facility_list_layout, parent, false);
        } else {
            view = convertView;
        }
        Facility facility = getItem(position);
        TextView facilityName = view.findViewById(R.id.facility_name_in_list);
        TextView facilityLocation = view.findViewById(R.id.facility_location_in_list);
        TextView facilityOrganizer = view.findViewById(R.id.organizer_id_in_list);
        TextView facilityID = view.findViewById(R.id.facility_id_in_list);

        assert facility != null;
        facilityName.setText(facility.getFacilityName());
        facilityLocation.setText(facility.getFacilityLocation());
        facilityOrganizer.setText(facility.getOrganizer());
        facilityID.setText(facility.getFacilityID());

        return view;
    }
}
