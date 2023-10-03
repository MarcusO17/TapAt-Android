package com.example.tapat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AdminCreate extends Fragment {

    private static final String ARG_FRAGMENT_TITLE = "fragmentTitle";

    public AdminCreate() {
        // Required empty public constructor
    }

    public static AdminCreate newInstance(String fragmentTitle) {
        AdminCreate fragment = new AdminCreate();
        Bundle args = new Bundle();
        args.putString(ARG_FRAGMENT_TITLE, fragmentTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admincreate, container, false);

        // Retrieve the fragment title from arguments
        String fragmentTitle = getArguments().getString(ARG_FRAGMENT_TITLE);

        // Set the fragment title in the TextView
        TextView textView = view.findViewById(R.id.createTitleTextView);
        if (textView != null) {
            textView.setText("Add " + fragmentTitle);
        }

        // You can add your UI elements and logic for creating admin data here

        return view;
    }
}
