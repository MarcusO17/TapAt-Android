package com.example.tapat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class AdminDashboard extends Fragment {

    public AdminDashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admindashboard, container, false);

        // Initialize buttons
        Button studentButton = view.findViewById(R.id.studentButton);
        Button lecturerButton = view.findViewById(R.id.lecturerButton);
        Button courseButton = view.findViewById(R.id.courseButton);
        Button nfcReaderButton = view.findViewById(R.id.nfcReaderButton);
        Button nfcWriterButton = view.findViewById(R.id.nfcWriterButton);

        // Set click listeners for buttons
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Student button click
                // Replace the fragment with AdminListFragment and pass data
                replaceFragment(AdminList.newInstance("Student"));
            }
        });

        lecturerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Lecturer button click
                // Replace the fragment with AdminListFragment and pass data
                replaceFragment(AdminList.newInstance("Lecturer"));
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Course button click
                // Replace the fragment with AdminListFragment and pass data
                replaceFragment(AdminList.newInstance("Course"));
            }
        });

        nfcReaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle NFC Reader button click
                // Replace the fragment with AdminNFCReaderFragment
                replaceFragment(new AdminNFCReader());
            }
        });

        nfcWriterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle NFC Writer button click
                // Replace the fragment with AdminNFCWriterFragment
                replaceFragment(new AdminNFCwriter());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}


