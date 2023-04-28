package com.example.medicalconsultingapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Illness;

import java.util.List;

public class IllnessAdapter extends RecyclerView.Adapter<IllnessAdapter.ViewHolder> {
    private final List<Illness> mData;
    private final LayoutInflater inflater;
    private final ItemClickListener itemClickListener;
    Context context;

    public IllnessAdapter(Context context, List<Illness> data, ItemClickListener onClick) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.itemClickListener = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.illness_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtIllnessName.setText(mData.get(position).getNameIllness());
        holder.imgIllnessName.setImageResource(mData.get(position).getImageIllness());
        holder.container.setOnClickListener(v ->
                itemClickListener.onItemClick(holder.getAdapterPosition(), mData.get(position).getId()));

        switch (position) {
            case 0: {
                holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.blueLight));
                holder.txtIllnessName.setTextColor(ContextCompat.getColor(context, R.color.blue));
                break;
            }
            case 1: {
                holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.redLight));
                holder.txtIllnessName.setTextColor(ContextCompat.getColor(context, R.color.red));
                break;
            }
            case 2: {
                holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.greenLight));
                holder.txtIllnessName.setTextColor(ContextCompat.getColor(context, R.color.green));
                break;
            }
            case 3: {
                holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.orangeLight));
                holder.txtIllnessName.setTextColor(ContextCompat.getColor(context, R.color.orange));
                break;
            }
            case 4: {
                holder.container.setBackgroundColor(ContextCompat.getColor(context, R.color.purpleLight));
                holder.txtIllnessName.setTextColor(ContextCompat.getColor(context, R.color.purple));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtIllnessName;
        ImageView imgIllnessName;
        LinearLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            this.txtIllnessName = itemView.findViewById(R.id.txtName);
            this.imgIllnessName = itemView.findViewById(R.id.imgType);
            this.container = itemView.findViewById(R.id.container);
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
