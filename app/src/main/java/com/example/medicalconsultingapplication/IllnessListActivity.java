package com.example.medicalconsultingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.medicalconsultingapplication.adapter.ConsultationAdapter;
import com.example.medicalconsultingapplication.model.Consultation;


import java.util.ArrayList;

public class IllnessListActivity extends AppCompatActivity implements ConsultationAdapter.ItemClickListener {
    ArrayList<Consultation> items = new ArrayList<>();
    ConsultationAdapter consultationAdapter;
    LinearLayoutManager layoutManagerList = new LinearLayoutManager(this);
    RecyclerView rvIllnessesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness_list);
        rvIllnessesList = findViewById(R.id.rvIllnessesList);
        items.add(new Consultation("1", "ايات", R.drawable.image_doctor, "ناتشاكاسكاكاركرا"));
        items.add(new Consultation("2", "ايات", R.drawable.image_doctor, "نلتشركتلل"));
        items.add(new Consultation("3", "ايات", R.drawable.image_doctor, "تاشكراكاك"));
        items.add(new Consultation("4", "ايات", R.drawable.image_doctor, "شتابكاكا"));
        items.add(new Consultation("5", "ايات", R.drawable.image_doctor, "شنابكخاكا"));
        items.add(new Consultation("6", "ايات", R.drawable.image_doctor, "شخاكنابكخا"));
        items.add(new Consultation("7", "ايات", R.drawable.image_doctor, "خشهابايبكخاشؤ"));
        rvIllnessesList.setLayoutManager(layoutManagerList);
        consultationAdapter = new ConsultationAdapter(this, items, this);
        rvIllnessesList.setAdapter(consultationAdapter);
    }

    @Override
    public void onItemClickList(int position, String id) {

    }
}