package com.example.medicalconsultingapplication.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

 import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationAdapter;
 import com.example.medicalconsultingapplication.AddConsultionActivity;
import com.example.medicalconsultingapplication.R;
 import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.model.Consultation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileUserFragment extends Fragment implements ConsultationProfileAdapter.ItemClickListener {
    ArrayList<Consultation> items = new ArrayList<>();
    ConsultationProfileAdapter consultationProfileAdapter;
     StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
    FirebaseFirestore db;
    private FirebaseAuth mAuth;

     RecyclerView reDoctorConsultationsProfile;

    @SuppressLint("MissingInflatedId")
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         int strtext = getArguments().getInt("idAuthDoctor");
        Log.e("messageNada" , String.valueOf(strtext)) ;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        reDoctorConsultationsProfile = view.findViewById(R.id.reDoctorConsultationsProfile);
        getConsultstionData();

        return view;
    }

    @Override
    public void onItemClickList(int position, String id) {
        Intent intent = new Intent(getContext(), AddConsultionActivity.class);
        startActivity(intent);
    }

    public  void  getConsultstionData ()
    {
        ArrayList<Consultation> items = new ArrayList<>();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        db.collection("Consultion").whereEqualTo("doctorId" ,"qLZodlXUdeYtaPRKqF5V").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> list  = queryDocumentSnapshots.getDocuments() ;
                    if(!list.isEmpty()){
                        for (DocumentSnapshot d : list) {
                            Log.e("trtt" , d.getString("conLogo")) ;
                             Consultation result = new  Consultation("",
                                     d.getString("title") ,
                                       "https://firebasestorage.googleapis.com/v0/b/medical-consulting-app.appspot.com/o/Logos%2F1681941285580_LogoImage?alt=media&token=57596923-9564-4e22-8cfd-4a42bcc0462d") ;

                          items.add(result);
                    consultationProfileAdapter =
                            new ConsultationProfileAdapter(getContext(), items , ProfileUserFragment.this);
                       reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);


                        }

                }else{
                    Log.e("ttttt" , "empty") ;

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ttttttttttttttt" , "FAILD") ;
            }
        });


    }



}