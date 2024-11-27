package com.example.athena.EntrantAndOrganizerFragments.eventCreation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class dateDialog extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private String textField;
    private datePickerListener datePickerListener;
    public interface datePickerListener {
        public void onDateSet(String textfield, String date);
    }

    public static dateDialog newInstance(datePickerListener listener) {
        dateDialog dialog = new dateDialog();
        dialog.setDatePickerListener(listener);
        return dialog;
    }

    public datePickerListener getDatePickerListener() {
        return this.datePickerListener;
    }

    public void setDatePickerListener(datePickerListener listener) {
        this.datePickerListener = listener;
    }

    public void notifyDatePickerListener(String textfield, String date) {
        if (this.datePickerListener != null) {
            this.datePickerListener.onDateSet(textfield, date);
        }
    }

    public String getTextField() {
        return this.textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        String selectedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        notifyDatePickerListener(this.getTextField(), selectedDate);
    }
}