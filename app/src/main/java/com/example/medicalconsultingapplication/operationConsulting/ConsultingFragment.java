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

import androidx.fragment.app.Fragment;

import com.example.medicalconsultingapplication.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;


public class ConsultingFragment extends Fragment {
    TextView txtNameCons;
    TextView txtNameDoc;
    ImageView imageConsu;
    ImageView imageDoc;
    ImageView imageInfo;
    TextView txtContent;
    FirebaseFirestore db;
    String conId;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulting, container, false);
        db = FirebaseFirestore.getInstance();
        txtNameCons = view.findViewById(R.id.namecons);
        txtContent = view.findViewById(R.id.textcons);
        txtNameDoc = view.findViewById(R.id.namedoc);
        imageConsu = view.findViewById(R.id.imageconsu);
        imageDoc = view.findViewById(R.id.imagedoc);
        imageInfo = view.findViewById(R.id.imageinfo);
        assert getArguments() != null;
        conId = getArguments().getString("conId");
        getconsultation();
        return view;
    }
    public void getconsultation() {
        db.collection("Consultion").document(conId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String namecons = documentSnapshot.getString("title");
                        String namedoc = documentSnapshot.getString("doctorName");
                        String textcons = documentSnapshot.getString("content");
                        String imageconsu = documentSnapshot.getString("conLogo");
                        String imagedoc = documentSnapshot.getString("doctorImage");
                        String imageinfo = documentSnapshot.getString("conInfo");
                        txtNameCons.setText(namecons);
                        txtNameDoc.setText(namedoc);
                        txtContent.setText(textcons);
                        Picasso.get().load(imageconsu).fit().centerInside().into(imageConsu);
                        Picasso.get().load(imageinfo).fit().centerInside().into(imageInfo);
                        Picasso.get().load(imagedoc).fit().centerInside().into(imageDoc);

//                        Log.e("imageinfo", imageinfo);
//                        Log.e("namecons", namecons);
                    } else {
                        Log.d("drn", "onSuccess: LIST EMPTY");
                    }
                })
                .addOnFailureListener(e -> Log.e("testcons", "get failed with "));
//                whereEqualTo(,conId).get().addOnSuccessListener(queryDocumentSnapshots -> {
//            Log.e("TAG", "getconsultation22: "+FieldPath.documentId());
//            if (queryDocumentSnapshots.ex) {
//                Log.d("drn", "onSuccess: LIST EMPTY");
//            }else {
//                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                    if (documentSnapshot.exists()) {
//                        String namecons=documentSnapshot.getString("title");
//                        String namedoc=documentSnapshot.getString("doctorName");
//                        String textcons=documentSnapshot.getString("content");
//                        String imageconsu=documentSnapshot.getString("conLogo");
//                        String imagedoc=documentSnapshot.getString("doctorImage");
//                        String  imageinfo=documentSnapshot.getString("conInfo");
//                        Log.e("imageinfo",imageinfo);
//                        Log.e("namecons",namecons);
//                    }
//                    }
//            }
//        }).addOnFailureListener(e -> Log.e("testcons", "get failed with "));
    }
}