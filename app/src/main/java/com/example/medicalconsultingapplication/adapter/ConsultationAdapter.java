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
import com.squareup.picasso.Picasso;

import java.util.List;

public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationAdapter.ViewHolder> {
    private final List<Consultation> mData;
    private final LayoutInflater mInflater;
    private final ConsultationAdapter.ItemClickListener mClickListener;

    public ConsultationAdapter(Context context, List<Consultation> data, ConsultationAdapter.ItemClickListener onClick) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.illness_list_item, parent, false);
        return new ConsultationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.doctorName.setText(mData.get(position).getDoctorName());
        holder.consultationTitle.setText(mData.get(position).getConsultationHeader());
        Picasso.get().load(mData.get(position).getDoctorImage()).fit().centerInside().into(holder.doctorImage);
        holder.container.setOnClickListener(v -> mClickListener.onItemClickList(holder.getAdapterPosition(), mData.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView doctorName;
        public TextView consultationTitle;
        public ConstraintLayout container;
        public ImageView doctorImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.doctorName = itemView.findViewById(R.id.txtHeaderConsultationListDoctor);
            this.consultationTitle = itemView.findViewById(R.id.txtConsultation);
            this.doctorImage = itemView.findViewById(R.id.doctorImageList);
            this.container = itemView.findViewById(R.id.containerListIllness);
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
