package com.example.medicalconsultingapplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Value;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private final List<Users> mData;
    private final LayoutInflater mInflater;
    private final ItemClickListener mClickListener;
    private final ItemClickListener2 itemClickListener2;


    public DoctorAdapter(Context context, List<Users> data, ItemClickListener onClickChat, ItemClickListener2 onClick2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = onClickChat;
        this.itemClickListener2 = onClick2;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.doctor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.doctorName.setText(mData.get(position).getUserName());
        holder.doctorCategory.setText(mData.get(position).getDoctorCategory());
        Picasso.get().load(mData.get(position).getUserImage()).fit().centerInside().into(holder.doctorImage);
//        holder.chat.setOnClickListener(v -> mClickListener.onItemClickChat(holder.getAdapterPosition(), mData.get(position).getId()));
//        holder.container.setOnClickListener(v -> itemClickListener2.onItemClick2(holder.getAdapterPosition(), mData.get(position).getId()));

        FirebaseFirestore db;
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        db.collection("Users").whereEqualTo("idUserAuth", firebaseUser.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Log.e("typeUser", String.valueOf(d.get("typeUser")));
                                if (String.valueOf(d.get("typeUser")).equals("دكتور")) {

                                     holder.chat.setVisibility(View.GONE);
                                } else {
                                    Log.e("nadaTestAuth ", "مريض  ");
                                     Log.e("testDoctor", "0");
                                    holder.chat.setVisibility(View.VISIBLE);

                                }


                            }
                        } else {
                            Log.e("AuthIDUSER", "empty");

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("AuthIDUSER", "FAILD");
                    }
                });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView doctorName;
        public TextView doctorCategory;
        public ImageView chat;
        public ConstraintLayout container;
        public ImageView doctorImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.doctorName = itemView.findViewById(R.id.txtDoctorName);
            this.doctorCategory = itemView.findViewById(R.id.txtCategoryName);
            this.chat = itemView.findViewById(R.id.imgChat);
            this.doctorImage = itemView.findViewById(R.id.doctorImage);
            this.container = itemView.findViewById(R.id.containerDoctor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemClickListener {
        void onItemClickChat(int position, String id);
    }

    public interface ItemClickListener2 {
        void onItemClick2(int position, String id);
    }



    public void getData() {
    }
}
