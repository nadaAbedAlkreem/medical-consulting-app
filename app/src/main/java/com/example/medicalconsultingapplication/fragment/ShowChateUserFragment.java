package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.UserAdapter;
import com.example.medicalconsultingapplication.model.Requests;
import com.example.medicalconsultingapplication.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class ShowChateUserFragment extends Fragment implements UserAdapter.ItemClickListener2 {
    FirebaseDatabase database;
    DatabaseReference ref, chatrequestRef;
    RecyclerView rvUser;
    UserAdapter userAdapter;
    String UserName;
    String type_user;
    String UserImage;
    //    private ValueEventListener userListener;
    ArrayList<Users> allUser = new ArrayList<>();
    SwipeRefreshLayout refreshFind;
    TextView okay_text;
    TextView cancel_text;
    TextView nameusers;
    ImageView image;
    private String reciever_id;
    private String sender_user_id;
    private FirebaseAuth mauth;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_chate_user, container, false);
        database = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();
        sender_user_id = Objects.requireNonNull(mauth.getCurrentUser()).getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        chatrequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
//        String current_state = "new";
        refreshFind = view.findViewById(R.id.refreshFind);
        refreshFind.setOnRefreshListener(() -> {
            if (refreshFind.isRefreshing()) {
                refreshFind.setRefreshing(false);
            }
            allUser.clear();
            getAllUser();

        });
        rvUser = view.findViewById(R.id.rvUser);
       /*  userAdapter= new UserAdapter(getContext(),allUser,ShowChateUserFragment.this,ShowChateUserFragment.this);
         rvUser.setAdapter(userAdapter);
*/
        getAllUser();
        return view;
    }

    public void getAllUser() {
        Log.e("ghydaa", "onSuccess");
        ArrayList<Users> allUser = new ArrayList<>();
        ref.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String id = snapshot.getKey();
                String iduser = Objects.requireNonNull(snapshot.child("idUserAuth").getValue()).toString();
                Log.e("hhjjjjjjjjj", iduser);
                if (!iduser.equals(Objects.requireNonNull(mauth.getCurrentUser()).getUid())) {
                    Log.e("yyyyy", mauth.getCurrentUser().getUid());
                    type_user = Objects.requireNonNull(snapshot.child("typeUser").getValue()).toString();
                    UserName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                    UserImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                    Users user = new Users(id, UserName, UserImage, type_user);
                    allUser.add(user);
                    userAdapter = new UserAdapter(getContext(), allUser, ShowChateUserFragment.this);
                    Log.e("ghydaa", UserName);
                    rvUser.setAdapter(userAdapter);
                    rvUser.setHasFixedSize(true);
                    userAdapter.notifyDataSetChanged();
                } else {
                    Log.e("ghydaa", "eeeeeehssjjd");


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



  /*  @Override
    public void onStart() {
        super.onStart();

     userListener= new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             allUser.clear();
             for (DataSnapshot userdata:snapshot.getChildren()){
                 Users user=userdata.getValue(Users.class);
                 allUser.add(user);
             }
             userAdapter.notifyDataSetChanged();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     };
     ref.addValueEventListener(userListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (userListener!= null){
            ref.removeEventListener(userListener);
        }
    }*/


    @Override
    public void onItemClick2(int position, String id) {
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.sendrequest);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        okay_text = dialog.findViewById(R.id.okay_text);
        cancel_text = dialog.findViewById(R.id.cancel_text);
        image = dialog.findViewById(R.id.image);
        nameusers = dialog.findViewById(R.id.nameusers);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String id_array = (snapshot.getKey());
                assert id_array != null;
                if (id_array.equals(id)) {
                    Log.e("hhjjjjjjjjj", id_array);
                    UserName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                    UserImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                    Log.e("123", UserName);
                    Picasso.get().load(UserImage).into(image);
                    nameusers.setText(UserName);
                }
                okay_text.setOnClickListener(v -> {
                    String userrecieved = Objects.requireNonNull(mauth.getCurrentUser()).getUid();
                    String status = "process";
                    addData(id, userrecieved, status, UserName, UserImage);
                    dialog.dismiss();
                });
                cancel_text.setOnClickListener(v -> {
                    dialog.dismiss();
                });

                dialog.show();
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

    private void addData(String idsenser, String reciever_id, String proccse,String username,String image) {
        Requests requests = new Requests(reciever_id, idsenser, proccse,username,image);
        DatabaseReference userref = chatrequestRef.push();
        userref.setValue(requests);
    }
}





