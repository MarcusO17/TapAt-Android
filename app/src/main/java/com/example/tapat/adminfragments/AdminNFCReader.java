package com.example.tapat.adminfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tapat.R;
import com.example.tapat.helpers.dbHelper;

public class AdminNFCReader extends Fragment {
    private dbHelper db;

    public AdminNFCReader() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminnfcreader, container, false);

        // Set the title in the TextView
        TextView textView = view.findViewById(R.id.displayFragmentTitleTextView);
        if (textView != null) {
            textView.setText("AdminNFCReader");
        }

        // Initialize and handle NFC Reader related UI components and functionality here
        //String[] studentData = db.getStudentData(nfc_data);

        //TextView nameView = view.findViewById(R.id.nameText);
        //nameView.setText(studentData[1]);

        //TextView idView = view.findViewById(R.id.idText);
        //idView.setText(studentData[0]);

        //TextView programView = view.findViewById(R.id.programText);
        //programView.setText(studentData[2]);

        return view;
    }
}
