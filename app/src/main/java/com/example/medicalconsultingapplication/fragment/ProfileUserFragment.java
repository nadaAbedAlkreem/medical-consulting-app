package com.example.medicalconsultingapplication.fragment;

 import androidx.annotation.NonNull;
 import androidx.recyclerview.widget.RecyclerView;

 import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

 import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

 import com.example.medicalconsultingapplication.R;
 import com.example.medicalconsultingapplication.operationConsulting.AddConsultionActivity;
 import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.model.Consultation;
 import com.example.medicalconsultingapplication.operationConsulting.UpdateConsultionActivity;
 import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

 import com.example.medicalconsultingapplication.model.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProfileUserFragment extends Fragment implements ConsultationProfileAdapter.ItemClickListener {
     ConsultationProfileAdapter consultationProfileAdapter;
     FirebaseFirestore db;
     private FirebaseAuth mAuth;
     ImageView imageUserCurrent ;
     TextView nameUserCurrent ;
      Button viewDetailsConsulting  ,  updateConsulting , deleteConsulting , consel;
    ArrayList<Consultation> items = new ArrayList<>();
     RecyclerView reDoctorConsultationsProfile;
     ArrayList<Users> data = new ArrayList<>();
     FloatingActionButton fAdd;
    String doctorName;
    String doctorImage;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int typeUser = getArguments().getInt("idAuthDoctor");
        Log.e("messageNada" , String.valueOf(typeUser)) ;
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        fAdd = view.findViewById(R.id.fAdd);
        reDoctorConsultationsProfile = view.findViewById(R.id.reDoctorConsultationsProfile);
         imageUserCurrent = view.findViewById(R.id.imageProfileUser);
        nameUserCurrent = view.findViewById(R.id.txtProfileUserName) ;
        db.collection("Users").whereEqualTo("idUserAuth" ,  mAuth.getCurrentUser().getUid() ).get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list  = queryDocumentSnapshots.getDocuments() ;
                if(!list.isEmpty()){

                    for (DocumentSnapshot d : list) {
                       Log.e("name" ,  d.getString("userName") );
                       nameUserCurrent.setText(d.getString("userName"));

                          Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/medical-consulting-app.appspot.com/o/image%2F0e9777e4-eae5-442c-865e-d9535860007c?alt=media&token=3bbb4742-30a1-467e-a88f-945aeac0330f"  )
                                .into(imageUserCurrent);
                    }

                }else{
                    Log.e("ttttt" , "empty") ;

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.e("ttttttttttttttt" , "FAILD") ;
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
        Log.e("test" , doctorId);

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
        viewDetailsConsulting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        });
        updateConsulting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), UpdateConsultionActivity.class);
                intent1.putExtra("idClickUpdateItemConsulting", id  );
                Log.e("ttttt", id);

                startActivity(intent1);


            }
        });
        deleteConsulting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Log.e("idPosition", id);

                db.collection("Consultion").document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialog.dismiss();
                                items.remove(position); // updating source
                                consultationProfileAdapter.notifyItemRemoved(position);


                                Log.e("nada", "success delete");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("nada", "Failure delete");
                            }
                        });
            }
        });
        consel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    public  void  getConsultstionData ()
    {
       // mAuth.getCurrentUser().getUid()

    db.collection("Consultion").whereEqualTo("doctorAuth" ,mAuth.getCurrentUser()
            .getUid() ).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
    {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> list  = queryDocumentSnapshots.getDocuments() ;
                    if(!list.isEmpty()){
                        for (DocumentSnapshot d : list) {
                              Consultation result = new  Consultation(d.getId(), d.getString("conLogo") ,
                                     d.getString("title")
                                     ) ;
                          items.add(result);
                    consultationProfileAdapter =
                            new ConsultationProfileAdapter(getContext(), items , ProfileUserFragment.this);
                       reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);


                        }

                }else{
                    Log.e("ttttt" , "empty") ;

                }


            }
        }).addOnFailureListener(new OnFailureListener()
    {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ttttttttttttttt" , "FAILD") ;
            }
        });


    }



}