package com.mellow.mellow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mellow.mellow.Helpers.DashboardAssignmentHelper;

import java.util.ArrayList;

public class DashboardAssignmentsAdapter extends RecyclerView.Adapter<DashboardAssignmentsAdapter.DashboardAssignmentViewHolder> {

    ArrayList<DashboardAssignmentHelper> assignments;

    public DashboardAssignmentsAdapter(ArrayList<DashboardAssignmentHelper> assignments) {
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public DashboardAssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_card_view, parent, false);
        DashboardAssignmentViewHolder dashboardAssignmentViewHolder = new DashboardAssignmentViewHolder(view);
        return dashboardAssignmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAssignmentViewHolder holder, int position) {
        DashboardAssignmentHelper dashboardAssignmentHelper = assignments.get(position);
        holder.image.setImageResource(dashboardAssignmentHelper.getImage());
        holder.title.setText(dashboardAssignmentHelper.getTitle());
        holder.descr.setText(dashboardAssignmentHelper.getDescription());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }


    public static class DashboardAssignmentViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, descr;
        public DashboardAssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.assignment_image);
            title = itemView.findViewById(R.id.assignment_title_user_input);
            descr = itemView.findViewById(R.id.assignment_descr_user_input);
        }
    }
}
