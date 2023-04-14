package com.example.medicalconsultingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.example.medicalconsultingapplication.adapter.DoctorAdapter;
import com.example.medicalconsultingapplication.adapter.AdapterSlider.IllnessAdapter;
import com.example.medicalconsultingapplication.model.Doctor;
import com.example.medicalconsultingapplication.model.Illness;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements IllnessAdapter.ItemClickListener, DoctorAdapter.ItemClickListener, DoctorAdapter.ItemClickListener2 {
    //illness
    ArrayList<Illness> items = new ArrayList<>();
    IllnessAdapter illnessAdapter;
    LinearLayoutManager layoutManagerIllness = new LinearLayoutManager(this);
    RecyclerView rvIllness;
    // doctor
    ArrayList<Doctor> doctorItems = new ArrayList<>();
    DoctorAdapter doctorAdapter;
    LinearLayoutManager layoutManagerDoctor = new LinearLayoutManager(this);
    RecyclerView rvDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //illness
        rvIllness = findViewById(R.id.rvIllnesses);
        items.add(new Illness("1", R.drawable.heart, "القلب"));
        items.add(new Illness("2", R.drawable.kidneys, "الكلى"));
        items.add(new Illness("3", R.drawable.lungs, "الرئة"));
        items.add(new Illness("4", R.drawable.digestive, "المعدة"));
        items.add(new Illness("5", R.drawable.cancer, "السرطان"));
        layoutManagerIllness.setOrientation(LinearLayoutManager.HORIZONTAL);
         rvIllness.setLayoutManager(layoutManagerIllness);
        illnessAdapter = new IllnessAdapter(this, items, this);
        rvIllness.setAdapter(illnessAdapter);
        Log.e("ayat", "" + items);
        //doctor
        rvDoctor = findViewById(R.id.rvDoctor);
        doctorItems.add(new Doctor("1", "ايات", R.drawable.image_doctor, "القلب"));
        doctorItems.add(new Doctor("2", "ايات", R.drawable.image_doctor, "القلب"));
        doctorItems.add(new Doctor("3", "ايات", R.drawable.image_doctor, "القلب"));
        doctorItems.add(new Doctor("4", "ايات", R.drawable.image_doctor, "القلب"));
        doctorItems.add(new Doctor("5", "ايات", R.drawable.image_doctor, "القلب"));
        doctorItems.add(new Doctor("6", "ايات", R.drawable.image_doctor, "القلب"));
        doctorItems.add(new Doctor("7", "ايات", R.drawable.image_doctor, "القلب"));
        rvDoctor.setLayoutManager(layoutManagerDoctor);
        doctorAdapter = new DoctorAdapter(this, doctorItems, this, this);
        rvDoctor.setAdapter(doctorAdapter);
    }

    // illness
    @Override
    public void onItemClick(int position, String id) {
        Intent intent = new Intent(this, IllnessListActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    // doctor
    @Override
    public void onItemClick2(int position, String id) {

    }

    @Override
    public void onItemClickChat(int position, String id) {

    }
}