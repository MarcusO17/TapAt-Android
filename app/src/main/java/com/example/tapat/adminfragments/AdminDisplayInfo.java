package com.example.tapat.adminfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tapat.R;

import java.lang.reflect.Field;

public class AdminDisplayInfo extends Fragment {

    private static final String ARG_BUTTON_NAME = "buttonName";
    private static final String ARG_FRAGMENT_TITLE = "fragmentTitle";
    private String buttonName;
    private String fragmentTitle;

    private static final String[] programArray = {"BCSCUN", "DCS", "MCS03", "BCTCUN"};

    private LinearLayout containerLayout;
    private Button editButton;
    private Button saveButton;
    private Button cancelButton;

    public AdminDisplayInfo() {
        // Required empty public constructor
    }

    public static AdminDisplayInfo newInstance(String buttonName, String fragmentTitle) {
        AdminDisplayInfo fragment = new AdminDisplayInfo();
        Bundle args = new Bundle();
        args.putString(ARG_BUTTON_NAME, buttonName);
        args.putString(ARG_FRAGMENT_TITLE, fragmentTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admindisplayinfo, container, false);

        // Retrieve the buttonName and fragmentTitle from arguments
        fragmentTitle = getArguments().getString(ARG_FRAGMENT_TITLE);
        buttonName = getArguments().getString(ARG_BUTTON_NAME);

        // Set the fragment title and button name in the TextViews
        TextView fragmentTitleTextView = view.findViewById(R.id.displayFragmentTitleTextView);

        if (fragmentTitleTextView != null) {
            fragmentTitleTextView.setText(fragmentTitle);
        }

        // Initialize UI elements
        containerLayout = view.findViewById(R.id.containerLayout);
        editButton = view.findViewById(R.id.editButton);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        // Hide editButton and show saveButton and cancelButton initially
        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);

        // Set up the UI based on the fragmentTitle
        generateUI();

