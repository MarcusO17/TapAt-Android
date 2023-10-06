package com.example.tapat.adminfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tapat.R;

public class AdminNFCReader extends Fragment {

    public AdminNFCReader() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminnfcreader, container, false);

        // Set the title in the TextView
        TextView textView = view.findViewById(R.id.textViewNFCReader);
        if (textView != null) {
            textView.setText("AdminNFCReader");
        }

        // Initialize and handle NFC Reader related UI components and functionality here

        return view;
    }
}
