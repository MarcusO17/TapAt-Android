package com.example.tapat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AdminDisplayInfo extends Fragment {

    private static final String ARG_FRAGMENT_TITLE = "fragmentTitle";
    private static final String ARG_BUTTON_NAME = "buttonName";

    public AdminDisplayInfo() {
        // Required empty public constructor
    }

    public static AdminDisplayInfo newInstance(String fragmentTitle, String buttonName) {
        AdminDisplayInfo fragment = new AdminDisplayInfo();
        Bundle args = new Bundle();
        args.putString(ARG_FRAGMENT_TITLE, fragmentTitle);
        args.putString(ARG_BUTTON_NAME, buttonName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admindisplayinfo, container, false);

        // Retrieve the fragment title and button name from arguments
        String fragmentTitle = getArguments().getString(ARG_FRAGMENT_TITLE);
        String buttonName = getArguments().getString(ARG_BUTTON_NAME);

        // Set the fragment title and button name in the TextViews
        TextView fragmentTitleTextView = view.findViewById(R.id.displayFragmentTitleTextView);
        TextView buttonNameTextView = view.findViewById(R.id.displayButtonNameTextView);

        if (fragmentTitleTextView != null) {
            fragmentTitleTextView.setText(fragmentTitle);
        }

        if (buttonNameTextView != null) {
            buttonNameTextView.setText(buttonName);
        }

        // You can customize the display of admin information here

        return view;
    }
}
