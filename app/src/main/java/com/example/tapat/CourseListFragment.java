package com.example.tapat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tapat.adapter.CourseItemViewAdapter;
import com.example.tapat.helpers.dbHelper;
import com.example.tapat.model.CourseItem;

import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment implements CourseItemViewAdapter.OnClickListener{
    View view;
    dbHelper db;
    List<CourseItem> courseList = new ArrayList<>();
    public CourseListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_course_list, container, false);

        //init DB
        db = new dbHelper(getContext());

        RecyclerView courseListRecyclerView = view.findViewById(R.id.courselistrecyclerview);
        LinearLayoutManager courseListLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        courseListRecyclerView.setLayoutManager(courseListLayout);

        //query the shit here
        /*
        CourseItem course1 = new CourseItem("Android Development Skill", "A202SGI");
        CourseItem course2 = new CourseItem("Data Science", "INT5005CEM");
        CourseItem course3 = new CourseItem("Software Engineering","INT5001CEM");
        CourseItem course4 = new CourseItem("Software Engineering","INT5001CEM");
        CourseItem course5 = new CourseItem("Software Engineering","INT5001CEM");
        CourseItem course6 = new CourseItem("Software Engineering","INT5001CEM");

        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course4);
        courseList.add(course5);
        courseList.add(course6);
        */
        courseList = db.getCourses();
        CourseItemViewAdapter courseItemAdapter = new CourseItemViewAdapter(courseList, this);

        courseListRecyclerView.setAdapter(courseItemAdapter);
        Bundle args = getArguments();

        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        courseList.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView title = getActivity().findViewById(R.id.fragmentholdertitle);
        title.setText("Course List");
    }

    @Override
    public void onClickListener(int position) {

        // set title in fragment holder
        TextView title = getActivity().findViewById(R.id.fragmentholdertitle);
        title.setText(courseList.get(position).getCourseCode() + " - " + courseList.get(position).getName());

        Bundle args = new Bundle();
        args.putString("course_code",courseList.get(position).getCourseCode());
        args.putString("course_name",courseList.get(position).getName());

        ClassListFragment classListFragment = new ClassListFragment();

        classListFragment.setArguments(args);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.classlistframelayout, classListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}