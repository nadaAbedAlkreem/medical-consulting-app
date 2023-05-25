package com.example.medicalconsultingapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Requests;
import com.example.medicalconsultingapplication.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestFriendsAdapter extends RecyclerView.Adapter<RequestFriendsAdapter.ViewHolder> {
    private final List<Requests> mData;
    private final LayoutInflater mInflater;
    private final RequestFriendsAdapter.ItemClickListener mClickListener;


    public RequestFriendsAdapter(Context context, ArrayList<Requests> data    ,  ItemClickListener onClick) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        DatabaseReference mDatabase;
        DatabaseReference ref;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String id =   mData.get(position).getId();
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();

        ref = database.getReference("Users");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(Objects.requireNonNull(snapshot.child("idUserAuth").getValue()).toString().equals(mData.get(position).getIdSend())){
                    holder.UserNameRequest.setText(Objects.requireNonNull(snapshot.child("userName").getValue()).toString());
                    Picasso.get().load( Objects.requireNonNull(snapshot.child("userImage").getValue()).toString()).fit().centerInside().into(holder.UserImageRequest);
                    holder.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("nada" ,  id) ;
                            mDatabase.child("Chat Requests").child(id).child("status").setValue("accept");
                            Log.e("nada" , String.valueOf(position));
                            mData.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mData.size());
                        }
                    });
                    holder.ignore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("nada" ,  id) ;
                            mDatabase.child("Chat Requests").child(id).removeValue();
                            mData.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mData.size());

                        }
                    });
                }



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

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView UserNameRequest;
        public ImageView UserImageRequest;
        public Button accept  ;
        public  Button ignore ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.UserNameRequest = itemView.findViewById(R.id.username_request);
            this.UserImageRequest = itemView.findViewById(R.id.userimage_request);
            this.accept = itemView.findViewById(R.id.accept_btn) ;
            this.ignore = itemView.findViewById(R.id.ignore_btn) ;



         }


    }

    public interface ItemClickListener {
        void onItemClickList(int position, String id);
    }


}
