package com.example.medicalconsultingapplication.operationConsuting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.medicalconsultingapplication.R;


public class AddConsultionActivity extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_add_consultion);
            }


            private void swipe(Fragment fragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,
                        fragment).addToBackStack("").commit();
            }

        }
    
        