package com.example.tapat.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;
import com.example.tapat.model.ClassListItem;

import java.util.ArrayList;
import java.util.List;

public class ClassListViewAdapter extends RecyclerView.Adapter<ClassListViewAdapter.ViewHolder>{

    List<ClassListItem> classList = new ArrayList<>();

    OnClickListener onclickListener;


    public ClassListViewAdapter(List<ClassListItem> classList, OnClickListener onclickListener) {
        this.classList = classList;
        this.onclickListener = onclickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view, onclickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassListItem classListItem = classList.get(position);

        String className = classList.get(position).getClassName();
        String classID = classList.get(position).getClassID();

        holder.title.setText(classID + "-" + className);
    }

    @Override
    public int getItemCount() {
        return 0;
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
