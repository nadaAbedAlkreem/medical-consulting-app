package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.TextView;
 import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.DoctorAdapter;
import com.example.medicalconsultingapplication.adapter.IllnessAdapter;
import com.example.medicalconsultingapplication.model.Illness;
import com.example.medicalconsultingapplication.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
 import com.google.firebase.analytics.FirebaseAnalytics;
 import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class HomeFragment extends Fragment implements IllnessAdapter.ItemClickListener, DoctorAdapter.ItemClickListener {
    SwipeRefreshLayout refreshDoctor;
    //illness
    ArrayList<Illness> items = new ArrayList<>();
    IllnessAdapter illnessAdapter;
    RecyclerView rvIllness;
    // doctor
    ArrayList<Users> doctorItems;
    DoctorAdapter doctorAdapter;
    RecyclerView rvDoctor;
    FragmentTransaction fragmentTransaction;
    Bundle data = new Bundle();
    int idAuthDoctor;
    //    String doctorAuth;
    String doctorCategory;
    String doctorName;
    String doctorImage;
    //    String doctorId;
    // realtime Data base
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    String doctorId;
    String doctorAuth;
    String typeUser;
    private FirebaseAnalytics mfirebaseAnalystic;

    Calendar calendar = Calendar.getInstance() ;
    int houres  = calendar.get(Calendar.HOUR) ;
    int minutes  = calendar.get(Calendar.MINUTE) ;
    int second  = calendar.get(Calendar.SECOND) ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        refreshDoctor = view.findViewById(R.id.refreshDoctor);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        ref = database.getReference("Users");
        checkTypeUesrCurrent();
        mfirebaseAnalystic = FirebaseAnalytics.getInstance(requireActivity());
        refreshDoctor.setOnRefreshListener(() -> {
            if (refreshDoctor.isRefreshing()) {
                refreshDoctor.setRefreshing(false);
            }
            items.clear();
            getCatgories();
        });

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
        getCatgories();
        return view;
    }


    @Override
    public void onItemClick2(int position, String id) {
        ProfileUserFragment profileUserFragment = new ProfileUserFragment();
        fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        Toast.makeText(requireActivity(), id, Toast.LENGTH_SHORT).show();
        data.putString("id_doctor_home", id);
        profileUserFragment.setArguments(data);
        fragmentTransaction.replace(R.id.mainContainer, profileUserFragment).addToBackStack("").commit();
    }

    @Override
    public void onItemClick(int position, String id) {
        IllnessListFragment illnessListFragment = new IllnessListFragment();
        fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        String category = items.get(position).getNameIllness();
//        Log.e("position", "" + category);
        data.putString("doctorCategory", category);
        data.putInt("idAuthDoctor", idAuthDoctor);//category
        Log.e("TAG", "onItemClick: ClickAyat" + idAuthDoctor);
        illnessListFragment.setArguments(data);
        fragmentTransaction.replace(R.id.mainContainer,
                illnessListFragment).addToBackStack("").commit();
    }


    public void getCatgories() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Log.e("drn", "onSuccessA:");
        doctorItems = new ArrayList<>();
        ref.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (Objects.requireNonNull(snapshot.child("typeUser").getValue()).toString().equals("دكتور")) {
                    doctorCategory = Objects.requireNonNull(snapshot.child("doctorCategory").getValue()).toString();
                    doctorName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                    doctorImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                    String idAuth = Objects.requireNonNull(snapshot.child("idUserAuth").getValue()).toString() ;
                    if(!firebaseUser.getUid().equals(Objects.requireNonNull(idAuth))) {
                        Users users = new Users( idAuth, doctorName, doctorImage ,doctorCategory);
                        doctorItems.add(users);
                        doctorAdapter = new DoctorAdapter(getContext(), doctorItems, HomeFragment.this);
                        rvDoctor.setAdapter(doctorAdapter);
                        rvDoctor.setHasFixedSize(true);
                        doctorAdapter.notifyDataSetChanged();
                    }
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

    private void checkTypeUesrCurrent() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                assert firebaseUser != null;
                doctorAuth = firebaseUser.getUid();
                if (doctorAuth.equals(Objects.requireNonNull(snapshot.child("idUserAuth").getValue()).toString())) {
                    doctorId = snapshot.getKey();
                    doctorCategory = Objects.requireNonNull(snapshot.child("doctorCategory").getValue()).toString();
                    doctorName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                    doctorImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                    typeUser = Objects.requireNonNull(snapshot.child("typeUser").getValue()).toString();
                    Log.e("ayat", "C " + doctorId);
                    Log.e("ayat", "C " + doctorCategory);
                    Log.e("ayat", "N " + doctorName);
                    Log.e("ayat", "I " + doctorImage);
                    Log.e("ayat", "I " + typeUser);
                    if (snapshot.exists()) {
//                            List<DataSnapshot> list = (List<DataSnapshot>) snapshot.getChildren();
                        if (typeUser.equals("دكتور")) {
                            idAuthDoctor = 1;
                            Log.e("testDoctor", "1" + firebaseUser.getUid());
                        } else {
                            Log.e("nadaTestAuth ", "مريض  ");
                            idAuthDoctor = 0;
                            Log.e("testDoctor", "0");
                        }
                        Log.e("TAG", "onChildAdded:Ayat " + idAuthDoctor);
                    }
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
    public void onPause() {
        Calendar calendar = Calendar.getInstance();
        int houres2 = calendar.get(Calendar.HOUR);
        int minutes2 = calendar.get(Calendar.MINUTE);
        int second2 = calendar.get(Calendar.SECOND);
        int h = houres2 - houres;
        int m = minutes2 - minutes;
        int s = second2 - second;
        HashMap<String, Object> Traffic = new HashMap<>();
        Traffic.put("time", h + ":" + m + ":" + s);
        Traffic.put("screen_name", "Home");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TrackUsers")
                .add(Traffic)
                .addOnSuccessListener(documentReference -> Log.e("TAG", "Data added successfully to database"))
                .addOnFailureListener(e -> Log.e("TAG", "Failed to add database"));
        super.onPause();
    }

  }
