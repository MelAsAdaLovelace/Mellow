package com.mellow.mellow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    ImageView hare;
    TextView mellowText;
    SharedPreferences onBoardingPref;
    SharedPreferences loggedIn;
    private static int SPLASH_SCREEN = 3000; //5sec
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        hare = findViewById(R.id.mainImageView);
        mellowText = findViewById(R.id.mainTextView);

        hare.setAnimation(topAnim);
        mellowText.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                onBoardingPref = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingPref.getBoolean("firstTime", true);
                if(isFirstTime){
                    intent = new Intent(MainActivity.this, OnBoarding.class);
                    SharedPreferences.Editor editor = onBoardingPref.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();
                }else{
                    loggedIn = getSharedPreferences("loggedIn", MODE_PRIVATE);
                    boolean isLoggedIn = loggedIn.getBoolean("isLoggedIn", false);
                    if(!isLoggedIn){
                        intent = new Intent(MainActivity.this, Login.class);
                    }else{
                        intent = new Intent(MainActivity.this, UserProfile.class);
                    }

                }

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(hare, "logo_name");
                pairs[1] = new Pair<View, String>(mellowText, "logo_text");

                ActivityOptions options  = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());
                finish();
            }
        }, SPLASH_SCREEN);

    }
}
