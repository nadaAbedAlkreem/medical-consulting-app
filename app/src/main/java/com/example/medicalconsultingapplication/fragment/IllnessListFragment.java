package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.AdapterSlider.ConsultationAdapter;
import com.example.medicalconsultingapplication.model.Consultation;

import java.util.ArrayList;

public class IllnessListFragment extends Fragment implements ConsultationAdapter.ItemClickListener{
    ArrayList<Consultation> items = new ArrayList<>();
    ConsultationAdapter consultationAdapter;
    RecyclerView rvIllnessesList;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_illness_list, container, false);
        rvIllnessesList = view.findViewById(R.id.rvIllnessesList);
        items.add(new Consultation("1", "ايات", R.drawable.image_doctor, "ناتشاكاسكاكاركرا"));
        items.add(new Consultation("2", "ايات", R.drawable.image_doctor, "نلتشركتلل"));
        items.add(new Consultation("3", "ايات", R.drawable.image_doctor, "تاشكراكاك"));
        items.add(new Consultation("4", "ايات", R.drawable.image_doctor, "شتابكاكا"));
        items.add(new Consultation("5", "ايات", R.drawable.image_doctor, "شنابكخاكا"));
        items.add(new Consultation("6", "ايات", R.drawable.image_doctor, "شخاكنابكخا"));
        items.add(new Consultation("7", "ايات", R.drawable.image_doctor, "خشهابايبكخاشؤ"));
        consultationAdapter = new ConsultationAdapter(getContext(), items, this);
        rvIllnessesList.setAdapter(consultationAdapter);        return view;
    }
    @Override
    public void onItemClickList(int position, String id) {

    }

    //notification
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navNotification) {
            Log.e("Ayat", "notification");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notification_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.navNotification);
        item.setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

}