package com.mellow.mellow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mellow.mellow.Helpers.AssignmentItemHelper;

import java.util.Random;

public class NewAssignmentForm extends AppCompatActivity {
    TextInputLayout title, descr, due;
    Button saveAss, cancelAss;
    DatabaseReference mReference;
    long assId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assignment_form);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title = findViewById(R.id.ass_form_title_input_layout);
        descr = findViewById(R.id.ass_form_descr_input_layout);
        due = findViewById(R.id.ass_form_date_input_layout);

        saveAss = findViewById(R.id.create_new_ass_btn);
        cancelAss = findViewById(R.id.cancel_new_ass_btn);


    }

    public void createNewAssignment(View view) {
        final String titleText = title.getEditText().getText().toString();
        final String descrText = descr.getEditText().getText().toString();
        final String dueText =  due.getEditText().getText().toString();

        if(titleText.length() == 0){
            title.setError("Title is mandatory");
        }else {
            if(titleText.length() <= title.getCounterMaxLength() && descrText.length() <= descr.getCounterMaxLength() && dueText.length() <= due.getCounterMaxLength() ){
                SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                String userid = userInfo.getString("userid", "");
                assId = new Random().nextInt();
                final String assID = "ass" + assId;
                mReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid).child("assignments").child(assID);
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mReference.setValue(new AssignmentItemHelper(titleText, descrText,dueText, assID));
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
