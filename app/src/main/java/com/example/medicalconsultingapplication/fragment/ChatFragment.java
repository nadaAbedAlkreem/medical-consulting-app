package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicalconsultingapplication.R;

public class ChatFragment extends Fragment {
    SwipeRefreshLayout refreshFriend;
    Button btnFind;
    RecyclerView rvFriend;
    int idAuthDoctor;
    RecyclerView rvActive;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        refreshFriend = view.findViewById(R.id.refreshFriend);
        btnFind = view.findViewById(R.id.btnFind);
        rvFriend = view.findViewById(R.id.rvFriend);
        rvActive= view.findViewById(R.id.rvActive);
        assert getArguments() != null;
        idAuthDoctor = getArguments().getInt("idAuthDoctor");
        if (idAuthDoctor == 1){
            btnFind.setVisibility(View.GONE);
        }
        btnFind.setOnClickListener(v -> {
     // go to the page have all user
        });
        return view;
    }
}