package com.example.medicalconsultingapplication.SpalshActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.medicalconsultingapplication.Authentication.LogInActivity;
import com.example.medicalconsultingapplication.DrawerNavigationActivity;
import com.example.medicalconsultingapplication.R;
import com.google.api.Authentication;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    TextView logo_text;
    ConstraintLayout constraintlayout;
    Animation txtAnimation, layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPref =
                getSharedPreferences("loginAndLogoutOP", Context.MODE_PRIVATE);
        String login_active = sharedPref.getString(String.valueOf(R.string.LoginActive), "") ;
        boolean only_once = sharedPref.getBoolean("only_once", false) ;
        Log.e("nada" , login_active) ;

        if ( login_active != "" )
        {
            Log.e("nada" , "test")  ;
            Intent intent1 = new Intent(Splash.this, DrawerNavigationActivity.class);
            startActivity(intent1);
        }
        else

        {
            if(only_once){
                Intent intent1 = new Intent(Splash.this, LogInActivity.class);
                startActivity(intent1);
            }
            else {
                txtAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
                layoutAnimation = AnimationUtils.loadAnimation(this, R.anim.fall_down);

                logo_text = findViewById(R.id.logo);
                constraintlayout = findViewById(R.id.conMain);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        constraintlayout.setVisibility(View.VISIBLE);
                        constraintlayout.setAnimation(layoutAnimation);
                        new Handler().postDelayed(() -> {
                            logo_text.setVisibility(View.VISIBLE);
                            logo_text.setAnimation(txtAnimation);


                        }, 500);

                    }
                }, 1000);


                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }, 5000);
            }

        }}

}