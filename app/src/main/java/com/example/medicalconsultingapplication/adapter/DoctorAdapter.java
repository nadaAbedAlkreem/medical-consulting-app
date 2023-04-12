package com.example.medicalconsultingapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private final List<Doctor> mData;
    private final LayoutInflater mInflater;
    private final ItemClickListener mClickListener;
    private final ItemClickListener2 itemClickListener2;

    public DoctorAdapter(Context context, List<Doctor> data, ItemClickListener onClickChat, ItemClickListener2 onClick2) {
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
        holder.doctorName.setText(mData.get(position).getDoctorName());
        holder.doctorCategory.setText(mData.get(position).getDoctorCategory());
        holder.doctorImage.setImageResource(mData.get(position).getDoctorImage());
        holder.chat.setOnClickListener(v -> mClickListener.onItemClickChat(holder.getAdapterPosition(), mData.get(position).getId()));
        holder.container.setOnClickListener(v -> itemClickListener2.onItemClick2(holder.getAdapterPosition(), mData.get(position).getId()));
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
}
