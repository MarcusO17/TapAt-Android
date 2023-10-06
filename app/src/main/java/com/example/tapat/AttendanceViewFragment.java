package com.example.tapat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tapat.adapter.AttendanceViewAdapter;
import com.example.tapat.model.AttendanceListRowData;
import com.example.tapat.model.Student;

import java.util.ArrayList;
import java.util.List;

public class AttendanceViewFragment extends Fragment {

    View view;
    List<AttendanceListRowData> attendanceList = new ArrayList<>();
    String className;
    String classID;

    public AttendanceViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_attendance_view, container, false);
        RecyclerView attendanceListRecyclerView = view.findViewById(R.id.attendanceviewrecyclerview);
        LinearLayoutManager attendanceListLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        attendanceListRecyclerView.setLayoutManager(attendanceListLayout);

        Bundle args = getArguments();


        if (args != null) {
            className = args.getString("class_id");
            classID = args.getString("class_name");
            Log.d("ClassListFragment", "class name: " + className);
            Log.d("ClassListFragment", "class id: " + classID);
        }
        //query the shit here
        Student student1 = new Student("Ali", "P21011234");
        Student student2 = new Student("Abu", "P21010001");
        Student student3 = new Student("John","P21011002");
        Student student4 = new Student("Felix","P21011003");

        AttendanceListRowData row1 = new AttendanceListRowData(student1.getStudentName(),false,"");
        AttendanceListRowData row2 = new AttendanceListRowData(student2.getStudentName(),false,"");
        AttendanceListRowData row3 = new AttendanceListRowData(student3.getStudentName(),false,"");
        AttendanceListRowData row4 = new AttendanceListRowData(student4.getStudentName(),false,"");

        attendanceList.add(row1);
        attendanceList.add(row2);
        attendanceList.add(row3);
        attendanceList.add(row4);


        AttendanceViewAdapter attendanceListAdapter = new AttendanceViewAdapter(attendanceList);

        attendanceListRecyclerView.setAdapter(attendanceListAdapter);

        return view;

    }
}