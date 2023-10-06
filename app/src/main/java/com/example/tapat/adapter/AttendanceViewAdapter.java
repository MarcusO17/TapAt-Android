package com.example.tapat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;
import com.example.tapat.model.AttendanceListRowData;

import java.util.ArrayList;
import java.util.List;

public class AttendanceViewAdapter extends RecyclerView.Adapter<AttendanceViewAdapter.ViewHolder>{
    List<AttendanceListRowData> attendanceList = new ArrayList<>();
    public AttendanceViewAdapter(List<AttendanceListRowData> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public AttendanceViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_view_recycler_item, parent, false);
        return new AttendanceViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(attendanceList.get(position).getStudentName());
        holder.reasonTextView.setText(attendanceList.get(position).getReason());
        holder.attendanceCheckBox.setChecked(attendanceList.get(position).isAttendance());
        holder.attendanceCheckBox.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView reasonTextView;
        CheckBox attendanceCheckBox;
        int position = getAdapterPosition();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.attendance_view_title);
            reasonTextView = itemView.findViewById(R.id.reasontextview);
            attendanceCheckBox = itemView.findViewById(R.id.attendanceviewcheckbox);

        }
    }
}
