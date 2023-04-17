package com.example.medicalconsultingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class LogInActivity extends AppCompatActivity {
  Button log;
    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        log=findViewById(R.id.log);
        container=findViewById(R.id.maincontainer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ConsultingFragment myFragment = new ConsultingFragment();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               swipe(myFragment);
            }
        });
    }
    private void swipe(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,
                fragment).addToBackStack("").commit();
    }

}