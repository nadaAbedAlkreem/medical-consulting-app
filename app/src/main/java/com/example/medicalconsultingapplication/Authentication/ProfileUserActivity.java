package com.example.medicalconsultingapplication.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationAdapter;
import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.model.Consultation;

import java.util.ArrayList;

public class ProfileUserActivity extends AppCompatActivity  implements ConsultationProfileAdapter.ItemClickListener  {
    ArrayList<Consultation> items = new ArrayList<>();
    ConsultationProfileAdapter consultationProfileAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);


    RecyclerView reDoctorConsultationsProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        reDoctorConsultationsProfile = findViewById(R.id.reDoctorConsultationsProfile);
        items.add(new Consultation("1", "عنوان الاستشارة ", R.drawable.heart));
        items.add(new Consultation("2", "عنوان الاستشارة ", R.drawable.heart ));
        items.add(new Consultation("3", "عنوان الاستشارة ", R.drawable.heart  ));
        items.add(new Consultation("4", "عنوان الاستشارة ", R.drawable.heart   ));
        items.add(new Consultation("5", "عنوان الاستشارة ", R.drawable.heart  ));
        items.add(new Consultation("6", "عنوان الاستشارة ", R.drawable.heart   ));
        items.add(new Consultation("7", "عنوان الاستشارة ", R.drawable.heart  ));
        items.add(new Consultation("8", "عنوان الاستشارة ", R.drawable.heart  ));
          reDoctorConsultationsProfile.setLayoutManager(staggeredGridLayoutManager);
        consultationProfileAdapter = new ConsultationProfileAdapter(this, items, this );
        reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);




    }

    @Override
    public void onItemClickList(int position, String id) {
        Toast.makeText(this, "idfg", Toast.LENGTH_SHORT).show();

    }

}