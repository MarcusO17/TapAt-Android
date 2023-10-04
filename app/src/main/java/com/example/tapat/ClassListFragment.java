package com.example.tapat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.tapat.adapter.ClassListViewAdapter;
import com.example.tapat.adapter.CourseItemViewAdapter;
import com.example.tapat.model.ClassListItem;

import java.util.ArrayList;
import java.util.List;

public class ClassListFragment extends Fragment implements CourseItemViewAdapter.OnClickListener {

    String courseName;
    String courseCode;
    Button addClassButton;
    public ClassListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);

        addClassButton = view.findViewById(R.id.button_add_class);

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

        classList.add(class1);
        classList.add(class2);
        classList.add(class3);

        Log.d("ClassListFragment","classList size: " + classList.size());

        ClassListViewAdapter adapter = new ClassListViewAdapter(getContext(),classList, this::onClickListener);
        classListRecyclerView.setAdapter(adapter);

        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer size = classList.size()+1;
                String className = "Class " + size;
                String classID = "c" + size;
                ClassListItem tempclass = new ClassListItem(className, classID);
                classList.add(tempclass);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onClickListener(int position) {
        //open attendanceListDetails
        //implement the same exact thing again
    }
}