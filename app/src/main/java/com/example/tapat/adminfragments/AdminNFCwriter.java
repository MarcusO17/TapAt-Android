package com.example.tapat.adminfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tapat.R;

public class AdminNFCwriter extends Fragment {

    public AdminNFCwriter() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminnfcwriter, container, false);

        // Set the title in the TextView
        TextView textView = view.findViewById(R.id.textViewNFCWriter);
        if (textView != null) {
            textView.setText("AdminNFCWriter");
        }

        // Initialize and handle NFC Writer related UI components and functionality here

        return view;
    }
}
