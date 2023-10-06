package com.example.tapat.adminfragments;

import android.os.Bundle;
import android.text.InputType;
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

public class AdminCreate extends Fragment {

    private static final String ARG_FRAGMENT_TITLE = "fragmentTitle";

    private String fragmentTitle;
    private LinearLayout containerLayout;
    private Button addButton;
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

            EditText idEditText = new EditText(requireContext());
            idEditText.setHint("ID Number");

            Spinner programSpinner = new Spinner(requireContext());
            handleSpinnerUI(programSpinner);
            ArrayAdapter<String> programAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, programArray);
            programAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            programSpinner.setAdapter(programAdapter);

            containerLayout.addView(nameEditText);
            containerLayout.addView(idEditText);
            containerLayout.addView(programSpinner);
        } else if ("Lecturer".equals(fragmentTitle)) {
            // Create UI for Lecturer
            EditText nameEditText = new EditText(requireContext());
            nameEditText.setHint("Name");

            EditText idEditText = new EditText(requireContext());
            idEditText.setHint("ID Number");

            EditText emailEditText = new EditText(requireContext());
            emailEditText.setHint("Email");

            EditText passwordEditText = new EditText(requireContext());
            passwordEditText.setHint("Password");
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            containerLayout.addView(nameEditText);
            containerLayout.addView(idEditText);
            containerLayout.addView(emailEditText);
            containerLayout.addView(passwordEditText);
        } else if ("Course".equals(fragmentTitle)) {
            EditText courseNameEditText = new EditText(requireContext());
            courseNameEditText.setHint("Course Name");

            EditText courseIdEditText = new EditText(requireContext());
            courseIdEditText.setHint("Course ID");

            //pull down the lecturer_id as array
            String[] lecturerIdArray = {"L1000", "L1001", "L1002", "L1003", "L1004", "L1005", "L1006", "L1007", "L1008", "L1009", "L1010"};
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
            containerLayout.addView(courseNameEditText);
            containerLayout.addView(courseIdEditText);
            containerLayout.addView(lecturerIdSpinner);
            containerLayout.addView(programSpinner);

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

    private void handleAddButtonClick() {
        // Handle the logic for adding data to the respective arrays
        if ("Student".equals(fragmentTitle)) {
            String name = ((EditText) containerLayout.getChildAt(0)).getText().toString();
            String id = ((EditText) containerLayout.getChildAt(1)).getText().toString();
            String program = ((Spinner) containerLayout.getChildAt(2)).getSelectedItem().toString();
            String[] studentData = {name, id, program};
            // Add studentData to the student array

            replaceFragment(AdminList.newInstance("Student"));
        } else if ("Lecturer".equals(fragmentTitle)) {
            String name = ((EditText) containerLayout.getChildAt(0)).getText().toString();
            String id = ((EditText) containerLayout.getChildAt(1)).getText().toString();
            String email = ((EditText) containerLayout.getChildAt(2)).getText().toString();
            String password = ((EditText) containerLayout.getChildAt(3)).getText().toString();
            String[] lecturerData = {name, id, email, password};
            // Add lecturerData to the lecturer array

            replaceFragment(AdminList.newInstance("Lecturer"));
        } else if ("Course".equals(fragmentTitle)) {
            String coursename = ((EditText) containerLayout.getChildAt(0)).getText().toString();
            String courseid = ((EditText) containerLayout.getChildAt(1)).getText().toString();
            String lecturerid = ((Spinner) containerLayout.getChildAt(2)).getSelectedItem().toString();
            String program = ((Spinner) containerLayout.getChildAt(3)).getSelectedItem().toString();
            String[] courseData = {coursename, courseid, lecturerid, program};
            // Handle adding a course (if needed)

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
