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
import com.example.medicalconsultingapplication.model.Consultation;

import java.util.List;

public class ConsultationProfileAdapter extends RecyclerView.Adapter<ConsultationProfileAdapter.ViewHolder> {
    private final List<Consultation> mData;
    private final LayoutInflater mInflater;
    private final ConsultationProfileAdapter.ItemClickListener mClickListener;


    public ConsultationProfileAdapter(Context context, List<Consultation> data, ConsultationProfileAdapter.ItemClickListener onClick ) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = onClick;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.consultations_list_item, parent, false);
        return new ConsultationProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.consultationHeader.setText(mData.get(position).getConsultationHeader());
         holder.consultationImage.setImageResource(mData.get(position).getConsultationImage());
        holder.containerProfile.setOnClickListener(v -> mClickListener.onItemClickList(holder.getAdapterPosition(), mData.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView consultationHeader;
         public ImageView consultationImage;
        public ConstraintLayout containerProfile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.consultationHeader = itemView.findViewById(R.id.txtHeaderConsultationList);
             this.consultationImage = itemView.findViewById(R.id.logoConsultation);
            this.containerProfile = itemView.findViewById(R.id.containerConsultationProfile);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemClickListener {
        void onItemClickList(int position, String id);
    }

}
