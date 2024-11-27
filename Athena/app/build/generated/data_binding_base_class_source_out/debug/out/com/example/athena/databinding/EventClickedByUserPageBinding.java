// Generated by view binder compiler. Do not edit!
package com.example.athena.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public final class EventClickedByUserPageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView eventDescNonEditList;

  @NonNull
  public final TextView eventDescriptionTextviewList;

  @NonNull
  public final ImageView eventPoster;

  @NonNull
  public final ImageButton imageButton;

  private EventClickedByUserPageBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView eventDescNonEditList, @NonNull TextView eventDescriptionTextviewList,
      @NonNull ImageView eventPoster, @NonNull ImageButton imageButton) {
    this.rootView = rootView;
    this.eventDescNonEditList = eventDescNonEditList;
    this.eventDescriptionTextviewList = eventDescriptionTextviewList;
    this.eventPoster = eventPoster;
    this.imageButton = imageButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static EventClickedByUserPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EventClickedByUserPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.event_clicked_by_user_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EventClickedByUserPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.event_desc_non_edit_list;
      TextView eventDescNonEditList = ViewBindings.findChildViewById(rootView, id);
      if (eventDescNonEditList == null) {
        break missingId;
      }

      id = R.id.event_description_textview_list;
      TextView eventDescriptionTextviewList = ViewBindings.findChildViewById(rootView, id);
      if (eventDescriptionTextviewList == null) {
        break missingId;
      }

      id = R.id.event_poster;
      ImageView eventPoster = ViewBindings.findChildViewById(rootView, id);
      if (eventPoster == null) {
        break missingId;
      }

      id = R.id.imageButton;
      ImageButton imageButton = ViewBindings.findChildViewById(rootView, id);
      if (imageButton == null) {
        break missingId;
      }

      return new EventClickedByUserPageBinding((ConstraintLayout) rootView, eventDescNonEditList,
          eventDescriptionTextviewList, eventPoster, imageButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}