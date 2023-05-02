package com.example.medicalconsultingapplication.operationConsulting;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalconsultingapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


public class ConsultingFragment extends Fragment {
    TextView namecons;
    TextView namedoc;
    ImageView imageconsu;
    ImageView imagedoc;
   ImageView imageinfo;
   TextView textcons;
    FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulting, container, false);
        db=FirebaseFirestore.getInstance();
        namecons = view.findViewById(R.id.namecons);
        textcons= view.findViewById(R.id.textcons);
        namedoc= view.findViewById(R.id.namedoc);
        imageconsu=view.findViewById(R.id.imageconsu);
        imagedoc=view.findViewById(R.id.imagedoc);
        imageinfo=view.findViewById(R.id.imageinfo);
        getconsultation();
        return view;

    }
    public void getconsultation(){

        db.collection("Consultion").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("drn", "onSuccess: LIST EMPTY");
                    return;
                }else {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        if (documentSnapshot.exists()) {
                            String namecons1=documentSnapshot.getString("title");
                            String namedoc1=documentSnapshot.getString("doctorName");
                            String textcons1=documentSnapshot.getString("content");
                            String imageconsu1=documentSnapshot.getString("conLogo");
                            String imagedoc1=documentSnapshot.getString("doctorImage");
                            String  imageinfo1=documentSnapshot.getString("conInfo");
                             namecons.setText(namecons1);
                             namedoc.setText(namedoc1);
                             textcons.setText(textcons1);
                            Picasso.get().load(imageconsu1).into(imageconsu);
                            Picasso.get().load(imagedoc1).into(imagedoc);
                            Picasso.get().load(imageinfo1).into(imageinfo);
                            Log.e("imageinfo",imageinfo1);
                            Log.e("namecons",namecons1);
                        }
                        }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("testcons", "get failed with ");
            }
        });
    }

}