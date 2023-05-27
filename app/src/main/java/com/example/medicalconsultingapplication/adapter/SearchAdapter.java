package com.example.medicalconsultingapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Consultation;

import java.util.List;

public class SearchAdapter  extends  RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final List<Consultation> mData;
    private final LayoutInflater inflater;
    Context context;

    public SearchAdapter(Context context, List<Consultation> mData) {
        this.mData = mData;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.serach, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        Log.e("nada55" , mData.get(position).getConsultationHeader()) ;
        Log.e("nada55" , "mData.get(position).getConsultationHeader()") ;

        holder.nameSearch.setText(mData.get(position).getConsultationHeader());
    }

    @Override
    public int getItemCount() {
        return  mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameSearch;
        LinearLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            this.nameSearch = itemView.findViewById(R.id.nameSearch);
            this.container = itemView.findViewById(R.id.container);

        }
    }
}
