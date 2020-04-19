package com.mellow.mellow;

import android.content.Context;
import android.content.Intent;
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

    public AssignmentAdapter(Context context, ArrayList<AssignmentItemHelper> assignments) {
        this.context = context;
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssignmentViewHolder(LayoutInflater.from(context).inflate(R.layout.assignment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AssignmentViewHolder holder, int position) {
        AssignmentItemHelper helper = assignments.get(position);
        holder.title.setText(helper.getTitle());
        holder.descr.setText(helper.getDescr());
        holder.date.setText(helper.getDue());

        final String titleText = assignments.get(position).getTitle();
        final String descrText = assignments.get(position).getDescr();
        final String dueText = assignments.get(position).getDue();
        final String assid = assignments.get(position).getAssID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AssignmentCardDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", titleText);
                intent.putExtra("descr", descrText);
                intent.putExtra("duedate", dueText);
                intent.putExtra("assID", assid);
                context.startActivity(intent);
            }
        });
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
