package com.mellow.mellow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mellow.mellow.Helpers.CourseHelper;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    ArrayList<CourseHelper> courses;

    public CourseAdapter(ArrayList<CourseHelper> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_card_view, parent, false);
        CourseViewHolder assignmentViewHolder = new CourseViewHolder(view);
        return assignmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseHelper assignmentHelper = courses.get(position);
        holder.title.setText(assignmentHelper.getTitle());
        holder.semester.setText(assignmentHelper.getSemester());
        holder.instructor.setText(assignmentHelper.getInstructor());
        holder.time.setText(assignmentHelper.getTime());
        holder.classroom.setText(assignmentHelper.getClassroom());
        holder.grade.setText(assignmentHelper.getGrade());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView title, semester, instructor,time, classroom, grade;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.courses_card_title_text);
            semester = itemView.findViewById(R.id.courses_card_semester_name);
            instructor = itemView.findViewById(R.id.courses_card_instructor_name);
            time = itemView.findViewById(R.id.courses_card_time);
            classroom = itemView.findViewById(R.id.courses_card_classroom);
            grade = itemView.findViewById(R.id.courses_card_grade_predicted);
        }
    }
}




