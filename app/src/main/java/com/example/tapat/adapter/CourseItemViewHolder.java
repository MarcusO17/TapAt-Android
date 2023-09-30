//this helps represent a single item in the recycler view
package com.example.tapat.adapter;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;

public class CourseItemViewHolder extends RecyclerView.ViewHolder {
    Button courseItemViewTitle;

    public CourseItemViewHolder(@NonNull View itemView) {
        super(itemView);
        courseItemViewTitle = itemView.findViewById(R.id.recycle_view_item_title);
    }
}
