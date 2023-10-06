package com.example.tapat.adminfragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;

import java.util.Arrays;

public class AdminNFCwriter extends Fragment {

    private ButtonListAdapter buttonListAdapter;

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

        String [] student = new String[]{"Abu", "Ali", "Chisa", "Murta", "Marci", "John", "Baboon", "Dill", "Chloe", "Furn"};

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize ButtonListAdapter
        buttonListAdapter = new ButtonListAdapter(requireContext());

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(buttonListAdapter);

        // Initialize views
        EditText searchEditText = view.findViewById(R.id.searchEditText);

        buttonListAdapter.setOnItemClickListener(new ButtonListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String buttonText) {
                writeNfc(buttonText);
            }
        });

        // Initialize and populate the button list
        buttonListAdapter.setData(Arrays.asList(student));

        // Implement search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the button list based on the search text
                buttonListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed in this case
            }
        });

        return view;
    }

    private void writeNfc(String buttonName) {
        // Create and display the Toast message with the buttonName
        Context context = requireContext(); // Get the context
        Toast toast = Toast.makeText(context, buttonName, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        // Set a delay of 3 seconds to hide the Toast
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel(); // Hide the Toast after 3 seconds
            }
        }, 3000); // 3000 milliseconds = 3 seconds

        // Your NFC writing logic can go here
        // ...
    }
}
