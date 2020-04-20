package com.mellow.mellow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mellow.mellow.Helpers.AssignmentItemHelper;

import java.util.Calendar;
import java.util.Random;

public class AssignmentCardDetails extends AppCompatActivity {
    TextInputLayout title, descr;
    TextView due;
    Button updateAssBtn, deleteAssBtn;
    DatabaseReference mReference;

    DatePickerDialog.OnDateSetListener mDateSetListener;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_card_details);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        title = findViewById(R.id.ass_edit_title_input_layout);
        descr = findViewById(R.id.ass_edit_descr_input_layout);
        due = findViewById(R.id.ass_edit_form_date_input);
        due.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickADate();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                due.setText(dayOfMonth + "." + (month + 1) + "." + year);
                pickATime();
            }
        };
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                due.setText(due.getText() + "\n" + hourOfDay + ":" + minute);
            }
        };

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
        due.setText(getIntent().getStringExtra("duedate"));

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
                dataSnapshot.getRef().child("due").setValue(due.getText().toString());
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

    private void pickATime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(
                AssignmentCardDetails.this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                mTimeSetListener,
                hour, min, true
        );
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void pickADate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                AssignmentCardDetails.this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}
