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
import android.widget.Toast;

import com.example.tapat.adapter.AttendanceViewAdapter;
import com.example.tapat.helpers.CSVGenerator;
import com.example.tapat.helpers.dbHelper;
import com.example.tapat.model.AttendanceListRowData;
import com.example.tapat.model.StudentItem;

import java.util.ArrayList;
import java.util.List;

public class AttendanceViewFragment extends Fragment {

    View view;
    List<AttendanceListRowData> attendanceList = new ArrayList<>();
    String className;
    String classID;
    String courseName;
    AttendanceViewAdapter attendanceListAdapter;
    dbHelper db;
    EditText searchbar;
    Button exportasCSVButton;

    public AttendanceViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_attendance_view, container, false);
        // the recycler view is made and the contents of the recycler view is populated using the adapter
        RecyclerView attendanceListRecyclerView = view.findViewById(R.id.attendanceviewrecyclerview);
        LinearLayoutManager attendanceListLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        attendanceListRecyclerView.setLayoutManager(attendanceListLayout);

        //init DB

        db = new dbHelper(getContext());

        Bundle args = getArguments();

        searchbar = view.findViewById(R.id.attendanceviewsearchbar);
        // search bar updates recyclerview by notifying the adapter
        // search bar will trigger update every text change
        searchbar.addTextChangedListener(new TextWatcher() {
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

        exportasCSVButton = view.findViewById(R.id.exportascsv);
        exportasCSVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setMessage("Export Attendance List as CSV?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        CSVGenerator csvGenerator = new CSVGenerator();
                        csvGenerator.generateCSV(getActivity(),attendanceList,courseName,classID);
                        String toastString = "FILE IS GENERATED AT DOWNLOADS FOLDER";
                        Log.d("TOAST STRING", toastString);
                        Toast.makeText(getActivity(),toastString, Toast.LENGTH_LONG).show();
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

        // get data
        if (args != null) {
            className = args.getString("class_id");
            classID = args.getString("class_name");
            courseName = args.getString("course_name");
            Log.d("ClassListFragment", "class name: " + className);
            Log.d("ClassListFragment", "class id: " + classID);
            Log.d("ClassListFragment", "course name: " + courseName);
        }

        attendanceList = db.getPastAttendanceData(className);
        attendanceListAdapter = new AttendanceViewAdapter(attendanceList);

        attendanceListRecyclerView.setAdapter(attendanceListAdapter);

        return view;

    }

    // updating title to match fragment
    // usually triggered for on back
    @Override
    public void onResume() {
        super.onResume();
        TextView title = getActivity().findViewById(R.id.fragmentholdertitle);
        title.setText(className +" - " + classID);
    }
    // search bar filtering recyclerview function
    private void filter(String text){
        ArrayList<AttendanceListRowData> filteredList= new ArrayList<>();
        for (AttendanceListRowData item: attendanceList) {
            if (item.getStudentName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        attendanceListAdapter.filteredList(filteredList);

    }
}