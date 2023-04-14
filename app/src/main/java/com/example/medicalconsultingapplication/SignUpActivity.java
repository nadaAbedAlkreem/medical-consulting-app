package com.example.medicalconsultingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Spinner spinnerLanguages=findViewById(R.id.spinner_languages);
    //    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.illnesses, android.R.layout.simple_spinner_item);
     /*   adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.getBackground().setColorFilter(Color.parseColor("#2ca6ff"), PorterDuff.Mode.SRC_ATOP);

        spinnerLanguages.setAdapter(adapter);*/
    }
}