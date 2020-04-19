package com.mellow.mellow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mellow.mellow.Helpers.AssignmentItemHelper;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Assignments extends AppCompatActivity {

    TextView pageTitle;
    DatabaseReference mReference;
    RecyclerView assignRecycle;
    ArrayList<AssignmentItemHelper> assignmentList;
    AssignmentAdapter adapter;
    SharedPreferences userInfo;
    ImageView addNewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_assignments);
        pageTitle = findViewById(R.id.ass_page_title);
        addNewBtn = findViewById(R.id.assignments_add_plus);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Assignments.this, NewAssignmentForm.class));
            }
        });

        assignRecycle = findViewById(R.id.ass_recycler);
        assignRecycle.setLayoutManager(new LinearLayoutManager(this));
//        assignRecycle.hasFixedSize();
//        assignRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        assignmentList = new ArrayList<>();
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userid = userInfo.getString("userid", "");
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("assignments");



        //SORUN BURADA
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                   AssignmentItemHelper item = dataSnapshot1.getValue(AssignmentItemHelper.class);
                   assignmentList.add(item);
                }
                adapter = new AssignmentAdapter(getBaseContext(),assignmentList);
                assignRecycle.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void goToDashboard(View view) {
        startActivity(new Intent(Assignments.this, UserDashboard.class));
        finish();
    }

    public void createAssignmentForm(View view) {
        startActivity(new Intent(Assignments.this, NewAssignmentForm.class));
    }
}
