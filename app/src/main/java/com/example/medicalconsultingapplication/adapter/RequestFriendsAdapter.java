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

public class RequestFriendsAdapter extends RecyclerView.Adapter<RequestFriendsAdapter.ViewHolder> {
    private final List<Users> mData;
    private final LayoutInflater mInflater;
    private final RequestFriendsAdapter.ItemClickListener mClickListener;


    public RequestFriendsAdapter(Context context, List<Users> data    , RequestFriendsAdapter.ItemClickListener onClick) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = onClick;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.request_friends_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.UserNameRequest.setText(mData.get(position).getUserName());
         Picasso.get().load(mData.get(position).getUserImage()).fit().centerInside().into(holder.UserImageRequest);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView UserNameRequest;
        public ImageView UserImageRequest;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.UserNameRequest = itemView.findViewById(R.id.username_request);
            this.UserImageRequest = itemView.findViewById(R.id.userimage_request);

         }


    }

    public interface ItemClickListener {
        void onItemClickList(int position, String id);
    }


}
