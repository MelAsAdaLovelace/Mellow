package com.mellow.mellow;

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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    Button registerBtn;
    ImageView hareImg;
    TextView mellowText, signInText;
    TextInputLayout username, password, fullname, email;
    Button loginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

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
        username = findViewById(R.id.registerLayoutUsername);
        password = findViewById(R.id.registerLayoutPassword);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, Login.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(hareImg, "logo_image");
                pairs[1] = new Pair<View, String>(mellowText, "logo_text");
                pairs[2] = new Pair<View, String>(signInText, "logo_descr");
                pairs[3] = new Pair<View, String>(username, "trans_username");
                pairs[4] = new Pair<View, String>(password, "trans_password");
                pairs[5] = new Pair<View, String>(registerBtn, "trans_go_btn");
                pairs[6] = new Pair<View, String>(loginBtn, "trans_reglog_btn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

    public void registerUser(View view) {
        if(!validateName() | !validateUsername() | !validateEmail() | !validatePassword()){
            return;
        }
        String name = fullname.getEditText().getText().toString();
        String user = username.getEditText().getText().toString();
        String mail = email.getEditText().getText().toString();
        String pass = password.getEditText().getText().toString();

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

    private boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            username.setError("Whitespaces are not allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
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
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
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

}
