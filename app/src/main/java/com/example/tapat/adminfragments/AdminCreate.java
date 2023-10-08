package com.example.tapat.adminfragments;

import android.content.Context;
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
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tapat.R;
import com.example.tapat.helpers.dbHelper;

import java.lang.reflect.Field;

public class AdminCreate extends Fragment {

    private static final String ARG_FRAGMENT_TITLE = "fragmentTitle";

    private String fragmentTitle;
    private LinearLayout containerLayout;
    private Button addButton;

    private dbHelper db;
    private static final String[] programArray = {"BCSCUN", "DCS", "MCS03", "BCTCUN"};

    public AdminCreate() {
        // Required empty public constructor
    }

    public static AdminCreate newInstance(String fragmentTitle) {
        AdminCreate fragment = new AdminCreate();
        Bundle args = new Bundle();
        args.putString(ARG_FRAGMENT_TITLE, fragmentTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admincreate, container, false);
        //Init DB
        db = new dbHelper(getContext());

        // Retrieve the fragment title from arguments
        fragmentTitle = getArguments().getString(ARG_FRAGMENT_TITLE);

        // Set the fragment title in the TextView
        TextView titleTextView = view.findViewById(R.id.createTitleTextView);
        titleTextView.setText("Add " + fragmentTitle);

        // Initialize UI elements based on the fragment title
        containerLayout = view.findViewById(R.id.containerLayout);
        addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddButtonClick();
            }
        });

        // Generate the UI elements based on the fragment title
        generateUI();

        return view;
    }

    private void generateUI() {
        containerLayout.removeAllViews(); // Clear any existing UI elements

        if ("Student".equals(fragmentTitle)) {
            // Create UI for Student
            EditText nameEditText = new EditText(requireContext());
            nameEditText.setHint("Name");
            nameEditText.setTextColor(Color.parseColor("#ffffff"));
            nameEditText.setHintTextColor(Color.parseColor("#66ffffff"));
            nameEditText.setTextSize(18);

            EditText idEditText = new EditText(requireContext());
            idEditText.setHint("ID Number");
            idEditText.setTextColor(Color.parseColor("#ffffff"));
            idEditText.setHintTextColor(Color.parseColor("#66ffffff"));
            idEditText.setTextSize(18);

            Spinner programSpinner = new Spinner(requireContext());
            handleSpinnerUI(programSpinner);
            ArrayAdapter<String> programAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, programArray);
            programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            programSpinner.setAdapter(programAdapter);

            containerLayout.addView(editSection("Name",nameEditText));
            containerLayout.addView(editSection("ID",idEditText));
            containerLayout.addView(editSection("Program",programSpinner));

        } else if ("Lecturer".equals(fragmentTitle)) {
            // Create UI for Lecturer
            EditText nameEditText = new EditText(requireContext());
            nameEditText.setHint("Name");
            nameEditText.setTextColor(Color.parseColor("#ffffff"));
            nameEditText.setHintTextColor(Color.parseColor("#66ffffff"));
            nameEditText.setTextSize(18);

            EditText idEditText = new EditText(requireContext());
            idEditText.setHint("ID Number");
            idEditText.setTextColor(Color.parseColor("#ffffff"));
            idEditText.setHintTextColor(Color.parseColor("#66ffffff"));
            idEditText.setTextSize(18);

            EditText emailEditText = new EditText(requireContext());
            emailEditText.setHint("Email");
            emailEditText.setTextColor(Color.parseColor("#ffffff"));
            emailEditText.setHintTextColor(Color.parseColor("#66ffffff"));
            emailEditText.setTextSize(18);

            EditText passwordEditText = new EditText(requireContext());
            passwordEditText.setHint("Password");
            passwordEditText.setTextColor(Color.parseColor("#ffffff"));
            passwordEditText.setHintTextColor(Color.parseColor("#66ffffff"));
            passwordEditText.setTextSize(18);
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            containerLayout.addView(editSection("Name",nameEditText));
            containerLayout.addView(editSection("ID",idEditText));
            containerLayout.addView(editSection("Email",emailEditText));
            containerLayout.addView(editSection("Password",passwordEditText));

        } else if ("Course".equals(fragmentTitle)) {
            EditText courseNameEditText = new EditText(requireContext());
            courseNameEditText.setHint("Course Name");
            courseNameEditText.setTextColor(Color.parseColor("#ffffff"));
            courseNameEditText.setHintTextColor(Color.parseColor("#66ffffff"));
            courseNameEditText.setTextSize(18);

            EditText courseIdEditText = new EditText(requireContext());
            courseIdEditText.setHint("Course ID");
            courseIdEditText.setTextColor(Color.parseColor("#ffffff"));
            courseIdEditText.setHintTextColor(Color.parseColor("#66ffffff"));
            courseIdEditText.setTextSize(18);

            //pull down the lecturer_id as array
            String[] lecturerIdArray = db.getID("Lecturer");

            Spinner lecturerIdSpinner = new Spinner(requireContext());

            handleSpinnerUI(lecturerIdSpinner);
            ArrayAdapter<String> lecturerIdAdapter = new ArrayAdapter<>(requireContext(),
                                                    android.R.layout.simple_spinner_item, lecturerIdArray);

            lecturerIdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            lecturerIdSpinner.setAdapter(lecturerIdAdapter);

            Spinner programSpinner = new Spinner(requireContext());

            handleSpinnerUI(programSpinner);
            ArrayAdapter<String> programAdapter = new ArrayAdapter<>(requireContext(),
                                                     android.R.layout.simple_spinner_item, programArray);

            programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            programSpinner.setAdapter(programAdapter);

            // Add UI elements to the containerLayout
            containerLayout.addView(editSection("Course Name",courseNameEditText));
            containerLayout.addView(editSection("Course ID",courseIdEditText));
            containerLayout.addView(editSection("Lecturer",lecturerIdSpinner));
            containerLayout.addView(editSection("Program",programSpinner));

        }
    }

    private Spinner handleSpinnerUI(Spinner spin){
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            ListPopupWindow popupWindow = (ListPopupWindow) popup.get(spin);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);

            spin.setBackgroundColor(Color.parseColor("#66ffffff"));

        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        return spin;
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

    private void handleAddButtonClick() {
        // Handle the logic for adding data to the respective arrays
        if ("Student".equals(fragmentTitle)) {
          
            String name = ((EditText) containerLayout.getChildAt(0)).getChildAt(1)).getText().toString();
            String id = ((EditText) containerLayout.getChildAt(1)).getChildAt(1)).getText().toString();
            String program = ((Spinner) containerLayout.getChildAt(2)).getChildAt(1)).getSelectedItem().toString();
            String[] studentData = {id, name, program};
            //Error Handling
            if(studentData[0].equals("") || studentData[1].equals("")){
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }else if(!db.insertStudentData(studentData)) {
            // Add studentData to the student array
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }

            replaceFragment(AdminList.newInstance("Student"));

        } else if ("Lecturer".equals(fragmentTitle)) {

            String name = ((EditText) containerLayout.getChildAt(0)).getChildAt(1)).getText().toString();
            String id = ((EditText) containerLayout.getChildAt(1)).getChildAt(1)).getText().toString();
            String email = ((EditText) containerLayout.getChildAt(2)).getChildAt(1)).getText().toString();
            String password = ((EditText) containerLayout.getChildAt(3)).getChildAt(1)).getText().toString();
            String[] lecturerData = { id,name, email, password};
            if(lecturerData[0].equals("") || lecturerData[1].equals("")){
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }else if(!db.insertLecturerData(lecturerData)) {
                // Add studentData to the student array
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }

            replaceFragment(AdminList.newInstance("Lecturer"));


        } else if ("Course".equals(fragmentTitle)) {
          
            String courseName = ((EditText) containerLayout.getChildAt(0)).getChildAt(1)).getText().toString();
            String courseID= ((EditText) containerLayout.getChildAt(1)).getChildAt(1)).getText().toString();
            String lecturerID = ((Spinner) containerLayout.getChildAt(2)).getChildAt(1)).getSelectedItem().toString();
            String program = ((Spinner) containerLayout.getChildAt(3)).getChildAt(1)).getSelectedItem().toString();
            String[] courseData = {courseID,lecturerID,courseName,program};
            // Handle adding a course (if needed)
            if(courseData[0].equals("") || courseData[2].equals("")){
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }else if(!db.insertCourseData(courseData)) {
                // Add studentData to the student array
                Toast.makeText(getContext(),"Insert Failed!",Toast.LENGTH_SHORT).show();
            }

            replaceFragment(AdminList.newInstance("Course"));
        }
    }

    private void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
