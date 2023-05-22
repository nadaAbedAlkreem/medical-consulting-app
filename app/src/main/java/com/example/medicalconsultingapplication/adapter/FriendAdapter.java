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
import com.example.medicalconsultingapplication.model.Requests;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private final List<Requests> mData;
    private final LayoutInflater mInflater;
    private final FriendAdapter.ItemClickListener mClickListener;

    public FriendAdapter(Context context, List<Requests> data, FriendAdapter.ItemClickListener onClick) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.friend_item, parent, false);
        return new FriendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.friendName.setText(mData.get(position).getNameReviver());
        Picasso.get().load(mData.get(position).getImageReciver()).fit().centerInside().into(holder.friendImage);
        holder.container.setOnClickListener(v -> mClickListener.onItemClickChat(holder.getAdapterPosition(), mData.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView friendName;
        public ConstraintLayout container;
        public ImageView friendImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.friendName = itemView.findViewById(R.id.friendName);
            this.friendImage = itemView.findViewById(R.id.friendImage);
            this.container = itemView.findViewById(R.id.containerFriend);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemClickListener {
        void onItemClickChat(int position, String id);
    }
}
