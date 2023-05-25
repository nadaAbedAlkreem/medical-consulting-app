package com.example.medicalconsultingapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.fragment.ShowChateUserFragment;
import com.example.medicalconsultingapplication.model.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final List<Users> mData;
    private final LayoutInflater mInflater;

    private final UserAdapter.ItemClickListener2 itemClickListener2;
    private DatabaseReference Userref;
    public UserAdapter(Context context, List<Users> data,  UserAdapter.ItemClickListener2 onClick2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.itemClickListener2 = onClick2;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.showchateuser, parent, false);
        Userref= FirebaseDatabase.getInstance().getReference().child("Users");
        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.UserName.setText(mData.get(position).getUserName());
        holder.type_user.setText(mData.get(position).getTypeUser());
        Picasso.get().load(mData.get(position).getUserImage()).fit().centerInside().into(holder.UserImage);
        holder.containeruser.setOnClickListener(t -> itemClickListener2.onItemClick2(
                holder.getAdapterPosition(),
                mData.get(position).getId() , mData.get(position).getIdUserAuth()));
        holder.itemView.setOnClickListener(v -> {
                    String user=mData.get(position).getId();
                    Intent intent = new Intent(holder.itemView.getContext(), ShowChateUserFragment.class);
                    intent.putExtra("id", user);
                    Log.e("user", user.toString());
                    holder.itemView.getContext().startActivity(intent);
                }
                );

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView UserName;
     //   public ConstraintLayout container;
        public ImageView UserImage;
        public TextView type_user;

        public ConstraintLayout containeruser;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.UserName = itemView.findViewById(R.id.UserName);
            this.UserImage = itemView.findViewById(R.id.UserImage);
            this.type_user = itemView.findViewById(R.id.type_user);
        //    this.container = itemView.findViewById(R.id.containerDoctor);
            this.containeruser=itemView.findViewById(R.id.containeruser);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface ItemClickListener2 {
        void onItemClick2(int position, String id , String idAuthUser );

    }

}
