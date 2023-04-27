package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.operationConsulting.AddConsultionActivity;
import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.model.Consultation;
import com.example.medicalconsultingapplication.model.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProfileUserFragment extends Fragment implements ConsultationProfileAdapter.ItemClickListener {
    ArrayList<Consultation> items = new ArrayList<>();
    ArrayList<Users> data = new ArrayList<>();
    ConsultationProfileAdapter consultationProfileAdapter;
    FloatingActionButton fAdd;
    String doctorName;
    String doctorImage;
    RecyclerView reDoctorConsultationsProfile;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        fAdd = view.findViewById(R.id.fAdd);
        reDoctorConsultationsProfile = view.findViewById(R.id.reDoctorConsultationsProfile);
        assert getArguments() != null;
        int idAuthDoctor = getArguments().getInt("idAuthDoctor");
        String doctorId = getArguments().getString("doctorId");
        String doctorAuth = getArguments().getString("doctorAuth");
        String doctorCategory = getArguments().getString("doctorCategory");
        if (idAuthDoctor == 1) {
             doctorName = getArguments().getString("userName");
             doctorImage = getArguments().getString("userImage");
        }
        Log.e("messageNada", String.valueOf(idAuthDoctor));
        Log.e("messageNada", String.valueOf(doctorId));
        if (idAuthDoctor != 1) {
            fAdd.setVisibility(View.GONE);
        }
//        items.add(new Consultation("1", "عنوان الاستشارة ", R.drawable.heart));
//        items.add(new Consultation("2", "عنوان الاستشارة ", R.drawable.heart));
//        items.add(new Consultation("3", "عنوان الاستشارة ", R.drawable.heart));
//        items.add(new Consultation("4", "عنوان الاستشارة ", R.drawable.heart));
//        items.add(new Consultation("5", "عنوان الاستشارة ", R.drawable.heart));
//        items.add(new Consultation("6", "عنوان الاستشارة ", R.drawable.heart));
//        items.add(new Consultation("7", "عنوان الاستشارة ", R.drawable.heart));
//        items.add(new Consultation("8", "عنوان الاستشارة ", R.drawable.heart));
        consultationProfileAdapter = new ConsultationProfileAdapter(getContext(), items, this);
        reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);
        fAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddConsultionActivity.class);
            intent.putExtra("doctorId", doctorId);
            intent.putExtra("doctorAuth", doctorAuth);
            intent.putExtra("doctorCategory", doctorCategory);
            intent.putExtra("userName", doctorName);
            intent.putExtra("userImage", doctorImage);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onItemClickList(int position, String id) {

    }
}