package com.example.tapat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class StudentListFragment extends Fragment {
    RecyclerView studentListRecyclerView;
    List<RecyclerItem> studentList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        studentListRecyclerView.findViewById(R.id.student_list_recycler_view);

        //query and put the student into the list


        RecyclerViewAdapter studentListAdapter = new RecyclerViewAdapter(studentList);
        studentListRecyclerView.setAdapter(studentListAdapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }
}