package com.example.tapat.adminfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.tapat.AdminActivity;
import com.example.tapat.AdminNFCReader;
import com.example.tapat.AdminNFCwriter;
import com.example.tapat.R;

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
                Intent readnfcIntent = new Intent(requireContext(), AdminNFCReader.class);
                startActivity(readnfcIntent);
            }
        });

        nfcWriterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent writenfcIntent = new Intent(requireContext(), AdminNFCwriter.class);
                startActivity(writenfcIntent);
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


