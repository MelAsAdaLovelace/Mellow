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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mellow.mellow.Helpers.AssignmentItemHelper;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class NewAssignmentForm extends AppCompatActivity {
    TextInputLayout title, descr;
    TextView due;
    Button saveAss, cancelAss;
    DatabaseReference mReference;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;
    long assId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assignment_form);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title = findViewById(R.id.ass_form_title_input_layout);
        descr = findViewById(R.id.ass_form_descr_input_layout);
        due = findViewById(R.id.ass_form_date_input);
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

        saveAss = findViewById(R.id.create_new_ass_btn);
        cancelAss = findViewById(R.id.cancel_new_ass_btn);


    }

    private void pickATime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(
                NewAssignmentForm.this,
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
                NewAssignmentForm.this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void createNewAssignment(View view) {
        final String titleText = title.getEditText().getText().toString();
        final String descrText = descr.getEditText().getText().toString();
        final String dueText = due.getText().toString();

        if (titleText.length() == 0) {
            title.setError("Title is mandatory");
        } else {
            if (titleText.length() <= title.getCounterMaxLength() && descrText.length() <= descr.getCounterMaxLength()) {
                SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                String userid = userInfo.getString("userid", "");
                Date date = new Date();
                assId = date.getTime();
                final String assID = "ass" + assId;
                mReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("assignments").child(assID);
                mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mReference.setValue(new AssignmentItemHelper(titleText, descrText, dueText, assID));
                        startActivity(new Intent(NewAssignmentForm.this, Assignments.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

    }

    public void cancelTheAssignment(View view) {
        startActivity(new Intent(NewAssignmentForm.this, Assignments.class));
        finish();
    }


}