        // Set an onClickListener for the editButton
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEditButtonClick();
            }
        });

        // Set an onClickListener for the saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveButtonClick();
            }
        });

        // Set an onClickListener for the cancelButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancelButtonClick();
            }
        });

        return view;
    }

    private void generateUI() {
        containerLayout.removeAllViews(); // Clear any existing UI elements

        if ("Student".equals(fragmentTitle)) {
            String[] studentData = getStudentData(buttonName);
            if (studentData != null) {
                EditText nameEditText = createEditText("Name");
                EditText idEditText = createEditText("ID Number");
                Spinner programSpinner = createSpinner(programArray, studentData[2]);

                handleSpinnerUI(programSpinner);

                nameEditText.setText(studentData[0]);
                idEditText.setText(studentData[1]);

                containerLayout.addView(nameEditText);
                containerLayout.addView(idEditText);
                containerLayout.addView(programSpinner);
            }
        } else if ("Lecturer".equals(fragmentTitle)) {
            String[] lecturerData = getLecturerData(buttonName);
            if (lecturerData != null) {
                EditText nameEditText = createEditText("Name");
                EditText idEditText = createEditText("ID Number");
                EditText emailEditText = createEditText("Email");
                EditText passwordEditText = createEditText("Password");

                nameEditText.setText(lecturerData[0]);
                idEditText.setText(lecturerData[1]);
                emailEditText.setText(lecturerData[2]);
                passwordEditText.setText(lecturerData[3]);

                containerLayout.addView(nameEditText);
                containerLayout.addView(idEditText);
                containerLayout.addView(emailEditText);
                containerLayout.addView(passwordEditText);
            }
        } else if ("Course".equals(fragmentTitle)) {
            String[] courseData = getCourseData(buttonName);
            //query
            String[] lecturerIdArray = {"L1000", "L1001", "L1002", "L1003", "L1004", "L1005", "L1006", "L1007", "L1008", "L1009", "L1010"};
            if (courseData != null) {
                EditText courseNameEditText = createEditText("Course Name");
                EditText courseIdEditText = createEditText("Course ID");
                Spinner lecturerIdSpinner = createSpinner(lecturerIdArray, courseData[2]);
                Spinner programSpinner = createSpinner(programArray, courseData[3]);

                handleSpinnerUI(lecturerIdSpinner);
                handleSpinnerUI(programSpinner);

                courseNameEditText.setText(courseData[0]);
                courseIdEditText.setText(courseData[1]);

                containerLayout.addView(courseNameEditText);
                containerLayout.addView(courseIdEditText);
                containerLayout.addView(lecturerIdSpinner);
                containerLayout.addView(programSpinner);
            }
        }
    }

    private Spinner handleSpinnerUI(Spinner spin){
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spin);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);

        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        return spin;
    }

    private void handleEditButtonClick() {
        // Enable editing of EditText and Spinner fields
        for (int i = 0; i < containerLayout.getChildCount(); i++) {
            View child = containerLayout.getChildAt(i);
            if (child instanceof EditText) {
                ((EditText) child).setEnabled(true);
            } else if (child instanceof Spinner) {
                ((Spinner) child).setEnabled(true);
            }
        }

        // Show Save and Cancel buttons, hide Edit button
        editButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    private void handleSaveButtonClick() {
        // Disable editing of EditText and Spinner fields
        for (int i = 0; i < containerLayout.getChildCount(); i++) {
            View child = containerLayout.getChildAt(i);
            if (child instanceof EditText) {
                ((EditText) child).setEnabled(false);
            } else if (child instanceof Spinner) {
                ((Spinner) child).setEnabled(false);
            }
        }


        // Hide Save and Cancel buttons, show Edit button
        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);

        // Call a method to save the edited data
        saveData();
    }

    private void handleCancelButtonClick() {
        // Disable editing of EditText and Spinner fields
        for (int i = 0; i < containerLayout.getChildCount(); i++) {
            View child = containerLayout.getChildAt(i);
            if (child instanceof EditText) {
                ((EditText) child).setEnabled(false);
            } else if (child instanceof Spinner) {
                ((Spinner) child).setEnabled(false);
            }
        }

        // Hide Save and Cancel buttons, show Edit button
        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);

        // If you want to discard changes, you can reload the original data
        generateUI();
    }

    //getting data
    private String[] getStudentData(String buttonName) {
        //query single row
        String[][] studentData = {
                {"Ali","P21013251","BCSCUN"},
                {"Abu","P21013252","MCS03"},
                {"Chisa","P21013253","BCSCUN"},
                {"Murta","P21013254","DCS"},
                {"Marci","P21013255","BCTCUN"},
                {"John","P21013256","DCS"},
                {"Baboon","P21013257","BCSCUN"},
                {"Dill","P21013258","BCSCUN"},
                {"Chloe","P21013259","MCS03"},
                {"Furn","P21013260","MCS03"}
        };

        for (String[] student : studentData) {
            if (student[0].equals(buttonName)) {
                return student;
            }
        }

        return null; // Return null if data not found
    }

    private String[] getLecturerData(String buttonName) {
        //query single row
        String[][] lecturerData = {
                {"Muka","L1000","Muka@lecturer.college.edu.my","abc"},
                {"Ghili","L1001","Ghilli@lecturer.college.edu.my","135"}
        };

        for (String[] lecturer : lecturerData) {
            if (lecturer[0].equals(buttonName)) {
                return lecturer;
            }
        }

        return null; // Return null if data not found
    }

    private String[] getCourseData(String buttonName) {
        //query single row
        String[][] courseData = {
                {"Android Development","AG1001","L1000","BCSCUN"},
                {"Software Engineering","AG1003","L1001","DCS"}
        };

        for (String[] course : courseData) {
            if (course[0].equals(buttonName.split(": ")[1])) {
                return course;
            }
        }

        return null; // Return null if data not found
    }


    // Method to save Data(sql query)
    private void saveData() {

    }

    // Helper methods to create UI elements
    private EditText createEditText(String hint) {
        EditText editText = new EditText(requireContext());
        editText.setHint(hint);
        editText.setEnabled(false); // Initially, disable editing
        return editText;
    }

    private Spinner createSpinner(String[] array, String selectedItem) {
        Spinner spinner = new Spinner(requireContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(getArrayIndex(array, selectedItem));
        spinner.setEnabled(false); // Initially, disable editing
        return spinner;
    }

    private int getArrayIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return 0; // Default to the first item if not found
    }
}
