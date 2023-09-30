// for creating the items inside the recycler view
// if you want to fetch data from the database and use recyclerview i assume you get the data into a list
// before throwing it into this adapter constructor to populate the items in the recycler view
package com.example.tapat.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.ClassListFragment;
import com.example.tapat.R;
import com.example.tapat.model.CourseItem;

import java.util.List;

public class CourseItemViewAdapter extends RecyclerView.Adapter<CourseItemViewHolder> {

    List<CourseItem> courseItemList;

    public CourseItemViewAdapter(List<CourseItem> items) {
        this.courseItemList = courseItemList;
    }
    //creating the items in the recycler view
    @NonNull
    @Override
    public CourseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false));
    }

    //setting the title of the item
    @Override
    public void onBindViewHolder(@NonNull CourseItemViewHolder holder, int position) {
        holder.courseItemViewTitle.setText(courseItemList.get(position).getName());

        holder.courseItemViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseItem selectedCourse = courseItemList.get(position);

                // create the fragment here
                ClassListFragment fragment = new ClassListFragment();

                Bundle args = new Bundle();
                args.putSerializable("selected_course", selectedCourse);
                fragment.setArguments(args);

                // Replace the current fragment with StudentDetailFragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new MyFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseItemList.size();
    }
}