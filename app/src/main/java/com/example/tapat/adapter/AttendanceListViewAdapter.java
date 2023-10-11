package com.example.tapat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;
import com.example.tapat.model.AttendanceListRowData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttendanceListViewAdapter extends RecyclerView.Adapter<AttendanceListViewAdapter.ViewHolder>{

    List<AttendanceListRowData> attendanceList = new ArrayList<>();

    public AttendanceListViewAdapter(List<AttendanceListRowData> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AttendanceListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_recycler_view_item, parent, false);
        return new AttendanceListViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceListViewAdapter.ViewHolder holder, int position) {

        holder.title.setText(attendanceList.get(position).getStudentName());
        if(attendanceList.get(position).isAttendance()) {
            holder.attendanceCheckBox.setChecked(true);
        }else{
            holder.attendanceCheckBox.setChecked(false);
        }

        List<String> dropDownMenuItems = Arrays.asList("No Reason","Late", "MC");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                holder.itemView.getContext(),
                android.R.layout.simple_spinner_item,
                dropDownMenuItems
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.dropDownBox.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public void filteredList(List<AttendanceListRowData> list) {
        attendanceList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        Spinner dropDownBox;
        CheckBox attendanceCheckBox;
        int position = getAdapterPosition();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recycle_view_attendance_title);
            dropDownBox = itemView.findViewById(R.id.reasondropdownbox);
            attendanceCheckBox = itemView.findViewById(R.id.attendancecheckbox);

            attendanceCheckBox.setOnCheckedChangeListener((checkbox, isChecked) ->{
                if(position != RecyclerView.NO_POSITION){
                    attendanceList.get(position).setAttendance(isChecked);
                }
            });

            dropDownBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        AttendanceListRowData rowData = attendanceList.get(adapterPosition);
                        if (adapterView.getSelectedItem() != null){
                            String selectedItem = adapterView.getSelectedItem().toString();
                            rowData.setReason(selectedItem);
                        }
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}
