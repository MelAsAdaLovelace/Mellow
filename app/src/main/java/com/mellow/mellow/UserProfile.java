package com.mellow.mellow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    SharedPreferences loggedIn;
    SharedPreferences onBoardingScreen;
    TextView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        backBtn = findViewById(R.id.profile_back);
    }

    public void logOut(View view){
        loggedIn = getSharedPreferences("loggedIn", MODE_PRIVATE);
        onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = onBoardingScreen.edit();
        SharedPreferences.Editor editor = loggedIn.edit();
        editor.putBoolean("isLoggedIn", false);
        editor2.putBoolean("firstTime", true);
        editor.commit();
        editor2.commit();
        startActivity(new Intent(UserProfile.this, MainActivity.class));
        finish();
    }

    public void goToDashboard(View view){
        startActivity(new Intent(UserProfile.this, UserDashboard.class));
        finish();
    }
}
