package com.example.tapat;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tapat.adapter.AttendanceListViewAdapter;
import com.example.tapat.helpers.dbHelper;
import com.example.tapat.model.AttendanceListRowData;
import com.example.tapat.model.ClassListItem;
import com.example.tapat.model.StudentItem;

import java.util.ArrayList;
import java.util.List;

public class AttendanceListFragment extends Fragment{

    View view;
    List<AttendanceListRowData> attendanceList = new ArrayList<>();
    List<StudentItem> studentsInClass = new ArrayList<>();
    String className;
    String courseID;
    String classID;
    dbHelper db;
    Button attendanceTakingButton;
    Button submitButton;
    EditText searchBar;
    AttendanceListViewAdapter attendanceListAdapter;

    public AttendanceListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_attendance_list, container, false);

        //Init DB
        db = new dbHelper(getContext());
        db.populateCourseStudents();

        attendanceTakingButton = view.findViewById(R.id.attendance_taking_button);
        submitButton = view.findViewById(R.id.submit_attendance_button);
        searchBar = view.findViewById(R.id.attendancelistsearchbar);

        attendanceTakingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttendanceScanningFragment attendanceScanningFragment = new AttendanceScanningFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.classlistframelayout, attendanceScanningFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setMessage("Submit Attendance List?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //send to information to database

                        // back to last page
                        getActivity().onBackPressed();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        RecyclerView attendanceListRecyclerView = view.findViewById(R.id.attendancelistrecyclerview);
        LinearLayoutManager attendanceListLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        attendanceListRecyclerView.setLayoutManager(attendanceListLayout);

        Bundle args = getArguments();


        if (args != null) {
            className = args.getString("class_id");
            classID = args.getString("class_name");
            courseID = args.getString("course_ID");
            Log.d("ClassListFragment", "class name: " + className);
            Log.d("ClassListFragment", "class id: " + classID);
        }
        //query the shit here
        /* TESTING PRE-DB
        StudentItem studentItem1 = new StudentItem("Ali", "P21011234");
        StudentItem studentItem2 = new StudentItem("Abu", "P21010001");
        StudentItem studentItem3 = new StudentItem("John","P21011002");
        StudentItem studentItem4 = new StudentItem("Felix","P21011003");

        AttendanceListRowData row1 = new AttendanceListRowData(studentItem1.getStudentName(),false,"");
        AttendanceListRowData row2 = new AttendanceListRowData(studentItem2.getStudentName(),false,"");
        AttendanceListRowData row3 = new AttendanceListRowData(studentItem3.getStudentName(),false,"");
        AttendanceListRowData row4 = new AttendanceListRowData(studentItem4.getStudentName(),false,"");

        attendanceList.add(row1);
        attendanceList.add(row2);
        attendanceList.add(row3);
        attendanceList.add(row4);
        */

        studentsInClass = db.getCourseStudents(courseID);

        for(StudentItem student: studentsInClass){
            attendanceList.add(new AttendanceListRowData(classID,student.getStudentID(),student.getStudentName(),false,""));
        }

        attendanceListAdapter = new AttendanceListViewAdapter(attendanceList);

        attendanceListRecyclerView.setAdapter(attendanceListAdapter);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        TextView title = getActivity().findViewById(R.id.fragmentholdertitle);
        title.setText("Attendance List");
    }
    public void onPause() {
        super.onPause();
        attendanceList.clear();
    }

    private void filter(String text){
        ArrayList<AttendanceListRowData> filteredList= new ArrayList<>();
        for (AttendanceListRowData item: attendanceList) {
            if (item.getStudentName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        attendanceListAdapter.filteredList(filteredList);
    }

    public void showExitConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setMessage("Are you sure you want to Exit?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // back to last page
                getParentFragmentManager().popBackStack();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}