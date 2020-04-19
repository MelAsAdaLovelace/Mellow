package com.mellow.mellow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

public class Assignments extends AppCompatActivity {

    TextView pageTitle;
    DatabaseReference mReference;
    RecyclerView assignRecycle;
    ArrayList<AssignmentItemHelper> assignmentList;
    AssignmentAdapter adapter;
    SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_assignments);
        pageTitle = findViewById(R.id.ass_page_title);

        assignRecycle = findViewById(R.id.ass_recycler);
        assignRecycle.hasFixedSize();
        assignRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        assignRecycle.setLayoutManager(new LinearLayoutManager(this));

/*    assignmentList = new ArrayList<>();

        adapter = new AssignmentAdapter(assignmentList);
        assignRecycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
        assignmentList = new ArrayList<>();
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userid = userInfo.getString("userid", "");
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("assignments");
      /*  assignmentList.add(new AssignmentItemHelper("COMP416", "Spring2020", "Melike"));
        assignmentList.add(new AssignmentItemHelper("COMP415", "Spring2020", "Öznur"));
        assignmentList.add(new AssignmentItemHelper("COMP491", "Spring2020", "MTS"));


        adapter = new AssignmentAdapter(assignmentList);
        assignRecycle.setAdapter(adapter);*/


        //SORUN BURADA
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                   AssignmentItemHelper item = dataSnapshot1.getValue(AssignmentItemHelper.class);
                   assignmentList.add(item);
                }
                adapter = new AssignmentAdapter(assignmentList);
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
}
