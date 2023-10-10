package com.example.tapat.adminfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;

import java.util.ArrayList;
import java.util.List;

public class ButtonListAdapter extends RecyclerView.Adapter<ButtonListAdapter.ViewHolder> implements Filterable {
    private final List<String> buttonData;
    private final List<String> filteredButtonData;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private String selectedButtonName;

    public ButtonListAdapter(Context context) {
        this.context = context;
        buttonData = new ArrayList<>();
        filteredButtonData = new ArrayList<>();
    }

    public void setData(List<String> data) {
        buttonData.clear();
        buttonData.addAll(data);
        filteredButtonData.clear();
        filteredButtonData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String buttonText = filteredButtonData.get(position);
        holder.button.setText(buttonText);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(buttonText);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredButtonData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Button button;

        public ViewHolder(View view) {
            super(view);
            button = view.findViewById(R.id.buttonItem);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterPattern = constraint.toString().toLowerCase().trim();
                List<String> filteredList = new ArrayList<>();

                for (String item : buttonData) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredButtonData.clear();
                filteredButtonData.addAll((List<String>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(String buttonText);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Add a method to set the selected button name
    public void setSelectedButtonName(String buttonName) {
        selectedButtonName = buttonName;
    }

    // Add a method to get the selected button name
    public String getSelectedButtonName() {
        return selectedButtonName;
    }

}

