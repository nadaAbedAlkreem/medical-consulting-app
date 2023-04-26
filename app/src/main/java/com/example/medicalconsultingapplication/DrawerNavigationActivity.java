package com.example.medicalconsultingapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.medicalconsultingapplication.Authentication.LogInActivity;
import com.example.medicalconsultingapplication.fragment.HomeFragment;
import com.example.medicalconsultingapplication.fragment.ProfileUserFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.Objects;

public class DrawerNavigationActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ConstraintLayout container;
    NavigationView navigationView;
    public int idAuthDoctor = 0;
    //         0 paition , 1 doctor
    String doctorId;
    String doctorAuth;
    String doctorCategory;
    String doctorName;
    String doctorImage;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_navigation);
        mAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawerLayout);
        container = findViewById(R.id.mainContainer);
        navigationView = findViewById(R.id.navView);
        Bundle data = new Bundle();
        checkTypeUesrCurrent();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        swipe(new HomeFragment());
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navHome:
//                    swipe(new HomeFragment());
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                    data.putString("doctorCategory", doctorCategory);//category
                    homeFragment.setArguments(data);
                    fragmentTransactionHome.replace(R.id.mainContainer,
                            homeFragment).addToBackStack("").commit();
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
                case R.id.navLogOut: {
                    Intent intent = new Intent(this, LogInActivity.class);
                    startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.navNotification);
        Log.e("ayat", "ayat" + item);
        item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    private void checkTypeUesrCurrent() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        db.collection("Users").whereEqualTo("idUserAuth", firebaseUser.getUid())
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    doctorAuth = firebaseUser.getUid();
                    for (QueryDocumentSnapshot getData : queryDocumentSnapshots) {
                        doctorId = getData.getId();
                        doctorCategory = getData.get("doctorCategory").toString();
                        doctorName = getData.get("userName").toString();
                        doctorImage = getData.get("userImage").toString();
                        //doctorCategory
//                        getData.get("doctorCategory");
                        Log.e("testDoctorAyat", "" + getData.getId());
                        Log.e("testDoctorAyatDoctor", "" + doctorAuth);
                        Log.e("testDoctorAyatDoctorType", "" + getData.get("doctorCategory"));
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Log.e("typeUser", String.valueOf(d.get("typeUser")));
                                if (String.valueOf(d.get("typeUser")).equals("دكتور")) {
                                    idAuthDoctor = 1;
                                    Log.e("testDoctor", "1" + firebaseUser.getUid());
                                } else {
                                    Log.e("nadaTestAuth ", "مريض  ");
                                    idAuthDoctor = 0;
                                    Log.e("testDoctor", "0");
                                }
                            }
                        } else {
                            Log.e("AuthIDUSER", "empty");

                        }

                    }
                }).addOnFailureListener(e -> Log.e("AuthIDUSER", "FAILD"));
    }

    @Override
    public void onBackPressed() {
//        finish();
        super.onBackPressed();
    }
}