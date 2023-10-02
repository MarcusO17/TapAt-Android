// for creating the items inside the recycler view
// if you want to fetch data from the database and use recyclerview i assume you get the data into a list
// before throwing it into this adapter constructor to populate the items in the recycler view
package com.example.tapat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;
import com.example.tapat.model.CourseItem;

import java.util.ArrayList;
import java.util.List;

public class CourseItemViewAdapter extends RecyclerView.Adapter<CourseItemViewAdapter.ViewHolder> {

    List<CourseItem> courseItemList;
    OnClickListener onClickListener;

    public CourseItemViewAdapter(List<CourseItem> items, OnClickListener onClickListener) {
        this.courseItemList = items;
        this.onClickListener = onClickListener;
    }
    //creating the items in the recycler view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup holder, int position) {

        View view = LayoutInflater.from(holder.getContext()).inflate(R.layout.recycler_view_item,holder,false);
        return new ViewHolder(view, onClickListener);
    }

    //setting the title of the item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseItem courseitem = courseItemList.get(position);
        String coursename = courseItemList.get(position).getName();
        String coursecode = courseItemList.get(position).getCourseCode();

        holder.title.setText(coursecode + "-" + coursename);


    }
    @Override
    public int getItemCount() {
        return courseItemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        OnClickListener onClickListener;
        public ViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.recycle_view_item_title);
            this.onClickListener = onClickListener;

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onClickListener.onClickListener(getAdapterPosition());
        }
    }
    public interface OnClickListener{
        void onClickListener(int position);
    }
}