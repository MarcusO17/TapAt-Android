package com.example.tapat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AttendanceListFragment extends Fragment {

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
    BroadcastReceiver receiver;
    List<String> attendedStudentIDList = new ArrayList<>();
    public AttendanceListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("com.example.tapat.ACTION_NFC_DATA".equals(intent.getAction())) {
                    attendedStudentIDList = intent.getStringArrayListExtra("attendedList");
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("com.example.tapat.ACTION_NFC_DATA");
        requireActivity().registerReceiver(receiver, intentFilter);
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

                Intent intent = new Intent(getContext(), NFCReaderActivity.class);
                ArrayList<StudentItem> studentList = new ArrayList<>(studentsInClass);
                intent.putExtra("student_list", studentList);
                startActivity(intent);
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
                        ArrayList<AttendanceListRowData> attendanceArrayList = new ArrayList<AttendanceListRowData>(attendanceList);

                        //Initialising classData info for indexing
                        //Getting data
                        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm");
                        String date = dateFormat.format(Calendar.getInstance().getTime());

                        String[] classData = new String[]{className,courseID,date};
                        db.insertAttendanceData(classData);
                        db.insertAttendanceStudentsData(attendanceArrayList);
                        // back to last page
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.popBackStack();
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
            Log.d("ClassListFragment", "course_ID: " + courseID);
        }

        studentsInClass = db.getCourseStudents(courseID);

        for(StudentItem student: studentsInClass){
            attendanceList.add(new AttendanceListRowData(className,student.getStudentID(),student.getStudentName(),false,""));
            Log.d("GETTING ATTENDANCE LIST", student.getStudentID() + " " + student.getStudentName());
        }

        attendanceListAdapter = new AttendanceListViewAdapter(attendanceList);

        attendanceListRecyclerView.setAdapter(attendanceListAdapter);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        TextView title = getActivity().findViewById(R.id.fragmentholdertitle);
        title.setText(className + " - " + classID);

        Log.d("ROW ITEM", "IN ON RESUME");
        for (AttendanceListRowData student: attendanceList) {
            Log.d("inside attendance List", student.getStudentID());
        }
        for (String student: attendedStudentIDList) {
            Log.d("inside attendanded student List", student);
        }
        for (AttendanceListRowData data: attendanceList) {
            Log.d("ROW ITEM", data.getStudentID() + " " + data.getStudentName() + " " + data.getAttendance());
            for (String item : attendedStudentIDList) {
                Log.d("id", item);
                Log.d("another id", data.getStudentID());
                if (data.getStudentID().equals(item)) {
                    Log.d("Trigger checkbox check", item);
                    data.setAttendance(true);
                    Log.d("ROW ITEM", data.getStudentID() + " " + data.getStudentName() + " " + data.getAttendance());
                }
            }
        }
        attendanceListAdapter.notifyDataSetChanged();
    }
    public void onPause() {
        super.onPause();
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