package com.example.medicalconsultingapplication;

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

import java.util.List;
import java.util.Objects;

public class DrawerNavigationActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ConstraintLayout container;
    NavigationView navigationView;
    public  int idAuthDoctor  = -1;
    // 0 paition , 1 doctor
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    ImageView i ;


    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_navigation);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
         drawerLayout = findViewById(R.id.drawerLayout);
        container = findViewById(R.id.mainContainer);
        navigationView = findViewById(R.id.navView);
        checkTypeUesrCurrent();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        swipe(new HomeFragment());
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navHome:
                    swipe(new HomeFragment());
                    break;
                case R.id.navProfile:
//                    swipe(new ProfileUserFragment());
                     ProfileUserFragment  profileUserFragment = new ProfileUserFragment() ;
                    FragmentTransaction  fragmentTransaction = getSupportFragmentManager().beginTransaction() ;
                   Bundle data = new Bundle() ;
                   data.putInt("idAuthDoctor" , idAuthDoctor);
                   profileUserFragment.setArguments(data);
                       fragmentTransaction.replace(R.id.mainContainer,
                            profileUserFragment).addToBackStack("").commit();
                    break;
                case R.id.navLogOut: {
                    SharedPreferences sharedPref = getSharedPreferences("loginAndLogoutOP", Context.MODE_PRIVATE) ;
                    sharedPref.edit().putBoolean(String.valueOf(R.string.LoginActive) , false ).apply();
                    Intent intent = new Intent(DrawerNavigationActivity.this  , LogInActivity.class);
                    startActivity(intent);
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    private void checkTypeUesrCurrent()
    {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
              db.collection("Users").whereEqualTo("idUserAuth" , firebaseUser.getUid())
              .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                           @Override
                      public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty())
                           {
                             List<DocumentSnapshot> list  = queryDocumentSnapshots.getDocuments() ;
                                  for (DocumentSnapshot d : list) {
                                   Log.e("typeUser" , String.valueOf(d.get("typeUser"))) ;
                                  if( String.valueOf(d.get("typeUser")).equals("دكتور"))
                                     {

                                       idAuthDoctor = 1 ;
                                       Log.e("testDoctor" , "1") ;
                                   }else{
                                     Log.e("nadaTestAuth " , "مريض  ")  ;
                                     idAuthDoctor = 0 ;
                                      Log.e("testDoctor" , "0") ;


                                  }


        }
        }else{
        Log.e("AuthIDUSER" , "empty") ;

        }
        }
        }).addOnFailureListener(new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception e) {
        Log.e("AuthIDUSER" , "FAILD") ;
        }
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

    @Override
    public void onBackPressed() {
//        finish();
        super.onBackPressed();
    }
}