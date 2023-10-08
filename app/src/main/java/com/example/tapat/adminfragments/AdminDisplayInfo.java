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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tapat.R;
import com.example.tapat.helpers.dbHelper;

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
    private dbHelper db;

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

        //Init DB
        db = new dbHelper(getContext());

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

        /** FIX DISPLAY ISSUES **/
        if ("Student".equals(fragmentTitle)) {
            String[] studentData = db.getSingularData("Student",buttonName);
            if (studentData != null) {
                EditText nameEditText = createEditText("Name");
                EditText idEditText = createEditText("ID Number");
                Spinner programSpinner = createSpinner(programArray, studentData[2]);

                handleSpinnerUI(programSpinner);

                nameEditText.setText(studentData[1]);
                idEditText.setText(studentData[0]);

                containerLayout.addView(nameEditText);
                containerLayout.addView(idEditText);
                containerLayout.addView(programSpinner);
            }
        } else if ("Lecturer".equals(fragmentTitle)) {
            String[] lecturerData = db.getSingularData("Lecturer",buttonName);
            if (lecturerData != null) {
                EditText nameEditText = createEditText("Name");
                EditText idEditText = createEditText("ID Number");
                EditText emailEditText = createEditText("Email");
                EditText passwordEditText = createEditText("Password");

                nameEditText.setText(lecturerData[1]);
                idEditText.setText(lecturerData[0]);
                emailEditText.setText(lecturerData[2]);
                passwordEditText.setText(lecturerData[3]);

                containerLayout.addView(nameEditText);
                containerLayout.addView(idEditText);
                containerLayout.addView(emailEditText);
                containerLayout.addView(passwordEditText);
            }
        } else if ("Course".equals(fragmentTitle)) {
            String[] courseData = db.getSingularData("Course",buttonName);
            //query
            String[] lecturerIdArray = db.getID("Lecturer");
            if (courseData != null) {
                EditText courseNameEditText = createEditText("Course Name");
                EditText courseIdEditText = createEditText("Course ID");
                Spinner lecturerIdSpinner = createSpinner(lecturerIdArray, courseData[1]);
                Spinner programSpinner = createSpinner(programArray, courseData[3]);

                handleSpinnerUI(lecturerIdSpinner);
                handleSpinnerUI(programSpinner);

                courseNameEditText.setText(courseData[2]);
                courseIdEditText.setText(courseData[0]);

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

    // Method to save Data(sql query)
    private void saveData() {
        //Get specific User.
        buttonName = getArguments().getString(ARG_BUTTON_NAME);

        if ("Student".equals(fragmentTitle)) {
            String name = ((EditText) containerLayout.getChildAt(0)).getText().toString();
            String id = ((EditText) containerLayout.getChildAt(1)).getText().toString();
            String program = ((Spinner) containerLayout.getChildAt(2)).getSelectedItem().toString();
            String[] studentData = {name, id, program};
            // Change studentData on the student array
            if(studentData[0].equals("") || studentData[1].equals("")){
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }else if(!db.updateStudentData(studentData,buttonName)) {
                // Add studentData to the student array
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }

            replaceFragment(AdminList.newInstance("Student"));

        } else if ("Lecturer".equals(fragmentTitle)) {
            String name = ((EditText) containerLayout.getChildAt(0)).getText().toString();
            String id = ((EditText) containerLayout.getChildAt(1)).getText().toString();
            String email = ((EditText) containerLayout.getChildAt(2)).getText().toString();
            String password = ((EditText) containerLayout.getChildAt(3)).getText().toString();
            String[] lecturerData = {id,name, email, password};
            // Change lecturerData on the lecturer array
            if(lecturerData[0].equals("") || lecturerData[1].equals("")){
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }else if(!db.updateLecturerData(lecturerData,buttonName)) {
                // Add studentData to the student array
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }

            replaceFragment(AdminList.newInstance("Lecturer"));

        } else if ("Course".equals(fragmentTitle)) {
            String coursename = ((EditText) containerLayout.getChildAt(0)).getText().toString();
            String courseid = ((EditText) containerLayout.getChildAt(1)).getText().toString();
            String lecturerid = ((Spinner) containerLayout.getChildAt(2)).getSelectedItem().toString();
            String program = ((Spinner) containerLayout.getChildAt(3)).getSelectedItem().toString();
            String[] courseData = {courseid, lecturerid,coursename, program};
            // Change course data
            if(courseData[0].equals("") || courseData[1].equals("")){
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }else if(!db.updateCourseData(courseData,buttonName)) {
                // Add studentData to the student array
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }

            replaceFragment(AdminList.newInstance("Course"));
        }
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
