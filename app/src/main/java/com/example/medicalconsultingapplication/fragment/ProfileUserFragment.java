package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.model.Consultation;
import com.example.medicalconsultingapplication.operationConsulting.AddConsultionActivity;
import com.example.medicalconsultingapplication.operationConsulting.ConsultingFragment;
import com.example.medicalconsultingapplication.operationConsulting.UpdateConsultionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileUserFragment extends Fragment implements ConsultationProfileAdapter.ItemClickListener {
    ConsultationProfileAdapter consultationProfileAdapter;
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    ImageView imageUserCurrent;
    TextView nameUserCurrent;
    Button viewDetailsConsulting, updateConsulting, deleteConsulting, consel;
    ArrayList<Consultation> items = new ArrayList<>();
    RecyclerView reDoctorConsultationsProfile;
    FloatingActionButton fAdd;
    String doctorName;
    String doctorImage;
    SwipeRefreshLayout refreshCon;
    String conId;
    Bundle data = new Bundle();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        assert getArguments() != null;
        int typeUser = getArguments().getInt("idAuthDoctor");
        Log.e("messageNada", String.valueOf(typeUser));

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        fAdd = view.findViewById(R.id.fAdd);
        reDoctorConsultationsProfile = view.findViewById(R.id.reDoctorConsultationsProfile);
        imageUserCurrent = view.findViewById(R.id.imageProfileUser);
        nameUserCurrent = view.findViewById(R.id.txtProfileUserName);
        refreshCon = view.findViewById(R.id.refreshCon);
//        refreshCon.setOnRefreshListener(() -> {
//            if (refreshCon.isRefreshing()) {
//                refreshCon.setRefreshing(false);
//            }
//            items.clear();
//            getConsultstionData();
//        });
        ref.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (Objects.requireNonNull(snapshot.child("idUserAuth").getValue()).toString().equals(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())) {
                    doctorName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                    doctorImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                    Picasso.get().load(doctorImage).into(imageUserCurrent);
                    nameUserCurrent.setText(doctorName);
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
        if(typeUser == 1 )
        {
            Log.e("tesr 1", "jih") ;
            getConsultstionData();
        }
        else
        {
            /// عرض الاشتراكات الخاصة في  الاشعارات
        }

         assert getArguments() != null;
        int idAuthDoctor = getArguments().getInt("idAuthDoctor");
        String doctorId = getArguments().getString("doctorId");
//        Log.e("test" , doctorId);

        String doctorAuth = getArguments().getString("doctorAuth");
        String doctorCategory = getArguments().getString("doctorCategory");
        if (idAuthDoctor == 1) {
             doctorName = getArguments().getString("userName");
             doctorImage = getArguments().getString("userImage");
        }
        Log.e("messageNada", String.valueOf(idAuthDoctor));
        Log.e("messageNada", String.valueOf(doctorId));
        if (idAuthDoctor != 1) {
            fAdd.setVisibility(View.GONE);
        }

        consultationProfileAdapter = new ConsultationProfileAdapter(getContext(), items, this);
        reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);
        fAdd.setOnClickListener(v ->
        {
            Intent intent = new Intent(getContext(), AddConsultionActivity.class);
            intent.putExtra("doctorId", doctorId);
            intent.putExtra("doctorAuth", doctorAuth);
            intent.putExtra("doctorCategory", doctorCategory);
            intent.putExtra("userName", doctorName);
            intent.putExtra("userImage", doctorImage);
            startActivity(intent);
        });
         return view;
    }

    @Override
     public void onItemClickList(int position, String id) {
        Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_crud);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        viewDetailsConsulting = dialog.findViewById(R.id.view_details);
        updateConsulting = dialog.findViewById(R.id.update_consulting);
        deleteConsulting = dialog.findViewById(R.id.delete_consulting);
        consel = dialog.findViewById(R.id.consel);
        viewDetailsConsulting.setOnClickListener(v -> {
            ConsultingFragment consultingFragment = new ConsultingFragment();
            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
            data.putString("conId", conId); // con Document id
            Log.e("TAG", "onItemClickList: " + conId);
            consultingFragment.setArguments(data);
            fragmentTransaction.replace(R.id.mainContainer,
                    consultingFragment).addToBackStack("").commit();
            dialog.dismiss();
        });
        updateConsulting.setOnClickListener(v -> {
            Intent intent1 = new Intent(getContext(), UpdateConsultionActivity.class);
            intent1.putExtra("idClickUpdateItemConsulting", id);
            Log.e("ttttt", id);

            startActivity(intent1);


        });
        deleteConsulting.setOnClickListener(v -> {
            Log.e("idPosition", id);

            db.collection("Consultion").document(id)
                    .delete()
                    .addOnSuccessListener(unused -> {
                        dialog.dismiss();
                        items.remove(position); // updating source
                        consultationProfileAdapter.notifyItemRemoved(position);


                        Log.e("nada", "success delete");
                    }).addOnFailureListener(e -> Log.e("nada", "Failure delete"));
        });
        consel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    public void getConsultstionData() {
        // mAuth.getCurrentUser().getUid()

        db.collection("Consultion").whereEqualTo("doctorAuth", Objects.requireNonNull(mAuth.getCurrentUser())
                .getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            if (!list.isEmpty()) {
                for (DocumentSnapshot d : list) {
                    conId = d.getId();
                    Log.e("TAG", "getConsultstionDataAyat: " + conId);
                    Consultation result = new Consultation(d.getId(), d.getString("conLogo"),
                            d.getString("title")
                    );
                    items.add(result);
                    consultationProfileAdapter =
                            new ConsultationProfileAdapter(getContext(), items, ProfileUserFragment.this);
                    reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);


                }

            } else {
                Log.e("ttttt", "empty");

            }


        }).addOnFailureListener(e -> Log.e("ttttttttttttttt", "FAILD"));


    }
}