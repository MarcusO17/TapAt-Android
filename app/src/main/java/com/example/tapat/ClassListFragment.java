package com.example.tapat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.tapat.adapter.ClassListViewAdapter;
import com.example.tapat.adapter.CourseItemViewAdapter;
import com.example.tapat.model.ClassListItem;

import java.util.ArrayList;
import java.util.List;

public class ClassListFragment extends Fragment implements CourseItemViewAdapter.OnClickListener {


    String courseName;
    String courseCode;
    public ClassListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);

        RecyclerView classListRecyclerView = view.findViewById(R.id.classlistrecyclerview);
        LinearLayoutManager classListLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        classListRecyclerView.setHasFixedSize(true);
        classListRecyclerView.setLayoutManager(classListLayout);

        Bundle args = getArguments();


        if (args != null) {
            courseName = args.getString("course_name");
            courseCode = args.getString("course_code");
            Log.d("ClassListFragment","course name: " + courseName);
            Log.d("ClassListFragment","course code: " + courseCode);
        }
        List<ClassListItem> classList = new ArrayList<>();

        // use the coursename and coursecode to query the items here
        ClassListItem class1 = new ClassListItem("Class 1", "c1");
        ClassListItem class2 = new ClassListItem("Class 2", "c2");
        ClassListItem class3 = new ClassListItem("Class 3", "c3");
        ClassListItem class4 = new ClassListItem("Class 4", "c4");
        ClassListItem class5 = new ClassListItem("Class 5", "c5");
        ClassListItem class6 = new ClassListItem("Class 6", "c6");
        ClassListItem class7 = new ClassListItem("Class 7", "c7");

        classList.add(class1);
        classList.add(class2);
        classList.add(class3);
        classList.add(class4);
        classList.add(class5);
        classList.add(class6);
        classList.add(class7);

        Log.d("ClassListFragment","classList size: " + classList.size());

        ClassListViewAdapter adapter = new ClassListViewAdapter(getContext(),classList, this::onClickListener);
        classListRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClickListener(int position) {
        //open attendanceListDetails
        //implement the same exact thing again
    }
}