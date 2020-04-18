package com.mellow.mellow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    Button registerBtn;
    ImageView hareImg;
    TextView mellowText, signInText;
    TextInputLayout username, password, fullname, email;
    Button loginBtn;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loginBtn = findViewById(R.id.regLoginBtn);
        registerBtn = findViewById(R.id.regSignUpBtn);

        hareImg = findViewById(R.id.registerHare);
        mellowText = findViewById(R.id.registerTextMellow);
        signInText = findViewById(R.id.registerSignText);
        fullname = findViewById(R.id.registerLayoutFullName);
        email = findViewById(R.id.registerLayoutEmail);
        password = findViewById(R.id.registerLayoutPassword);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, Login.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(hareImg, "logo_image");
                pairs[1] = new Pair<View, String>(mellowText, "logo_text");
                pairs[2] = new Pair<View, String>(signInText, "logo_descr");
                pairs[3] = new Pair<View, String>(username, "trans_email");
                pairs[4] = new Pair<View, String>(password, "trans_password");
                pairs[5] = new Pair<View, String>(registerBtn, "trans_go_btn");
                pairs[6] = new Pair<View, String>(loginBtn, "trans_reglog_btn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

    public void registerUser(final View view) {
        if(!validateName() |  !validateEmail() | !validatePassword()){
            return;
        }
        final String name = fullname.getEditText().getText().toString();
        final String mail = email.getEditText().getText().toString();
        String pass = password.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String uid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                            UserInfo info = new UserInfo(name, mail);
                            mReference.child("Users").child(uid).child("userInfo").setValue(info);
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            email.setError("Already registered.");
                        }

                        // ...
                    }
                });

    }


    private boolean validateName() {
        String val = fullname.getEditText().getText().toString();
        if (val.isEmpty()) {
            fullname.setError("Field cannot be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailStr = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailStr)){
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-zA-Z])" +      //any letter
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private void makeToastText(View view, String text){
        Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
