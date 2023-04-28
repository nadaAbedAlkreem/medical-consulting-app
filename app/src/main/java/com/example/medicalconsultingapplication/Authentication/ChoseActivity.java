package com.example.medicalconsultingapplication.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.medicalconsultingapplication.R;

public class ChoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void LoginAction(View view)
    {
        Intent intent = new Intent(ChoseActivity.this  , LogInActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ChoseActivity.this).toBundle());

    }


    public void CreateAccountAction(View view)
    {
        Intent intent = new Intent(ChoseActivity.this  , SignUpActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ChoseActivity.this).toBundle());

    }
}