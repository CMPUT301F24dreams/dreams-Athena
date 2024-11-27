// Generated by view binder compiler. Do not edit!
package com.example.athena.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.athena.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CreateFacilityBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView UIBox;

  @NonNull
  public final Button createFacilityButton;

  @NonNull
  public final EditText facilityLocationTextView;

  @NonNull
  public final EditText facilityNameEditText;

  private CreateFacilityBinding(@NonNull ConstraintLayout rootView, @NonNull TextView UIBox,
      @NonNull Button createFacilityButton, @NonNull EditText facilityLocationTextView,
      @NonNull EditText facilityNameEditText) {
    this.rootView = rootView;
    this.UIBox = UIBox;
    this.createFacilityButton = createFacilityButton;
    this.facilityLocationTextView = facilityLocationTextView;
    this.facilityNameEditText = facilityNameEditText;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CreateFacilityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CreateFacilityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.create_facility, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CreateFacilityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.UIBox;
      TextView UIBox = ViewBindings.findChildViewById(rootView, id);
      if (UIBox == null) {
        break missingId;
      }

      id = R.id.createFacilityButton;
      Button createFacilityButton = ViewBindings.findChildViewById(rootView, id);
      if (createFacilityButton == null) {
        break missingId;
      }

      id = R.id.facility_location_textView;
      EditText facilityLocationTextView = ViewBindings.findChildViewById(rootView, id);
      if (facilityLocationTextView == null) {
        break missingId;
      }

      id = R.id.facility_name_editText;
      EditText facilityNameEditText = ViewBindings.findChildViewById(rootView, id);
      if (facilityNameEditText == null) {
        break missingId;
      }

      return new CreateFacilityBinding((ConstraintLayout) rootView, UIBox, createFacilityButton,
          facilityLocationTextView, facilityNameEditText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}