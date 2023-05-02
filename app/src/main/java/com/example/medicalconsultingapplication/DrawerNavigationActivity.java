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

 import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.ImageView;


  import com.example.medicalconsultingapplication.Authentication.LogInActivity;
import com.example.medicalconsultingapplication.fragment.HomeFragment;
import com.example.medicalconsultingapplication.fragment.ProfileUserFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
 import com.google.firebase.firestore.QuerySnapshot;
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


    public  int idAuthDoctor  = -1;
    // 0 paition , 1 doctor
    ImageView imagedrawe;

  //  LinearLayout header;

    // 0 paition , 1 doctor

    ImageView i ;

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
       mAuth=FirebaseAuth.getInstance();
       db=FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawerLayout);
        container = findViewById(R.id.mainContainer);
        navigationView = findViewById(R.id.navView);
        imagedrawe=findViewById(R.id.imagedrawe);

        checkTypeUesrCurrent();
       //  header=findViewById(R.id.header);
        getData();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
    //   Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
       toggle.syncState();
        swipe(new HomeFragment());

         db = FirebaseFirestore.getInstance();
         drawerLayout = findViewById(R.id.drawerLayout);
        container = findViewById(R.id.mainContainer);
        navigationView = findViewById(R.id.navView);
         drawerLayout = findViewById(R.id.drawerLayout);
        container = findViewById(R.id.mainContainer);
        navigationView = findViewById(R.id.navView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navHome:
                    HomeFragment  HomeFragment = new HomeFragment() ;
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction() ;
                    Bundle data1 = new Bundle() ;
                    data1.putInt("idAuthDoctor1" , idAuthDoctor);
                    Log.e("ahmed",""+idAuthDoctor);
                 HomeFragment.setArguments(data1);
                    fragmentTransaction1.replace(R.id.mainContainer,
                          HomeFragment).addToBackStack("").commit();

                break;
                case R.id.navProfile:
                    Bundle data = new Bundle();

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
//                    SharedPreferences sharedPref = getSharedPreferences("loginAndLogoutOP", Context.MODE_PRIVATE);
//                    sharedPref.edit().putBoolean(String.valueOf(R.string.LoginActive), false).apply();
//                    Intent intent = new Intent(DrawerNavigationActivity.this, LogInActivity.class);

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


     public void getData(){
          View v= navigationView.getHeaderView(0);
         TextView testDrawer =  v.findViewById(R.id.textdrawer) ;
         ImageView Image =  v.findViewById(R.id.imagedrawe) ;

         FirebaseUser firebaseUser1 = mAuth.getCurrentUser();
         db.collection("Users").whereEqualTo("idUserAuth",firebaseUser1.getUid())
                 .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                     @Override
                     public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                         if (queryDocumentSnapshots.isEmpty()) {
                             Log.d("drn", "onSuccess: LIST EMPTY");
                             return;
                         } else {
                             for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                                 if (documentSnapshot.exists()){
                                     String username= documentSnapshot.getString("userName");
                                     String userImage=documentSnapshot.getString("userImage");
                                    Picasso.get().load(userImage).into(Image);

                                     testDrawer.setText(username);
                                     Log.e("yy","hh");
                                     Log.e("testttttt",username);
                                     Log.e("tet",userImage);
                                 }
                                 }
                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Log.e("LogDATA", "get failed with ");
                     }
                 });
     }
}




