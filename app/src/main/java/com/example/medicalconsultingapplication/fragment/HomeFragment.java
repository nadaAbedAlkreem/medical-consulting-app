package com.example.medicalconsultingapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.adapter.IllnessAdapter;
import com.example.medicalconsultingapplication.adapter.DoctorAdapter;
import com.example.medicalconsultingapplication.adapter.IllnessAdapter;
import com.example.medicalconsultingapplication.model.Illness;
import com.example.medicalconsultingapplication.model.Users;
import com.example.medicalconsultingapplication.model.Users;
 import com.example.medicalconsultingapplication.model.Illness;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements IllnessAdapter.ItemClickListener, DoctorAdapter.ItemClickListener, DoctorAdapter.ItemClickListener2 {
    //illness
    ArrayList<Illness> items = new ArrayList<>();
    IllnessAdapter illnessAdapter;
    RecyclerView rvIllness;
    // doctor

    DoctorAdapter doctorAdapter;
    RecyclerView rvDoctor;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;


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
        mAuth=FirebaseAuth.getInstance();

        getcatgories();
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

//        doctorAdapter = new DoctorAdapter(getContext(), doctorItems, this, this);
//         rvDoctor.setAdapter(doctorAdapter);


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
//        Log.e("position", "" + category);
        data.putString("doctorCategory", category);//category
        illnessListFragment.setArguments(data);
        fragmentTransaction.replace(R.id.mainContainer,
                illnessListFragment).addToBackStack("").commit();
    }


    public void getcatgories(){
        ArrayList<Users> doctorItems = new ArrayList<>();
        db=FirebaseFirestore.getInstance();
     db.collection("Users").whereEqualTo("typeUser","دكتور").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
         @Override
         public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
             if (queryDocumentSnapshots.isEmpty()) {
                 Log.d("drn", "onSuccess: LIST EMPTY");
                 return;
             } else {
                 for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                     if (documentSnapshot.exists()){
                         String userName= documentSnapshot.getString("userName");
                         String doctorCatagory=documentSnapshot.getString("doctorCategory");
                         String userImage=documentSnapshot.getString("userImage");
                         Log.e("ghydaa",doctorCatagory.toString());
                         Log.e("g",userName);
                         Log.e("d",userImage);
                         Users users = new Users(" ","", userName,doctorCatagory,userImage);
                         doctorItems.add(users);
                         Log.e("LogDATA", String.valueOf(doctorItems));
                         rvDoctor.setLayoutManager(new LinearLayoutManager(requireContext()));
                         rvDoctor.setHasFixedSize(true);
                         rvDoctor.setAdapter(doctorAdapter);
                         if(doctorItems.isEmpty()){
                             Log.e("nada" , "null") ;
                         }else {
                             Log.e("nada" , " not null") ;

                         }
                         doctorAdapter =
                                 new DoctorAdapter(getContext(), doctorItems , HomeFragment.this , HomeFragment.this);
                         rvDoctor.setAdapter(doctorAdapter);
//        doctorAdapter = new DoctorAdapter(getContext(), doctorItems, this, this);
//         rvDoctor.setAdapter(doctorAdapter);

                         doctorAdapter.notifyDataSetChanged();

                          }
                 }
             }
         }
     }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
             Log.e("LogDATA", "get failed with ");
         }
     });


        }
    }

