package com.example.medicalconsultingapplication.adapter;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
 import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> UserMessageList;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    public MessageAdapter(List<Messages> UserMessageList)
    {
        this.UserMessageList=UserMessageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_two_side,parent,false);
       MessageViewHolder messageViewHolder=new MessageViewHolder(view);
       mAuth=FirebaseAuth.getInstance();

       return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, final int position) {
        String messagesenderid=mAuth.getCurrentUser().getUid();
        final Messages messages=UserMessageList.get(position);
        String fromuserid=messages.getFrom();
        String frommessagetype=messages.getType();
        String to =messages.getTo();
        Log.e("test" ,to ) ;
        Log.e("test" ,to ) ;

        userRef= FirebaseDatabase.getInstance().getReference().child("Users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = dataSnapshot.getKey();
                Log.e("nadaAdapterMass" , id ) ;

                if (id.equals(fromuserid)) {
                    Log.e("nasdajcjo88" , "tse") ;
                    if (dataSnapshot.hasChild("image")) {
                        String receiverprofileimage = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(receiverprofileimage).placeholder(R.drawable.doctor).into(holder.receiverprofileimage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.receivermessagetext.setVisibility(View.GONE);
        holder.receiverprofileimage.setVisibility(View.GONE);
        holder.sendermessagetext.setVisibility(View.GONE);


            if(fromuserid.equals(messagesenderid))
            {
                Log.e("test" ,"jl" ) ;

                holder.sendermessagetext.setVisibility(View.VISIBLE);
                holder.sendermessagetext.setBackgroundResource(R.drawable.sender_message_layout);
                holder.sendermessagetext.setText(messages.getMessage()+"\n \n"+messages.getTime()+" - "+messages.getDate());
            }
            else
            {
                Log.e("test" ,"m" ) ;
                holder.receivermessagetext.setVisibility(View.VISIBLE);
                holder.receiverprofileimage.setVisibility(View.VISIBLE);


                holder.receivermessagetext.setBackgroundResource(R.drawable.receiver_message_layout);
                holder.receivermessagetext.setText(messages.getMessage()+"\n \n"+messages.getTime()+" - "+messages.getDate());
            }


    }

    @Override
    public int getItemCount() {
        return UserMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView sendermessagetext,receivermessagetext;
        public ImageView receiverprofileimage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermessagetext=itemView.findViewById(R.id.sender_message_text);
            receivermessagetext=itemView.findViewById(R.id.receiver_message_text);
            receiverprofileimage=itemView.findViewById(R.id.imageProfileUserSignUp);

        }
    }


}
