package com.mellow.mellow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;
import com.mellow.mellow.Helpers.AssignmentHelper;
import com.mellow.mellow.Helpers.CourseHelper;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView assignmentRecycler, courseRecycler;
    RecyclerView.Adapter assignmentAdapter, courseAdapter;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        assignmentRecycler = findViewById(R.id.dash_upcoming_recycle);
        courseRecycler = findViewById(R.id.dash_courses_recycler);

        assignmentRecyler();
        courseRecyler();

        //Navigation

    }

    private void courseRecyler() {
        courseRecycler.setHasFixedSize(true);
        courseRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<CourseHelper> courseHelpers = new ArrayList<>();
        courseHelpers.add(new CourseHelper("COMP416", "Spring2020", "Melike", "11:30 - 12:45", "SNA22", "B"));
        courseHelpers.add(new CourseHelper("COMP415", "Spring2020", "Öznur", "08:30 - 09:45", "SNA22", "A"));
        courseHelpers.add(new CourseHelper("COMP491", "Spring2020", "MTS", "13:00 - 14:15", "SNA22", "C"));
        courseHelpers.add(new CourseHelper("PHIL416", "Spring2020", "Robbie", "14:30 - 15:45", "SNA22", "D"));
        courseHelpers.add(new CourseHelper("ETHR107", "Spring2020", "Hülya", "11:30 - 12:45", "SNA22", "A-"));

        courseAdapter = new CourseAdapter(courseHelpers);
        courseRecycler.setAdapter(courseAdapter);
    }

    private void assignmentRecyler() {
        assignmentRecycler.setHasFixedSize(true);
        assignmentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<AssignmentHelper> assignmentHelpers = new ArrayList<>();
        assignmentHelpers.add(new AssignmentHelper(R.drawable.relax_hare, "415 persembe dersi", "2 video izle \n Notları deftere geçir\n"));
        assignmentHelpers.add(new AssignmentHelper(R.drawable.tick, "416 persembe dersi", "2 video izle \n Notları deftere geçir\n"));
        assignmentHelpers.add(new AssignmentHelper(R.drawable.tick, "418 persembe dersi", "2 video izle \n Notları deftere geçir\n"));

        assignmentAdapter = new AssignmentsAdapter(assignmentHelpers);
        assignmentRecycler.setAdapter(assignmentAdapter);
    }
}
