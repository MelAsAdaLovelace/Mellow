package com.mellow.mellow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
    Button registerBtn, signInBtn;
    ImageView hareImg;
    TextView mellowText, signInText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        signInBtn = findViewById(R.id.loginLoginBtn);
        registerBtn = findViewById(R.id.loginRegisterBtn);
        hareImg = findViewById(R.id.loginHare);
        mellowText = findViewById(R.id.loginTextWelcome);
        signInText = findViewById(R.id.loginSignText);
        username = findViewById(R.id.loginLayoutUsername);
        password = findViewById(R.id.loginLayoutPassword);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(Login.this, UserProfile.class);
                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(hareImg, "logo_image");
                pairs[1] = new Pair<View, String>(mellowText, "logo_text");
                pairs[2] = new Pair<View, String>(signInText, "logo_descr");
                pairs[3] = new Pair<View, String>(password, "trans_password");
                pairs[4] = new Pair<View, String>(signInBtn, "trans_go_btn");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(profileIntent, options.toBundle());
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(hareImg, "logo_image");
                pairs[1] = new Pair<View, String>(mellowText, "logo_text");
                pairs[2] = new Pair<View, String>(signInText, "logo_descr");
                pairs[3] = new Pair<View, String>(username, "trans_username");
                pairs[4] = new Pair<View, String>(password, "trans_password");
                pairs[5] = new Pair<View, String>(signInBtn, "trans_go_btn");
                pairs[6] = new Pair<View, String>(registerBtn, "trans_reglog_btn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });


    }
}
