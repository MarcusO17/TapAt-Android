package com.example.tapat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class UserProfile extends Fragment {

    public UserProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userprofile, container, false);

        // Set the title in the TextView
        TextView textView = view.findViewById(R.id.textViewUserProfile);
        if (textView != null) {
            textView.setText("User Profile");
        }

        // Initialize and display user profile information and UI components here

        return view;
    }
}
