package com.mellow.mellow;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mellow.mellow.Helpers.AssignmentHelper;

import java.util.ArrayList;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentViewHolder> {

    ArrayList<AssignmentHelper> assignments;

    public AssignmentsAdapter(ArrayList<AssignmentHelper> assignments) {
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_card_view, parent, false);
        AssignmentViewHolder assignmentViewHolder = new AssignmentViewHolder(view);
        return assignmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        AssignmentHelper assignmentHelper = assignments.get(position);
        holder.image.setImageResource(assignmentHelper.getImage());
        holder.title.setText(assignmentHelper.getTitle());
        holder.descr.setText(assignmentHelper.getDescription());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }


    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, descr;
        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.assignment_image);
            title = itemView.findViewById(R.id.assignment_title_user_input);
            descr = itemView.findViewById(R.id.assignment_descr_user_input);
        }
    }
}
