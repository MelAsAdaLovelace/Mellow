package com.mellow.mellow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class UserProfile extends AppCompatActivity {
    SharedPreferences loggedIn;
    SharedPreferences onBoardingScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
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
}
