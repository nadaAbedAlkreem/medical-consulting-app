package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.SearchAdapter;
import com.example.medicalconsultingapplication.model.Consultation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchFragment extends Fragment  {



    RecyclerView searchRecycler;
    SearchAdapter searchAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText search;
    ImageView searchIcon;
    LinearLayoutManager linearLayoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchRecycler=view.findViewById(R.id.searchRecycler);
        search = view.findViewById(R.id.search_);
        searchIcon=view.findViewById(R.id.searchIcon_);
         searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= search.getText().toString();
                Log.e("ghydaga",name);
                 getSearchUser(name)  ;

            }
        });

        return  view;
    }

    private void getSearchUser(String name) {
        ArrayList<Consultation> itemsearch = new ArrayList<>();

        db.collection("Consultion")
                .whereEqualTo("title", name)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            String id = document.getId();
                                String title = document.getString("title");
                                Consultation e_consultation = new Consultation(title);
                                itemsearch.add(e_consultation);

                            Toast.makeText(requireContext(),title , Toast.LENGTH_SHORT).show();
                                  searchAdapter = new SearchAdapter(getContext(), itemsearch);
                                 searchRecycler.setAdapter(searchAdapter);
                                 Log.e("nadah" ,String.valueOf( searchAdapter.getItemCount())) ;
                                  searchRecycler.setHasFixedSize(true);
                                searchAdapter.notifyDataSetChanged();


                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("nada", "not found result", e);
                    }
                });
    }
}