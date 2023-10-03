package com.example.tapat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AdminList extends Fragment {

    private static final String ARG_FRAGMENT_TITLE = "fragmentTitle";
    private String[] dataArray;
    private LinearLayout buttonListLayout;
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
        String fragmentTitle = getArguments().getString(ARG_FRAGMENT_TITLE);

        // Set the fragment title in the TextView
        TextView textView = view.findViewById(R.id.textView);
        if (textView != null) {
            textView.setText(fragmentTitle);
        }

        // Initialize views
        buttonListLayout = view.findViewById(R.id.buttonListLayout);
        EditText searchEditText = view.findViewById(R.id.searchEditText);
        Button addButton = view.findViewById(R.id.addButton);

        // Set the button text for the Add button
        addButton.setText("Add " + fragmentTitle);

        // Initialize and populate the button list
        dataArray = getDataArrayForFragment(fragmentTitle);
        populateButtonList(dataArray);

        // Implement search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the button list based on the search text
                filterButtonList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed in this case
            }
        });

        // Handle button clicks inside the button list
        for (int i = 0; i < buttonListLayout.getChildCount(); i++) {
            View buttonView = buttonListLayout.getChildAt(i);
            if (buttonView instanceof HorizontalScrollView) {
                Button button = (Button) ((HorizontalScrollView) buttonView).getChildAt(0);
                final String buttonText = button.getText().toString();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openAdminDisplayInfoFragment(fragmentTitle, buttonText);
                    }
                });
            }
        }

        // Handle Add button click
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminCreateFragment(fragmentTitle);
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

    private void populateButtonList(String[] data) {
        // Initialize and add buttons to the button list layout based on the data array
        // Make sure to handle cases where text length exceeds the button width
        if (data != null && data.length > 0) {
            for (String item : data) {
                addButtonToLayout(item);
            }
        }
    }

    private void addButtonToLayout(String text) {
        // Create a horizontal scroll view for each button
        HorizontalScrollView scrollView = new HorizontalScrollView(requireContext());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Create a button for the item, and if the text is too long, make it scroll horizontally
        Button button = new Button(requireContext());
        button.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setSingleLine(true);
        button.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        button.setMarqueeRepeatLimit(-1);
        button.setSelected(true);
        button.setText(text);

        // Add the button to the scroll view, and the scroll view to the layout
        scrollView.addView(button);
        buttonListLayout.addView(scrollView);
    }

    private void filterButtonList(String searchText) {
        // Implement logic to filter the button list based on the search text
        // Show/hide buttons based on whether they match the search criteria
        for (int i = 0; i < buttonListLayout.getChildCount(); i++) {
            View view = buttonListLayout.getChildAt(i);
            if (view instanceof HorizontalScrollView) {
                Button button = (Button) ((HorizontalScrollView) view).getChildAt(0);
                if (button.getText().toString().toLowerCase().contains(searchText.toLowerCase())) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
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
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, adminDisplayInfoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openAdminCreateFragment(String fragmentTitle) {
        // Create a new AdminCreate fragment and pass the necessary data
        AdminCreate adminCreateFragment = new AdminCreate();
        Bundle args = new Bundle();
        args.putString("fragmentTitle", fragmentTitle);
        adminCreateFragment.setArguments(args);

        // Replace the current fragment with AdminCreate
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, adminCreateFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

