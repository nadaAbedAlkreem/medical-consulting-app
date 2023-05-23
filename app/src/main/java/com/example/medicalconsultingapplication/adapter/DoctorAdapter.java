package com.example.medicalconsultingapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private final List<Users> mData;
    private final LayoutInflater mInflater;
    private final ItemClickListener itemClickListener;


    public DoctorAdapter(Context context, List<Users> data,  ItemClickListener onClick2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

        this.itemClickListener = onClick2;

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
        holder.container.setOnClickListener(v ->
                itemClickListener.onItemClick2(holder.getAdapterPosition(), mData.get(position).getId()));

        FirebaseAuth mAuth;
        FirebaseDatabase database;
        DatabaseReference ref;
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        assert firebaseUser != null;
        ref.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


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
            this.doctorImage = itemView.findViewById(R.id.doctorImage);
            this.container = itemView.findViewById(R.id.containerDoctor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface ItemClickListener {
        void onItemClick2(int position, String id);
    }


    public void getData() {
    }
}