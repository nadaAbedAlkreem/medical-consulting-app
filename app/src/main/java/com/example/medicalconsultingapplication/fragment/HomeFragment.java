package com.example.medicalconsultingapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.DoctorAdapter;
import com.example.medicalconsultingapplication.adapter.IllnessAdapter;
import com.example.medicalconsultingapplication.model.Illness;
import com.example.medicalconsultingapplication.model.Users;

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
//    String doctorCategory = getArguments().getString("doctorCategory");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
 //illness
        SharedPreferences sharedPref =requireContext().
                getSharedPreferences("loginAndLogoutOP", Context.MODE_PRIVATE);
        boolean login_active = sharedPref.getBoolean(String.valueOf(R.string.LoginActive), false) ;
        Log.e("oo" , String.valueOf(login_active));

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

        rvDoctor = view.findViewById(R.id.rvDoctor);

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
        IllnessListFragment illnessListFragment = new IllnessListFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        Bundle data = new Bundle();
        String category = items.get(position).getNameIllness();
        Log.e("position", "" + category);
        data.putString("doctorCategory", category);//category
        illnessListFragment.setArguments(data);
        fragmentTransaction.replace(R.id.mainContainer,
                illnessListFragment).addToBackStack("").commit();
    }

}