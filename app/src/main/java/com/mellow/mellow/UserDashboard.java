package com.mellow.mellow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.mellow.mellow.Helpers.AssignmentHelper;
import com.mellow.mellow.Helpers.CourseHelper;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final float END_SCALE = 0.7f ;
    RecyclerView assignmentRecycler, courseRecycler;
    RecyclerView.Adapter assignmentAdapter, courseAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        assignmentRecycler = findViewById(R.id.dash_upcoming_recycle);
        courseRecycler = findViewById(R.id.dash_courses_recycler);
        menuIcon = findViewById(R.id.dash_menu);
        contentView = findViewById(R.id.dashboard_content);

        assignmentRecyler();
        courseRecyler();

        //Navigation
        drawerLayout = findViewById(R.id.dash_navi_layout);
        navigationView = findViewById(R.id.nav_view);


        navigationDraw();


    }

    private void navigationDraw() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.backgroundYellow));
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);

                }
            }
        });

        animateNavigationDraw();
        
    }

    private void animateNavigationDraw() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_logout:
                SharedPreferences loggedIn = getSharedPreferences("loggedIn", MODE_PRIVATE);
                SharedPreferences onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = onBoardingScreen.edit();
                SharedPreferences.Editor editor = loggedIn.edit();
                editor.putBoolean("isLoggedIn", false);
                editor2.putBoolean("firstTime", true);
                editor.commit();
                editor2.commit();
                startActivity(new Intent(UserDashboard.this, MainActivity.class));
                finish();
            case R.id.nav_profile:
                startActivity(new Intent(UserDashboard.this, UserProfile.class));
                finish();

        }
        return true;
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
