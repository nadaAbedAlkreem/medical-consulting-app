package com.example.medicalconsultingapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medicalconsultingapplication.Authentication.LogInActivity;
 import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.adapter.IllnessAdapter;
import com.example.medicalconsultingapplication.adapter.RequestFriendsAdapter;
 import com.example.medicalconsultingapplication.fragment.ChatFragment;
 import com.example.medicalconsultingapplication.fragment.HomeFragment;
import com.example.medicalconsultingapplication.fragment.ProfileUserFragment;
import com.example.medicalconsultingapplication.model.Consultation;
import com.example.medicalconsultingapplication.model.Illness;
import com.example.medicalconsultingapplication.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DrawerNavigationActivity extends AppCompatActivity   implements  RequestFriendsAdapter.ItemClickListener     {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RequestFriendsAdapter requestFriendsAdapter;
    RecyclerView recyclerViewRequestFriends;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ConstraintLayout container;
    NavigationView navigationView;
    public  int idAuthDoctor  = -1;
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
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawerLayout);
        container = findViewById(R.id.mainContainer);
        navigationView = findViewById(R.id.navView);
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
                case R.id.navAddFriendRequest :
                    Dialog dialog = new Dialog(DrawerNavigationActivity.this);

                    dialog.setContentView(R.layout.dialog_friend_request);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    recyclerViewRequestFriends =  (RecyclerView)dialog.findViewById(R.id.recyRequestFriends) ;
                    recyclerViewRequestFriends.setHasFixedSize(true);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    recyclerViewRequestFriends.setLayoutManager(layoutManager);
                    ArrayList<Users> items = new ArrayList<>();

                    items.add(new Users("1" ," ندى الكحلوت " ,"data:image/jpeg;base64,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"));
                    items.add(new Users("2" ," ندى الكحلوت " ,"data:image/jpeg;base64,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"));
                    items.add(new Users("3" ," ندى الكحلوت " ,"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUVFRgVFRIYGBgZGBgYGRgYERgYGBoaGhgZGRgYGBgcIS4lHB4rIRgYJjgmKy8xNTU1GiQ7QDs0Py40NTEBDAwMEA8QHhISGjQhISQ0NDExNDQ0NDQ/NDQxNDE0NDQ0NDQ0MTQ0MTQ0MTQ0NDQxNDQ0NDQ0NDQxNDQ0NDE0Mf/AABEIAQMAwgMBIgACEQEDEQH/xAAbAAABBQEBAAAAAAAAAAAAAAACAAEDBAUGB//EAD0QAAIBAgIGCQMACAYDAAAAAAABAgMRBCEFEjFBUXEGImGBkaGxwfAy0eETI0JygqKy8QcUUmKSwjM00v/EABgBAQEBAQEAAAAAAAAAAAAAAAACAQME/8QAIBEBAQEAAwADAAMBAAAAAAAAAAECESExAxJBIjJRcf/aAAwDAQACEQMRAD8A4oCTCnEjuYoUQgEww01hmiRITQEaHsSQgFOGQYr3FYVgrAMIJIQDDxiHYeKAjlEYmkiPVDUbBkySSI2gymQ6QkOgEohoZDSYBjoiuPrATCIdYQE8kQtErYLAaKFcKMhmAh0NFEmqA0CSTIwkBFKArBV6iirsgo4m7+m6JtbJymUQrFqELq+rbm2vKxVrKzzj5texn2V9QuaW1kf+bhe17c016idTmVqso78u4TTLle1gblGnUtknkWI11seXoVyzhLJkckECzQyHuFYYMJCY4rknAWhDsZhpCEIpiwgJoK4MmBEmSRYDEiWrCaDKusSQkUDaH1rDMrY6rqwb3vJc2Bm4nEucnwvtZYwss/jKNOKNHCSint9CKqNSNRaud+5fgq1Km/Vl3t28LpF6FW6yfhkVsTHm++RKmdOr8v8A3IZ1Vw/mJ5w7H85kE+RrEM3w+eAUKu5gTS4W8Rowb2FJXaVVxdne3ai5e+aM6eaV7LIkwlez1WxKyxcuIKwrAC0KwaQ7QELHuPMAArCGuIAtYTYzEgHEOkIAZEkJEchosCedRJXZSr0dfOTyz1V7vw8+RZhDXdtyzfcS6l+Rl0qZZ0MA/lmy7h8I8lq2fDVbZfpatrJJvjwOk0VgXJJ3yb3L58uRdcLznljYHQ05bV3G3huiSkrzXl9zr8BgYpK68jQlRyJ5tdZnMcDLonBfseYE+isP9PmdpWgV5xIvLpM5/wAcDiejMFu8jJnohQlsPRsTAwdI4dWeRs1U6xHEYqi7Wte3O5l1IWzWfajoNJUdXPcYMnqya2p+aO2by82pxV/B1taPasmWUjOwjSlk8pepqQKRRamRG0SuZE2BHNAMlZFMBhDXEBKxhrjOQBXFcESYCYl8+49iajRvZcfQy1UiehBKF5ZXzfG25epBUbns6sV27tnj9izjZbEtmXzyI6yUIdv983ys/EhdWtHRi+qlvV3x5npGiMOlFdVWtlx/H5PPej8FJRyz1rvjnc9QwlLVS5E69dMzrldgia5BFh3NjUFeNyhNGjMp1IWZNi4oVzFxpvYqJi42g9xNivxzOPpKSaZyONpauXB/2O1xsLHKaYjbvXz2Omb28/yRQoz4d690a+Hq6yMGnJmhhKln2M6+OPrSuM2MxFMMwZB2BaDEdhBWEAFx7CSCsS0yQ4kh1EBkjQwMc7vcm+95L3KKjmaNHqxS/wBWfcthNXlDUzmo/N33K+IvKK/3O3p9ixD60+f/AF+xHTV9RcIJ99l9yYqui6JYW9VRWxO/Zllc9CqT1Vsbe5Lecj0Ep9act1kkdjKOVyb665n8VGpSxE4t/p40uChBSa5ylv7jmNJSxMJdTHOfY6kU+5PaXdOYHEYicYqTjRi+vGMtWc1vSbyXf5HJ1+jdq8dalNQjlK8pTcutfWu3l1crLjtKl5npZZeOOXbaB0nXmnGus1bVlkm+dsjUxM8rnL9H8LKE5xjrKms4Kbu1/t23On0o7UW1tSJtdJ1wwdN6YcF1FrTeUV28X2HJywuJrvWq4lRXDXtb+GNkRTqTqVlra2q3aWq1ravCN3tfHsKmI0JJVLunPUskus5O985XvldbvM3M6ct3m+WrFbRs6TvGtKXY/pfY17mVpuPVT7fUu4bDVoScOs4X6utm12PuA0/StDPihPU6n8fHNRW7wLeEn8+dxTbyvvRawru12/PZHWuEa1N5BWI6Ly8ixFGwobAsKbAuYGENcQDyiJIlaGUQAsEkGkKMQHpwzLWtnyX4I6Std8EPT38kRp0yjpfX3DfTd/7VbwX4Hj9S8PFr7jY2CcbXtdZtbsjFO3/w9leE1wlb1O2Ucjz/APw5rXdWO/qP1X2PQoMyeumfFKvCS2ehSm7uzjc28TNJbDn8VjFGVltexInU4dc9xdw+Gtsj5EuLp3hJdgNFtxvN6u/N2QTnFw1lUUovY0735WN/GfrznDU1Cq21vaOhpwUlkY2maOpUdpZ/Vl7mroXFKcdmZLZJyWJw0YptKzON6Tu9KT7V/Uju8dsOD6Tf+Ka/d/qibn1PyyTLj6T2k2FyduDX2K1N5lqjlJ+Pod68UbFHfz/JYRWw728l6FkQoZICSDkyOUgBENrDgWVEfVDSCSKYiUSRIJIdIlpp5R2bX5L+7GhO13wYU5K/HYlyRXcuo78G/L8nOusNOVnFrs9Iv2ZZxMOq3tyv6X9yti8rrhF+S/Je1k4xXF6vjs9UY1N/htpBvGVISyvB2X7sl7M9aR4NofFf5fH05vKOuoy5TWq78nn3Hu9KalErXVbi3jtm6YxrirRV23Zc2LRWjEuvN6035diDx+E15wvsTfoZWJ0diIYiM5V5ywzycIy1XB3Szt9Uduf93zndd7euHTzpxas0mjI0hTv1YWTvvWXeaENHU3GOrN/Q3rKcruzSvtz27yHE6IeSjXkuq3K9nw2PmyrLUzUn64XH6On+kc5u8n5LguwnwktRrcSaawsoQcniJuWrN7Fm07Rsrdpi9H8PXm9avN6t24xaSla+V7LJdm0njpv276dTjZ60FJb0cF0klanO+9xX8yO+0hJRgordHzeZ5p0sxK6tNPO+tLuul5t+BuO6j5dfxc3Hai8lmuX5KcImhOOS7vb8navLF/BSv4L1Zbkyho+WXj6ltsNM2RyDbAYOAiEIMakUOkNFhophtUOKsnJ7kJIVeWyK4pv2+5Or0rM7VakNi37HzecgZrJR4yjHxafpcmUb5/Lbn4ETXWj2Jzf/AF9TlfXWeAx6yb7SzTl1E+Dg/wClfcgxS6rfty+wMZ/qlfdCDfcm/VCFZ3SKlaopbn7t29D03oRp/wDS0lCcuvBJO/7SWSkvc8903HWUX+6+9dU0dAU2mpRbTTya3G68jc+17DTkmTtbmc9orSLyU8nx3G/CaktpEdKo4nAQteN4vinYzKmHls/STatZdaWzxOgnTbWwzq9J7vQqqzf97ctj8JGGdrvi9pBh522/vPkaOPld9bcczpXSUaUZSbtfxfCKI7vRq8d1NpzTUYRcpPsS3t8F2nm9evKpOUpbZO/LgkHpHHTrS1pbN0b5JfftApQ2HfOfrHk3v7UdOPp7GhJZLufkVqUNnJ+hcqx6rXYjayH0e9q4P1LrRR0f9Uv4WaTQhUDBkSSQDQAWEFYQGhAmghU6ZOqZSQxRXnK75+m/7d5PXyVt728iGmr5++756nLVdczopx8/nsyBWbbf7UtnBLd4j4ipnbsfcviIpTsss3w8kvnayJ/q6lqdbxS8rsr4qpaNl2LwV/cmfVilvSz5v5YycRV1p6qeSv6Zs3M7Tq9LmNd4Jb7x9UdB0fhsujlauIvKMPm1ex3WhMNksjNeOnx910NKjlkXMPrw+h3XBkeGVi9BqxEdKjq6WlFdaDXJXMjEafgn1rpdsWXcS7u2RzXSFRiuZXNZzxFDS/SCEm1DP5xPP9I4yVWblJ5JtJbkuw1sdU1YSl4exz0dh1znjt5/k1aBrMv0IZfOCKls/A1cPDJl2ueYKjDPvJK/DsfovsFQhmvHyBq5z8v5X/8ARC0mBj1n2xXky8VMFlJ/ulplRNC0BIkYDDEYg7CA24RsG3vI9a4M3fJePzaxq8RuZzUM1rP1tyySAr1VFWSz4dvz5wixWMjBWvmu3ZzZj4nSMXtu+y3t+TnM2ul1MrE8QldJpy2tq7z7ifDqyvnfbeXzcZC0olspx5v7JENbHznley4JJIr6VP3jTxmKytF37fIzozULu92VFdhqORUzwi65M5u7lfP8r7HrHRHGRq0oy32szyrV+d6N7olpJ0puF8nmvf28zPkzzF/FrjX/AF65awLqZbbGbT0ipLbtG/zaPM9a3O+bOP6R1m3Y6DEYxKLzOJ0nidabd9hefXPXjn9N1vpiub9jPhsQ+Om3Ntkcdh6JOI8mrzUlPau43YQtB/OJgU9p0sIdXv8AczSslTjZ8kUoO7T3a0vZIuznqxnLuXnb2KeGj1Y+PzyIWnw0uvbs+xesZeHl+s7jQcy54jXo5IjH1hmGEIQgNCtXjBXlLVitrb8lxZiY7TWt1YJpcXlcqYrEOctaXcty5fcqygb9f9Z9vyAnUcndu/tyRFJhTVgWUkDJIgSQUGGnhttx+fclIN65k4DihU1JRmtzv914XGAkB3OAxmSzurJp9m4tzxfacx0fxF4uD2xf8r/N/I1Y5yPNrPFevOuYtYnEtqyZh4ngb0aa1W7GM4Xbb4mw05vSELS7k/b2IImhpeN52S3W8yhY758eXXoqe1c1+DpqLyjzb8zmaOclzR0ankuXsTpWVbHz6qjxld/PDwHpfTyXsVcVO80uH2v6stxeT5L2+5H4v9QU3+sv82XNCRlXtPwNMvLno8WSRIkySBTB6ohXES1gsYdjHRCOcbkElYsNgzVzBAKA2wcNLK+b5cyRtkFQkpTvk9vqAWswpIFbSRgS6LxGpUi9z6r5S+zs+46qLzOKmjrNG1teEZPba0uccm+/b3nL5J+u3xa/G0/otxKVbD8Cem8g60+qco7OQ0tDr34q35+cTKe35y9ja0ys01xMaa2eHp7noz48u/7Ghk1zRtKfsY3DmX4VNnJehmjIKr6/i/MuQndd32+xQqPrLv8At7FujsS+bCL46RDUfX8DUjsMmo+t84L7mthneKfYXEaO4hxCUR1EMIQeqMBgMFhMBloCwWwmA2AE0NEJoGO0AKgMQ6qAiBYi7jgodSvzAGRsdHK+c4Pf1lzWT8reBjyDwtfUnGS/Zd+a2NeDZOpzOF4vF5dzFgVXkFCSdms01dPinmhqiPO9SlPAa8KrtfUpzl3pZe77jk5rK/t83s9L0RR6lR8bR8E2/wCpHm1aDjrReeq7bf8AS7N+PsejPjy/J/ZFe5Yw093cV7Wy+ZjxlZ3Ns5jM3ip6u7mW6Tz70VaucbktKfVv2o53x0noa+UvD0NbRzvDldGRidvh88jS0VLKS7TYnS/YTHBZrCuIEQGEwWOxmWgLI5kjAkBGpCk9jBkK4B1V1bkMSap9JFACeBFUJ4IhqoB4yuvUaRFGVncmYa6zo/X16KV84PVfLbF8rZfws0mjlejOK1K6i31Z9SXN/S/+VvFnXVKbi2mcN54r0fHrmcNnRdL9T+85Pz1fY820/T1a9WNkus/5lrPzPUtGR/Vx/dXnn7nn/S7D9eNVbJNp9z1l5X8Dply365ySyi12r3Xk2C0SQV4yXC0u9bfK5Gi3NJQna6ex2XJ5/YljdJrvXdmVZLLvJqFS+3avNbCdReb+J8Vmky1oqfWa4r56lRq8WuH917hYCpaaJiq6CIpoaLFJmpAIC4gMNjMcZloMwGEwGwI5EcmHJgb0BPNZEUFmTPYRUlmBZisiCqTzdiOccrgVpBwlu+XBaGQBnpGi63+ZoRlda+rb+NZNPgm/U84kdN0KxdpypN21lrR5rKS71b/iTqcxWbxXoGEyg4O6tHV7dlvyU9JdGo4ijKEJtT+qDlbV1lsTsrpPZftNfDKM4pTWa2NOzW1e7yeRZpwjT636RtLdq58rmTptvLw+tTlTnKE4uM4txlF7U87p+hVgbfTHEOeOrTtZuUMuUIJeNrmJHaWgQGxhjNAWaU9ku5iWUiGg9q4k8nsOdnFdJeY36UrpDyIMLO8UTXKjAWEOI1jCGYhGpBIBiEBHIjW0QgLS2AYcYQElXagpbBCAqgiEAe5fN5d0RNrEU7O3XS9V7jiFHrOjpu20t1hCOa3lXTP/ANuf7sP6TBW3u9hCLnib6kEIRrDw+osU9neIRGvV5aeC2FsQjYymEIRo/9k="));
                    Log.e("nada" , items.get(0).getUserName());
                    requestFriendsAdapter = new RequestFriendsAdapter(DrawerNavigationActivity.this, items, this);

                    recyclerViewRequestFriends.setAdapter(requestFriendsAdapter);
                    requestFriendsAdapter.notifyDataSetChanged();

                    Log.e("nada",String.valueOf(requestFriendsAdapter.getItemCount()))  ;


                    dialog.show();



















                    break;

                case R.id.navLogOut: {
//                    SharedPreferences sharedPref = getSharedPreferences("loginAndLogoutOP", Context.MODE_PRIVATE);
//                    sharedPref.edit().putBoolean(String.valueOf(R.string.LoginActive), false).apply();
//                    Intent intent = new Intent(DrawerNavigationActivity.this, LogInActivity.class);


                    break;

                }

                case R.id.navChat: {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,
                            new ChatFragment()).addToBackStack("").commit();
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

    @Override
    public void onItemClickList(int position, String id) {

    }
}



