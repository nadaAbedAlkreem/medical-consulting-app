package com.example.medicalconsultingapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.firebase.analytics.FirebaseAnalytics;
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
import java.util.Calendar;
import java.util.HashMap;
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
    private FirebaseAnalytics mfirebaseAnalystic;
    Calendar calendar = Calendar.getInstance();
    int houres = calendar.get(Calendar.HOUR);
    int minutes = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

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
        mfirebaseAnalystic = FirebaseAnalytics.getInstance(this);
        ref = database.getReference("Users");
        checkTypeUesrCurrent();
        Log.e("idAuthDoctor", "onCreate: " + idAuthDoctor);
        getData();
        screenTrack("DrawerNavigationActivity");
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        swipe(new HomeFragment());

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navHome:
                     HomeFragment HomeFragment = new HomeFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    data.putInt("idAuthDoctor", idAuthDoctor);
//                    data.putInt("idAuthDoctor1", idAuthDoctor);
                    Log.e("ahmed", "" + idAuthDoctor);
                    data.putString("doctorCategory", doctorCategory);
                    HomeFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.mainContainer,
                            HomeFragment).addToBackStack("").commit();
                    btnEvent("id","drawer","home");
                    break;
                case R.id.navProfile:

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
                    btnEvent("id","drawer","navProfile");
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
                    recyclerViewRequestFriends.setHasFixedSize(true);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    recyclerViewRequestFriends.setLayoutManager(layoutManager);
                    ArrayList<Requests> items = new ArrayList<>();
                    mDatabase.child("Chat Requests").addChildEventListener(new ChildEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            String id = snapshot.getKey();
                            mAuth = FirebaseAuth.getInstance();

                            final String[] nameSender = {""};
                            final String[] imageSender = {""};
                            String userName = Objects.requireNonNull(snapshot.child("nameReviver").getValue()).toString();
                            String idRecievd = Objects.requireNonNull(snapshot.child("idRecievd").getValue()).toString();
                            String idSend = Objects.requireNonNull(snapshot.child("idSend").getValue()).toString();
                            String image = Objects.requireNonNull(snapshot.child("imageReciver").getValue()).toString();
                            String status = Objects.requireNonNull(snapshot.child("status").getValue()).toString();
                                 if(mAuth.getCurrentUser().getUid().equals(idRecievd)) {
                                     if (status.equals("process")) {


                                         Requests requests_friend = new Requests(id, idRecievd, idSend, status, image, userName );
                                         items.add(requests_friend);
                                         requestFriendsAdapter = new RequestFriendsAdapter(DrawerNavigationActivity.this, items , DrawerNavigationActivity.this);
                                         recyclerViewRequestFriends.setAdapter(requestFriendsAdapter);
                                         requestFriendsAdapter.notifyDataSetChanged();
                                      Log.e("nada", userName);
                                         Log.e("nada", String.valueOf(requestFriendsAdapter.getItemCount()));
                                     }
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
                    btnEvent("id","Draewr","logout");
                    break;

                }
                case R.id.navChat: {
//                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,
//                            new ChatFragment()).addToBackStack("").commit();
                    ChatFragment chatFragment = new ChatFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    data.putInt("idAuthDoctor", idAuthDoctor); // 1,0
                    data.putString("doctorAuth", doctorAuth);
                    data.putString("doctorId", doctorId);
                    chatFragment.setArguments(data);
                    fragmentTransaction.replace(R.id.mainContainer,
                            chatFragment).addToBackStack("").commit();
                     btnEvent("id","Draewr","navchate");
 
                     break;
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;

        });

    }

    private void swipe(HomeFragment fragment) {
        fragment = new HomeFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        data.putInt("idAuthDoctor", idAuthDoctor);
        Log.e("ahmed", "" + idAuthDoctor);
        data.putString("doctorCategory", doctorCategory);
        fragment.setArguments(data);
        fragmentTransaction.replace(R.id.mainContainer,fragment);

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


     public void screenTrack(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, name);
        mfirebaseAnalystic.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }
    public void btnEvent(String id, String name, String contentType) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType);
        mfirebaseAnalystic.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
    public void onPause() {
        Calendar calendar = Calendar.getInstance();
        int houres2 = calendar.get(Calendar.HOUR);
        int minutes2 = calendar.get(Calendar.MINUTE);
        int second2 = calendar.get(Calendar.SECOND);
        int h = houres2 - houres;
        int m = minutes2 - minutes;
        int s = second2 - second;
        HashMap<String, Object> Traffic = new HashMap<>();
        Traffic.put("time", h + ":" + m + ":" + s);
        Traffic.put("screen_name", "IlnessList");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TrackUsers")
                .add(Traffic)
                .addOnSuccessListener(documentReference -> Log.e("TAG", "Data added successfully to database"))
                .addOnFailureListener(e -> Log.e("TAG", "Failed to add database"));
        super.onPause();

}

    @Override
    public void onItemClickList(int position, String id) {

    }
}

