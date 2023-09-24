//this helps represent a single item in the recycler view
package com.example.tapat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView recyclerViewTitle;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        recyclerViewTitle = itemView.findViewById(R.id.recycle_view_item_title);
    }
}
