package com.example.tapat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AdminList extends Fragment {

    private static final String ARG_FRAGMENT_TITLE = "fragmentTitle";

    public AdminList() {
        // Required empty public constructor
    }

    public static AdminList newInstance(String fragmentTitle) {
        AdminList fragment = new AdminList();
        Bundle args = new Bundle();
        args.putString(ARG_FRAGMENT_TITLE, fragmentTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminlist, container, false);

        // Retrieve the fragment title from arguments
        String fragmentTitle = getArguments().getString(ARG_FRAGMENT_TITLE);

        // Set the fragment title in the TextView
        TextView textView = view.findViewById(R.id.textView);
        if (textView != null) {
            textView.setText(fragmentTitle);
        }

        // You can initialize and populate a RecyclerView or other views here to display your list data

        return view;
    }
}

