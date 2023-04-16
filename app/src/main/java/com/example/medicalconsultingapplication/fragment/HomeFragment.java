package com.example.medicalconsultingapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.IllnessAdapter;
import com.example.medicalconsultingapplication.adapter.DoctorAdapter;
 import com.example.medicalconsultingapplication.adapter.IllnessAdapter;
import com.example.medicalconsultingapplication.model.Users;
import com.example.medicalconsultingapplication.model.Users;
 import com.example.medicalconsultingapplication.model.Illness;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements IllnessAdapter.ItemClickListener, DoctorAdapter.ItemClickListener, DoctorAdapter.ItemClickListener2 {
    //illness
    ArrayList<Illness> items = new ArrayList<>();
    IllnessAdapter illnessAdapter;
    RecyclerView rvIllness;
    // doctor
    ArrayList<Users> doctorItems = new ArrayList<>();
    DoctorAdapter doctorAdapter;
    RecyclerView rvDoctor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//illness
        rvIllness = view.findViewById(R.id.rvIllnesses);
        items.add(new Illness("1", R.drawable.heart, "القلب"));
        items.add(new Illness("2", R.drawable.kidneys, "الكلى"));
        items.add(new Illness("3", R.drawable.lungs, "الرئة"));
        items.add(new Illness("4", R.drawable.digestive, "المعدة"));
        items.add(new Illness("5", R.drawable.cancer, "السرطان"));
        illnessAdapter = new IllnessAdapter(getContext(), items, this);
        rvIllness.setAdapter(illnessAdapter);
        Log.e("ayat", "" + items);
        //doctor
//         rvDoctor = findViewById(R.id.rvDoctor);
//        doctorItems.add(new Users("1", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Users("2", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Users("3", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Users("4", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Users("5", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Users("6", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Users("7", "ايات", R.drawable.image_doctor, "القلب"));
//        rvDoctor.setLayoutManager(layoutManagerDoctor);
//        doctorAdapter = new DoctorAdapter(HomeFragment.C  ,  doctorItems, this, this);

        rvDoctor = view.findViewById(R.id.rvDoctor);
//        doctorItems.add(new Doctor("1", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Doctor("2", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Doctor("3", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Doctor("4", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Doctor("5", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Doctor("6", "ايات", R.drawable.image_doctor, "القلب"));
//        doctorItems.add(new Doctor("7", "ايات", R.drawable.image_doctor, "القلب"));

        doctorAdapter = new DoctorAdapter(getContext(), doctorItems, this, this);
         rvDoctor.setAdapter(doctorAdapter);
        return view;
    }

    @Override
    public void onItemClickChat(int position, String id) {

    }

    @Override
    public void onItemClick2(int position, String id) {

    }

    @Override
    public void onItemClick(int position, String id) {

    }

    // illness
//    @Override
//     public void onItemClick(int position, String id)
//    {
//        Intent intent = new Intent(this, IllnessListActivity.class);
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//     public void onItemClick(int position, String id) {
//        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new IllnessListFragment()).addToBackStack("").commit();
//     }
//
//    // doctor
//    @Override
//     public void onItemClick2(int position, String id)
//    {
//
//     public void onItemClick2(int position, String id) {
//        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new ProfileUserFragment()).addToBackStack("").commit();
//     }
//
//    @Override
//    public void onItemClickChat(int position, String id)
//    {
//
//    }
}