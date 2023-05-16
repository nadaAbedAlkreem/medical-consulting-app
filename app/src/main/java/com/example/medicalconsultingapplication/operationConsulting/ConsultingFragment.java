package com.example.medicalconsultingapplication.operationConsulting;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalconsultingapplication.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


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
        db.collection("Consultion").get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.isEmpty()) {
                Log.d("drn", "onSuccess: LIST EMPTY");
            }else {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    if (documentSnapshot.exists()) {
                        String namecons=documentSnapshot.getString("title");
                        String namedoc=documentSnapshot.getString("doctorName");
                        String textcons=documentSnapshot.getString("content");
                        String imageconsu=documentSnapshot.getString("conLogo");
                        String imagedoc=documentSnapshot.getString("doctorImage");
                        String  imageinfo=documentSnapshot.getString("conInfo");
                        Log.e("imageinfo",imageinfo);
                        Log.e("namecons",namecons);
                    }
                    }
            }
        }).addOnFailureListener(e -> Log.e("testcons", "get failed with "));
    }

}