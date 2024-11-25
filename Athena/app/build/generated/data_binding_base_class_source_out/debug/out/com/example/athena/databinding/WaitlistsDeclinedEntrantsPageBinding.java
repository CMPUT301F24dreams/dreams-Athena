// Generated by view binder compiler. Do not edit!
package com.example.athena.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

public final class WaitlistsDeclinedEntrantsPageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ListView declinedEntrantsListview;

  @NonNull
  public final TextView declinedEntrantsTitleNonEdit;

  private WaitlistsDeclinedEntrantsPageBinding(@NonNull ConstraintLayout rootView,
      @NonNull ListView declinedEntrantsListview, @NonNull TextView declinedEntrantsTitleNonEdit) {
    this.rootView = rootView;
    this.declinedEntrantsListview = declinedEntrantsListview;
    this.declinedEntrantsTitleNonEdit = declinedEntrantsTitleNonEdit;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static WaitlistsDeclinedEntrantsPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static WaitlistsDeclinedEntrantsPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.waitlists_declined_entrants_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static WaitlistsDeclinedEntrantsPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.declined_entrants_listview;
      ListView declinedEntrantsListview = ViewBindings.findChildViewById(rootView, id);
      if (declinedEntrantsListview == null) {
        break missingId;
      }

      id = R.id.declined_entrants_title_non_edit;
      TextView declinedEntrantsTitleNonEdit = ViewBindings.findChildViewById(rootView, id);
      if (declinedEntrantsTitleNonEdit == null) {
        break missingId;
      }

      return new WaitlistsDeclinedEntrantsPageBinding((ConstraintLayout) rootView,
          declinedEntrantsListview, declinedEntrantsTitleNonEdit);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
