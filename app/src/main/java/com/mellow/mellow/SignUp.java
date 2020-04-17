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

public class SignUp extends AppCompatActivity {

    Button registerBtn;
    ImageView hareImg;
    TextView mellowText, signInText;
    TextInputLayout username, password;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginBtn = findViewById(R.id.regLoginBtn);
        registerBtn = findViewById(R.id.regSignUpBtn);
        hareImg = findViewById(R.id.registerHare);
        mellowText = findViewById(R.id.registerTextMellow);
        signInText = findViewById(R.id.registerSignText);
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
}
