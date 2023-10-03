package com.example.tapat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;

public class AdminList extends Fragment {

    private static final String ARG_FRAGMENT_TITLE = "fragmentTitle";
    private String[] dataArray;
    private ButtonListAdapter buttonListAdapter;
    private String fragmentTitle;

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
        fragmentTitle = getArguments().getString(ARG_FRAGMENT_TITLE);

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize ButtonListAdapter
        buttonListAdapter = new ButtonListAdapter(requireContext());

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(buttonListAdapter);

        // Initialize views
        EditText searchEditText = view.findViewById(R.id.searchEditText);

        // Set the button text for the Add button
        buttonListAdapter.setOnItemClickListener(new ButtonListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String buttonText) {
                openAdminDisplayInfoFragment(fragmentTitle, buttonText);
            }
        });

        // Initialize and populate the button list
        dataArray = getDataArrayForFragment(fragmentTitle);
        buttonListAdapter.setData(Arrays.asList(dataArray));

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

    private String[] getDataArrayForFragment(String fragmentTitle) {
        // Implement logic to get the appropriate data array based on the fragment title
        // For example, if fragmentTitle is "Students," return the students array
        // Similarly for "Lecturer" and "Courses"
        if ("Student".equals(fragmentTitle)) {
            return new String[]{"Abu", "Ali", "Chisa", "Murta", "Marci", "John", "Baboon", "Dill", "Chloe", "Furn"};
        } else if ("Lecturer".equals(fragmentTitle)) {
            return new String[]{"Muka", "Ghili"};
        } else if ("Course".equals(fragmentTitle)) {
            return new String[]{"AG1001: Android Development", "AG1003: Software Engineering"};
        } else {
            // Handle other fragment titles or return an empty array as needed
            return new String[]{};
        }
    }

    private void openAdminDisplayInfoFragment(String fragmentTitle, String buttonName) {
        // Create a new AdminDisplayInfo fragment and pass the necessary data
        AdminDisplayInfo adminDisplayInfoFragment = new AdminDisplayInfo();
        Bundle args = new Bundle();
        args.putString("fragmentTitle", fragmentTitle);
        args.putString("buttonName", buttonName);
        adminDisplayInfoFragment.setArguments(args);

        // Replace the current fragment with AdminDisplayInfo
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, adminDisplayInfoFragment)
                .addToBackStack(null)
                .commit();
    }
}
