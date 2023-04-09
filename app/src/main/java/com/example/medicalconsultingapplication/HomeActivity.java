package com.example.medicalconsultingapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.medicalconsultingapplication.adapter.AdapterSlider.IllnessAdapter;
import com.example.medicalconsultingapplication.model.Illness;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements IllnessAdapter.ItemClickListener {
    ArrayList<Illness> items = new ArrayList<>();
TextView textView;
    IllnessAdapter illnessAdapter;
    LinearLayoutManager layoutManagerIllness = new LinearLayoutManager(this);
    RecyclerView rvIllness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rvIllness = findViewById(R.id.rvIllnesses);
        textView = findViewById(R.id.textView);
  //      textView.setTextColor(ContextCompat.getColor(this,R.color.red));
        items.add(new Illness("1", R.drawable.heart, "أمراض القلب"));
        items.add(new Illness("2", R.drawable.heart, "أمراض القلب"));
        items.add(new Illness("3", R.drawable.heart, "أمراض القلب"));
        items.add(new Illness("4", R.drawable.heart, "أمراض القلب"));
        items.add(new Illness("5", R.drawable.heart, "أمراض القلب"));
        layoutManagerIllness.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvIllness.setLayoutManager(layoutManagerIllness);
        illnessAdapter = new IllnessAdapter(this, items, this);
        rvIllness.setAdapter(illnessAdapter);
        Log.e("ayat", "" + items);
    }

    @Override
    public void onItemClick(int position, String id) {

    }
}