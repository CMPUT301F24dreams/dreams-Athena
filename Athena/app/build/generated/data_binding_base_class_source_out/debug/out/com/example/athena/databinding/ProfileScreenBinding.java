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
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ProfileScreenBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton BackButton;

  @NonNull
  public final ImageView DeletePicture;

  @NonNull
  public final ImageView EditNotfis;

  @NonNull
  public final ImageView EditPicture;

  @NonNull
  public final ImageView EditProfileAll;

  @NonNull
  public final TextView ProfileEmail;

  @NonNull
  public final TextView ProfileNumber;

  @NonNull
  public final TextView appName2;

  @NonNull
  public final TextView bar;

  @NonNull
  public final CircleImageView profileImage;

  @NonNull
  public final TextView profileName;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView textView8;

  private ProfileScreenBinding(@NonNull ConstraintLayout rootView, @NonNull ImageButton BackButton,
      @NonNull ImageView DeletePicture, @NonNull ImageView EditNotfis,
      @NonNull ImageView EditPicture, @NonNull ImageView EditProfileAll,
      @NonNull TextView ProfileEmail, @NonNull TextView ProfileNumber, @NonNull TextView appName2,
      @NonNull TextView bar, @NonNull CircleImageView profileImage, @NonNull TextView profileName,
      @NonNull TextView textView4, @NonNull TextView textView8) {
    this.rootView = rootView;
    this.BackButton = BackButton;
    this.DeletePicture = DeletePicture;
    this.EditNotfis = EditNotfis;
    this.EditPicture = EditPicture;
    this.EditProfileAll = EditProfileAll;
    this.ProfileEmail = ProfileEmail;
    this.ProfileNumber = ProfileNumber;
    this.appName2 = appName2;
    this.bar = bar;
    this.profileImage = profileImage;
    this.profileName = profileName;
    this.textView4 = textView4;
    this.textView8 = textView8;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ProfileScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProfileScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.profile_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProfileScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.BackButton;
      ImageButton BackButton = ViewBindings.findChildViewById(rootView, id);
      if (BackButton == null) {
        break missingId;
      }

      id = R.id.DeletePicture;
      ImageView DeletePicture = ViewBindings.findChildViewById(rootView, id);
      if (DeletePicture == null) {
        break missingId;
      }

      id = R.id.EditNotfis;
      ImageView EditNotfis = ViewBindings.findChildViewById(rootView, id);
      if (EditNotfis == null) {
        break missingId;
      }

      id = R.id.EditPicture;
      ImageView EditPicture = ViewBindings.findChildViewById(rootView, id);
      if (EditPicture == null) {
        break missingId;
      }

      id = R.id.EditProfileAll;
      ImageView EditProfileAll = ViewBindings.findChildViewById(rootView, id);
      if (EditProfileAll == null) {
        break missingId;
      }

      id = R.id.ProfileEmail;
      TextView ProfileEmail = ViewBindings.findChildViewById(rootView, id);
      if (ProfileEmail == null) {
        break missingId;
      }

      id = R.id.ProfileNumber;
      TextView ProfileNumber = ViewBindings.findChildViewById(rootView, id);
      if (ProfileNumber == null) {
        break missingId;
      }

      id = R.id.app_name2;
      TextView appName2 = ViewBindings.findChildViewById(rootView, id);
      if (appName2 == null) {
        break missingId;
      }

      id = R.id.bar;
      TextView bar = ViewBindings.findChildViewById(rootView, id);
      if (bar == null) {
        break missingId;
      }

      id = R.id.profile_image;
      CircleImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.profileName;
      TextView profileName = ViewBindings.findChildViewById(rootView, id);
      if (profileName == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = ViewBindings.findChildViewById(rootView, id);
      if (textView8 == null) {
        break missingId;
      }

      return new ProfileScreenBinding((ConstraintLayout) rootView, BackButton, DeletePicture,
          EditNotfis, EditPicture, EditProfileAll, ProfileEmail, ProfileNumber, appName2, bar,
          profileImage, profileName, textView4, textView8);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}