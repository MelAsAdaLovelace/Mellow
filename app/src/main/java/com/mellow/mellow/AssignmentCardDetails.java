package com.mellow.mellow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mellow.mellow.Helpers.AssignmentItemHelper;

import java.util.Random;

public class AssignmentCardDetails extends AppCompatActivity {
    TextInputLayout title, descr, due;
    Button updateAssBtn, deleteAssBtn;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_card_details);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        title = findViewById(R.id.ass_edit_title_input_layout);
        descr = findViewById(R.id.ass_edit_descr_input_layout);
        due = findViewById(R.id.ass_edit_date_input_layout);

        updateAssBtn = findViewById(R.id.ass_edit_update_btn);
        updateAssBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAssignmentDetails(v);
            }
        });
        deleteAssBtn = findViewById(R.id.ass_edit_delete_btn);
        deleteAssBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAssignment(v);
            }
        });

        //Get the saved values
        title.getEditText().setText(getIntent().getStringExtra("title"));
        descr.getEditText().setText(getIntent().getStringExtra("descr"));
        due.getEditText().setText(getIntent().getStringExtra("duedate"));

    }

    public void updateAssignmentDetails(View view) {


        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userid = userInfo.getString("userid", "");

        final String assID = getIntent().getStringExtra("assID");
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("assignments").child(assID);
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("title").setValue(title.getEditText().getText().toString());
                dataSnapshot.getRef().child("descr").setValue(descr.getEditText().getText().toString());
                dataSnapshot.getRef().child("due").setValue(due.getEditText().getText().toString());
                startActivity(new Intent(AssignmentCardDetails.this, Assignments.class));
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void deleteAssignment(View view) {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userid = userInfo.getString("userid", "");

        final String assID = getIntent().getStringExtra("assID");
        mReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("assignments").child(assID);
        mReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(AssignmentCardDetails.this, Assignments.class));
                    finish();
                }
            }
        });
    }


}
