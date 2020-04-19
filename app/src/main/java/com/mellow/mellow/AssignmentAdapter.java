package com.mellow.mellow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mellow.mellow.Helpers.AssignmentItemHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    Context context;
    ArrayList<AssignmentItemHelper> assignments;

    public AssignmentAdapter(ArrayList<AssignmentItemHelper> assignments) {
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        AssignmentViewHolder dashboardAssignmentViewHolder = new AssignmentViewHolder(view);
        return dashboardAssignmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        AssignmentItemHelper helper = assignments.get(position);
        holder.title.setText(helper.getTitle());
        holder.descr.setText(helper.getDescr());
        holder.date.setText(helper.getDueDate());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    class AssignmentViewHolder extends RecyclerView.ViewHolder{
        TextView title, descr, date;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.assignment_item_title);
            descr = itemView.findViewById(R.id.assignment_item_descr);
            date = itemView.findViewById(R.id.assignment_item_date);
        }
    }
}
