// for creating the items inside the recycler view
// if you want to fetch data from the database and use recyclerview i assume you get the data into a list
// before throwing it into this adapter constructor to populate the items in the recycler view
package com.example.tapat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    List<RecyclerItem> items;

    public RecyclerViewAdapter(List<RecyclerItem> items) {
        this.items = items;
    }
    //creating the items in the recycler view
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false));
    }

    //setting the title of the item
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.recyclerViewTitle.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
