package com.example.tapat.adminfragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
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
                nameEditText.setTextColor(Color.parseColor("#ffffff"));
                nameEditText.setHintTextColor(Color.parseColor("#66ffffff"));
                nameEditText.setTextSize(18);

                EditText idEditText = createEditText("ID Number");
                idEditText.setTextColor(Color.parseColor("#ffffff"));
                idEditText.setHintTextColor(Color.parseColor("#66ffffff"));
                idEditText.setTextSize(18);

                Spinner programSpinner = createSpinner(programArray, studentData[2]);

                handleSpinnerUI(programSpinner);

                nameEditText.setText(studentData[0]);
                idEditText.setText(studentData[1]);

                containerLayout.addView(editSection("Name",nameEditText));
                containerLayout.addView(editSection("ID",idEditText));
                containerLayout.addView(editSection("Program",programSpinner));
            }
        } else if ("Lecturer".equals(fragmentTitle)) {
            String[] lecturerData = getLecturerData(buttonName);
            if (lecturerData != null) {
                EditText nameEditText = createEditText("Name");
                nameEditText.setTextColor(Color.parseColor("#ffffff"));
                nameEditText.setHintTextColor(Color.parseColor("#66ffffff"));
                nameEditText.setTextSize(18);

                EditText idEditText = createEditText("ID Number");
                idEditText.setTextColor(Color.parseColor("#ffffff"));
                idEditText.setHintTextColor(Color.parseColor("#66ffffff"));
                idEditText.setTextSize(18);

                EditText emailEditText = createEditText("Email");
                emailEditText.setTextColor(Color.parseColor("#ffffff"));
                emailEditText.setHintTextColor(Color.parseColor("#66ffffff"));
                emailEditText.setTextSize(18);

                EditText passwordEditText = createEditText("Password");
                passwordEditText.setTextColor(Color.parseColor("#ffffff"));
                passwordEditText.setHintTextColor(Color.parseColor("#66ffffff"));
                passwordEditText.setTextSize(18);
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                nameEditText.setText(lecturerData[0]);
                idEditText.setText(lecturerData[1]);
                emailEditText.setText(lecturerData[2]);
                passwordEditText.setText(lecturerData[3]);

                containerLayout.addView(editSection("Name",nameEditText));
                containerLayout.addView(editSection("ID",idEditText));
                containerLayout.addView(editSection("Email",emailEditText));
                containerLayout.addView(editSection("Password",passwordEditText));
            }
        } else if ("Course".equals(fragmentTitle)) {
            String[] courseData = getCourseData(buttonName);
            //query
            String[] lecturerIdArray = {"L1000", "L1001", "L1002", "L1003", "L1004", "L1005", "L1006", "L1007", "L1008", "L1009", "L1010"};
            if (courseData != null) {
                EditText courseNameEditText = createEditText("Course Name");
                courseNameEditText.setTextColor(Color.parseColor("#ffffff"));
                courseNameEditText.setHintTextColor(Color.parseColor("#66ffffff"));
                courseNameEditText.setTextSize(18);

                EditText courseIdEditText = createEditText("Course ID");
                courseIdEditText.setTextColor(Color.parseColor("#ffffff"));
                courseIdEditText.setHintTextColor(Color.parseColor("#66ffffff"));
                courseIdEditText.setTextSize(18);

                Spinner lecturerIdSpinner = createSpinner(lecturerIdArray, courseData[2]);
                Spinner programSpinner = createSpinner(programArray, courseData[3]);

                handleSpinnerUI(lecturerIdSpinner);
                handleSpinnerUI(programSpinner);

                courseNameEditText.setText(courseData[0]);
                courseIdEditText.setText(courseData[1]);

                containerLayout.addView(editSection("Course Name",courseNameEditText));
                containerLayout.addView(editSection("Course ID",courseIdEditText));
                containerLayout.addView(editSection("Lecturer",lecturerIdSpinner));
                containerLayout.addView(editSection("Program",programSpinner));
            }
        }
    }

    private LinearLayout editSection(String label, View widget) {
        // Create a new horizontal LinearLayout
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setMinimumHeight(150);
        linearLayout.setVerticalGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create a TextView for the label (1:1 ratio)
        TextView labelTextView = new TextView(getContext());
        labelTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0, // Width set to 0 for weight-based distribution
                LinearLayout.LayoutParams.WRAP_CONTENT,
                2 // Weight 2 for 2:2 ratio
        ));
        labelTextView.setText(label);
        labelTextView.setTextColor(Color.parseColor("#ffffff"));
        labelTextView.setTextSize(18);

        // Add the label TextView to the LinearLayout
        linearLayout.addView(labelTextView);

        // Set up the widget (2:1 ratio)
        widget.setLayoutParams(new LinearLayout.LayoutParams(
                0, // Width set to 0 for weight-based distribution
                LinearLayout.LayoutParams.WRAP_CONTENT,
                3 // Weight 3 for 3:2 ratio
        ));

        // Add the widget to the LinearLayout
        linearLayout.addView(widget);

        return linearLayout;
    }

    private Spinner handleSpinnerUI(Spinner spin){
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spin);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);

            spin.setBackgroundColor(Color.parseColor("#66ffffff"));

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
            if (child instanceof LinearLayout) {
                LinearLayout horizontalLayout = (LinearLayout) child;
                for (int j = 0; j < horizontalLayout.getChildCount(); j++) {
                    View innerChild = horizontalLayout.getChildAt(j);
                    if (innerChild instanceof EditText) {
                        ((EditText) innerChild).setEnabled(true);
                    } else if (innerChild instanceof Spinner) {
                        ((Spinner) innerChild).setEnabled(true);
                    }
                }
            }
        }

        // Show Save and Cancel buttons, hide Edit button
        editButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    private void handleSaveButtonClick() {
        // Call a method to save the edited data
        saveData();

        // Replace the fragment with AdminList
        replaceFragment(AdminList.newInstance(fragmentTitle));
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

    private void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
