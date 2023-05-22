package com.example.medicalconsultingapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalconsultingapplication.Authentication.LogInActivity;
import com.example.medicalconsultingapplication.adapter.RequestFriendsAdapter;
import com.example.medicalconsultingapplication.fragment.ChatFragment;
import com.example.medicalconsultingapplication.fragment.HomeFragment;
import com.example.medicalconsultingapplication.fragment.ProfileUserFragment;
import com.example.medicalconsultingapplication.model.Requests;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class DrawerNavigationActivity extends AppCompatActivity implements RequestFriendsAdapter.ItemClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RequestFriendsAdapter requestFriendsAdapter;
    RecyclerView recyclerViewRequestFriends;
    FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ConstraintLayout container;
    NavigationView navigationView;
    public int idAuthDoctor = -1;
    // 0 paition , 1 doctor
    ImageView imagedrawe;
    String doctorId;
    String doctorAuth;
    String doctorCategory;
    String doctorName;
    String doctorImage;
    private DatabaseReference mDatabase;
    String typeUser;
    String userName;
    String userImage;
    Bundle data = new Bundle();
    FragmentTransaction fragmentTransaction;
    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_navigation);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        drawerLayout = findViewById(R.id.drawerLayout);
        container = findViewById(R.id.mainContainer);
        navigationView = findViewById(R.id.navView);
        imagedrawe = findViewById(R.id.imagedrawe);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");
        checkTypeUesrCurrent();
        getData();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        swipe(new HomeFragment());
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navHome:
//                    swipe(new HomeFragment());
                    HomeFragment HomeFragment = new HomeFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    data.putInt("idAuthDoctor1", idAuthDoctor);
//                    Log.e("ahmed", "" + idAuthDoctor);
                    data.putString("doctorCategory", doctorCategory);
                    HomeFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.mainContainer,
                            HomeFragment).addToBackStack("").commit();
                    break;
                case R.id.navProfile:
//                    swipe(new ProfileUserFragment());
                    ProfileUserFragment profileUserFragment = new ProfileUserFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    data.putInt("idAuthDoctor", idAuthDoctor); // 1,0
                    data.putString("doctorId", doctorId); // document
                    data.putString("doctorAuth", doctorAuth); // Auth
                    if (idAuthDoctor == 1) {
                        data.putString("userName", doctorName);
                        data.putString("userImage", doctorImage);
                    }
                    data.putString("doctorCategory", doctorCategory);//category
                    profileUserFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.mainContainer,
                            profileUserFragment).addToBackStack("").commit();
                    break;
                case R.id.navAddFriendRequest:
                    Dialog dialog = new Dialog(DrawerNavigationActivity.this);
                    dialog.setContentView(R.layout.dialog_friend_request);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    recyclerViewRequestFriends = dialog.findViewById(R.id.recyRequestFriends);
                    Button accept_btn = dialog.findViewById(R.id.accept_btn);
                    Button ignore_btn = dialog.findViewById(R.id.ignore_btn);

                    recyclerViewRequestFriends.setHasFixedSize(true);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    recyclerViewRequestFriends.setLayoutManager(layoutManager);
                    ArrayList<Requests> items = new ArrayList<>();
                    mDatabase.child("Chat Requests").addChildEventListener(new ChildEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            String id = snapshot.getKey();
                            String userName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                            String idRecievd = Objects.requireNonNull(snapshot.child("idRecievd").getValue()).toString();
                            String idSend = Objects.requireNonNull(snapshot.child("idSend").getValue()).toString();
                            String image = Objects.requireNonNull(snapshot.child("image").getValue()).toString();
                            String status = Objects.requireNonNull(snapshot.child("statous").getValue()).toString();
                            if (status.equals("process")) {
                                Requests requests_friend = new Requests(id, idRecievd, idSend, status, image, userName);
                                items.add(requests_friend);
                                requestFriendsAdapter = new RequestFriendsAdapter(DrawerNavigationActivity.this, items, DrawerNavigationActivity.this);
                                recyclerViewRequestFriends.setAdapter(requestFriendsAdapter);
                                requestFriendsAdapter.notifyDataSetChanged();
                                Log.e("nada", userName);
                                Log.e("nada", String.valueOf(requestFriendsAdapter.getItemCount()));
                            }
                        }
                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
                       @Override
                       public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
                       @Override
                       public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {}
                    }
                    );
                    dialog.show();

                    break;
                case R.id.navLogOut: {
                    SharedPreferences sharedPref = getSharedPreferences("loginAndLogoutOP", Context.MODE_PRIVATE);
                    sharedPref.edit().putString(String.valueOf(R.string.LoginActive), "").apply();
                    Intent intent = new Intent(DrawerNavigationActivity.this, LogInActivity.class);
                    startActivity(intent);
                    break;

                }
                case R.id.navChat: {
//                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,
//                            new ChatFragment()).addToBackStack("").commit();
                    ChatFragment chatFragment = new ChatFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    data.putInt("idAuthDoctor", idAuthDoctor); // 1,0
                    data.putString("doctorAuth", doctorAuth);
                    chatFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.mainContainer,
                            chatFragment).addToBackStack("").commit();
                    break;
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }
    private void swipe(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,
                fragment).addToBackStack("").commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void checkTypeUesrCurrent() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                assert firebaseUser != null;
                doctorAuth = firebaseUser.getUid();
                if (doctorAuth.equals(Objects.requireNonNull(snapshot.child("idUserAuth").getValue()).toString())) {
                    doctorId = snapshot.getKey();
                    doctorCategory = Objects.requireNonNull(snapshot.child("doctorCategory").getValue()).toString();
                    doctorName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                    doctorImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                    typeUser = Objects.requireNonNull(snapshot.child("typeUser").getValue()).toString();
                    Log.e("ayat", "C " + doctorId);
                    Log.e("ayat", "C " + doctorCategory);
                    Log.e("ayat", "N " + doctorName);
                    Log.e("ayat", "I " + doctorImage);
                    Log.e("ayat", "I " + typeUser);
                    if (snapshot.exists()) {
//                            List<DataSnapshot> list = (List<DataSnapshot>) snapshot.getChildren();
                        if (typeUser.equals("دكتور")) {
                            idAuthDoctor = 1;
                            Log.e("testDoctor", "1" + firebaseUser.getUid());
                        } else {
                            Log.e("nadaTestAuth ", "مريض  ");
                            idAuthDoctor = 0;
                            Log.e("testDoctor", "0");
                        }
                    }
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
    public void onBackPressed() {
//        finish();
        super.onBackPressed();
    }

    public void getData() {
        View v = navigationView.getHeaderView(0);
        TextView testDrawer = v.findViewById(R.id.textdrawer);
        ImageView Image = v.findViewById(R.id.imagedrawe);

        FirebaseUser firebaseUser1 = mAuth.getCurrentUser();
        ref.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                assert firebaseUser1 != null;
                doctorAuth = firebaseUser1.getUid();

                if (doctorAuth.equals(Objects.requireNonNull(snapshot.child("idUserAuth").getValue()).toString())) {
                    userName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                    userImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                    Picasso.get().load(userImage).into(Image);
                    testDrawer.setText(userName);
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
    public void onItemClickList(int position, String id) {

    }
}


