package com.example.medicalconsultingapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Requests;

import java.util.List;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ViewHolder> {

    private final List<Requests> mData;
    private final LayoutInflater inflater;
    private final ActiveAdapter.ItemClickListener itemClickListener;
    Context context;

    public ActiveAdapter(Context context, List<Requests> data, ItemClickListener onClick) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.itemClickListener = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.active_item, parent, false);
        return new ActiveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtActiveName.setText(mData.get(position).getNameReviver());
        holder.imgActive.setImageResource(mData.get(position).getImageActive());
        holder.container.setOnClickListener(v ->
                itemClickListener.onItemClick(holder.getAdapterPosition(), mData.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtActiveName;
        ImageView imgActive;
        ConstraintLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            this.txtActiveName = itemView.findViewById(R.id.txtActiveFriend);
            this.imgActive = itemView.findViewById(R.id.imgActive);
            this.container = itemView.findViewById(R.id.containerActive);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemClickListener {
        void onItemClick(int position, String id);
    }
}
