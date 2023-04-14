package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.model.Consultation;

import java.util.ArrayList;

public class ProfileUserFragment extends Fragment implements ConsultationProfileAdapter.ItemClickListener {
    ArrayList<Consultation> items = new ArrayList<>();
    ConsultationProfileAdapter consultationProfileAdapter;
    RecyclerView reDoctorConsultationsProfile;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        reDoctorConsultationsProfile = view.findViewById(R.id.reDoctorConsultationsProfile);
        items.add(new Consultation("1", "عنوان الاستشارة ", R.drawable.heart));
        items.add(new Consultation("2", "عنوان الاستشارة ", R.drawable.heart));
        items.add(new Consultation("3", "عنوان الاستشارة ", R.drawable.heart));
        items.add(new Consultation("4", "عنوان الاستشارة ", R.drawable.heart));
        items.add(new Consultation("5", "عنوان الاستشارة ", R.drawable.heart));
        items.add(new Consultation("6", "عنوان الاستشارة ", R.drawable.heart));
        items.add(new Consultation("7", "عنوان الاستشارة ", R.drawable.heart));
        items.add(new Consultation("8", "عنوان الاستشارة ", R.drawable.heart));
        consultationProfileAdapter = new ConsultationProfileAdapter(getContext(), items, this);
        reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);
        return view;
    }

    @Override
    public void onItemClickList(int position, String id) {
        Toast.makeText(getContext(), "idfg", Toast.LENGTH_SHORT).show();

    }
}