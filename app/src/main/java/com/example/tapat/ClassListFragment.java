package com.example.tapat;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.tapat.adapter.ClassListViewAdapter;
import com.example.tapat.adapter.CourseItemViewAdapter;
import com.example.tapat.helpers.dbHelper;
import com.example.tapat.model.ClassListItem;
import com.example.tapat.model.CourseItem;

import java.util.ArrayList;
import java.util.List;

public class ClassListFragment extends Fragment implements CourseItemViewAdapter.OnClickListener {

    String courseName;
    String courseCode;
    Button addClassButton;
    dbHelper db;
    List<ClassListItem> classList = new ArrayList<>();
    public ClassListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_list, container, false);

        //init DB
        db = new dbHelper(getContext());
        addClassButton = view.findViewById(R.id.button_add_class);

        // the recycler view is made and the contents of the recycler view is populated using the adapter
        RecyclerView classListRecyclerView = view.findViewById(R.id.classlistrecyclerview);
        LinearLayoutManager classListLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        classListRecyclerView.setHasFixedSize(true);
        classListRecyclerView.setLayoutManager(classListLayout);

        Bundle args = getArguments();

        //getCourseID and CourseName

        if (args != null) {
            courseName = args.getString("course_name");
            courseCode = args.getString("course_code");
            Log.d("ClassListFragment","course name: " + courseName);
            Log.d("ClassListFragment","course code: " + courseCode);
        }

        classList = db.getClasses(courseCode);

        Log.d("ClassListFragment","classList size: " + classList.size());

        ClassListViewAdapter adapter = new ClassListViewAdapter(getContext(),classList, this::onClickListener);
        classListRecyclerView.setAdapter(adapter);

        //  triggered when add class button is pressed
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // show dialog confirmation box
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setMessage("Create New Class?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // add new object into the list, then trigger update to the adapter to update recycler view
                        Integer size = classList.size()+1;
                        String className = "Class " + size;
                        String classID = "AT" + courseCode + size;
                        String datetime ="";
                        ClassListItem tempclass = new ClassListItem(className, classID,datetime);
                        classList.add(tempclass);
                        adapter.notifyDataSetChanged();

                        // go to the attendance list fragment, setting title of fragment holder
                        TextView title = getActivity().findViewById(R.id.fragmentholdertitle);
                        title.setText(classID + " - " + className);

                        // send data to attendance list fragment
                        Bundle args = new Bundle();
                        args.putString("class_id",classID);
                        args.putString("class_name",className);
                        args.putString("course_ID",courseCode);

                        AttendanceListFragment attendanceListFragment = new AttendanceListFragment();

                        attendanceListFragment.setArguments(args);

                        // trigger fragment transaction to switch fragments, add to back stack for back buttons
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.classlistframelayout, attendanceListFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
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
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    // on resume is triggered when back is pressed. update title to match the current page
    @Override
    public void onResume() {
        super.onResume();
        TextView title = getActivity().findViewById(R.id.fragmentholdertitle);
        title.setText(courseCode + " - " + courseName);
    }
    // this function is triggered when the button in the recycler view is triggered
    @Override
    public void onClickListener(int position) {

        //set title in fragment holder
        TextView title = getActivity().findViewById(R.id.fragmentholdertitle);
        title.setText(classList.get(position).getClassID() + " - " + classList.get(position).getClassName());

        // send data to attendance view fragment
        Bundle args = new Bundle();
        args.putString("class_id",classList.get(position).getClassID());
        args.putString("class_name",classList.get(position).getClassName());
        args.putString("course_name", courseName);

        AttendanceViewFragment attendanceviewFragment = new AttendanceViewFragment();

        attendanceviewFragment.setArguments(args);

        // trigger fragment transaction to switch fragments, add to back stack for back buttons
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.classlistframelayout, attendanceviewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}