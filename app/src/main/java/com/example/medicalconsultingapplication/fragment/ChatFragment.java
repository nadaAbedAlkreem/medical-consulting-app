package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicalconsultingapplication.ChatActivty;
import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ActiveAdapter;
import com.example.medicalconsultingapplication.adapter.FriendAdapter;
import com.example.medicalconsultingapplication.adapter.IllnessAdapter;
import com.example.medicalconsultingapplication.model.Illness;
import com.example.medicalconsultingapplication.model.Requests;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class ChatFragment extends Fragment implements FriendAdapter.ItemClickListener , ActiveAdapter.ItemClickListener{
    //Active
    ArrayList<Requests> items = new ArrayList<>();
    ActiveAdapter activeAdapter;
    RecyclerView rvActive;


    SwipeRefreshLayout refreshFriend;
    Button btnFind;
    FriendAdapter FriendAdapter;
    RecyclerView rvFriend;
    int idAuthDoctor;
    String doctorAuth;

    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<Requests> requestsItems;
    String friendName;
    String friendImage;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        refreshFriend = view.findViewById(R.id.refreshFriend);
        btnFind = view.findViewById(R.id.btnFind);
        rvFriend = view.findViewById(R.id.rvFriend);
        rvActive = view.findViewById(R.id.rvActive);
        database = FirebaseDatabase.getInstance();
        // Active
        rvActive = view.findViewById(R.id.rvActive);
        items.add(new Requests("1", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("2", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("3", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("4", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("5", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("6", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("7", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("8", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("9", R.drawable.image_doctor, "القلب"));
        items.add(new Requests("10", R.drawable.image_doctor, "القلب"));

        activeAdapter = new ActiveAdapter(getContext(), items, this);
        rvActive.setAdapter(activeAdapter);
        //Friend
        ref = database.getReference("Chat Requests");
        assert getArguments() != null;
        idAuthDoctor = getArguments().getInt("idAuthDoctor");
        doctorAuth = getArguments().getString("doctorAuth");
        Log.d("TAG", "onCreateView: " + doctorAuth);
        if (idAuthDoctor == 1) {
            btnFind.setVisibility(View.GONE);
        }
        refreshFriend.setOnRefreshListener(() -> {
            if (refreshFriend.isRefreshing()) {
                refreshFriend.setRefreshing(false);
            }
            requestsItems.clear();
            getFriends();
        });
        btnFind.setOnClickListener(v -> {
            // go to the page have all user
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,
                    new ShowChateUserFragment()).addToBackStack("").commit();
        });
        getFriends();
        return view;
    }


    public void getFriends() {
        Log.e("drn", "onSuccessA:");
        requestsItems = new ArrayList<>();
        ref.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // note
                if (Objects.requireNonNull(snapshot.child("idSend").getValue()).toString().equals(doctorAuth) && (snapshot.child("status").getValue()).toString().equals("accept")) {
                    friendName = Objects.requireNonNull(snapshot.child("nameReviver").getValue()).toString();
                    friendImage = Objects.requireNonNull(snapshot.child("imageReciver").getValue()).toString();

                    Requests requests = new Requests("", "", friendName, friendImage);
                    requestsItems.add(requests);
                    FriendAdapter = new FriendAdapter(getContext(), requestsItems, ChatFragment.this);
                    rvFriend.setAdapter(FriendAdapter);
                    rvFriend.setHasFixedSize(true);
                    FriendAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClickChat(int position, String id) {
        // friend chat
        startActivity(new Intent(requireActivity(), ChatActivty.class));
    }

    @Override
    public void onItemClick(int position, String id) {
        // active chat
    }
}