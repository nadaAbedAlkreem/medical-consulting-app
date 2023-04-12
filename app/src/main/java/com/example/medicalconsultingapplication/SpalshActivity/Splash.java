package com.example.medicalconsultingapplication.SpalshActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.medicalconsultingapplication.R;

public class Splash extends AppCompatActivity {

    TextView logo_text;
    ConstraintLayout constraintlayout;
    Animation txtAnimation, layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        layoutAnimation = AnimationUtils.loadAnimation(this, R.anim.fall_down);


        logo_text = findViewById(R.id.logo);
        constraintlayout = findViewById(R.id.conMain);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                constraintlayout.setVisibility(View.VISIBLE);
                constraintlayout.setAnimation(layoutAnimation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logo_text.setVisibility(View.VISIBLE);
                        logo_text.setAnimation(txtAnimation);


                    }
                }, 500);

            }
        }, 1000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        }, 5000);


    }
}