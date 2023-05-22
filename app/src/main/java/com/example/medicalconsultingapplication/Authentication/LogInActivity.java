package com.example.medicalconsultingapplication.Authentication;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalconsultingapplication.DrawerNavigationActivity;
import com.example.medicalconsultingapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


public class LogInActivity extends AppCompatActivity {

    EditText textEditEmail;
    EditText textEditPassword;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button LoginBtn;
    TextView signup;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        textEditEmail = findViewById(R.id.email);
        textEditPassword = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.log);
        firebaseAuth=FirebaseAuth.getInstance();
        signup = findViewById(R.id.signup);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        LoginBtn.setOnClickListener(view -> {
            String textEmail = textEditEmail.getText().toString();
            String textPassword = textEditPassword.getText().toString();
            Log.e("email", textEmail);
            if (TextUtils.isEmpty(textEmail)) {
                Toast.makeText(LogInActivity.this, "please enter your email   ", Toast.LENGTH_SHORT).show();
                textEditEmail.setError(" email is required  ");
                textEditEmail.requestFocus();
            } else if (TextUtils.isEmpty(textPassword)) {
                Toast.makeText(LogInActivity.this, "please enter your password   ", Toast.LENGTH_SHORT).show();
                textEditPassword.setError(" password is required");
                textEditPassword.requestFocus();
            } else {
                loginFirebaseDB(textEmail, textPassword);
            }

        });
        signup.setOnClickListener(view -> {
                    Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this).toBundle());
                }
        );


    }

    private void loginFirebaseDB(String textEmail, String textPassword) {
        mAuth.signInWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser currentUser=firebaseAuth.getCurrentUser();

                SharedPreferences sharedPref = getSharedPreferences("loginAndLogoutOP", Context.MODE_PRIVATE);
                sharedPref.edit().putString(String.valueOf(R.string.LoginActive),"true").apply();
                sharedPref.edit().putBoolean("only_once",true).apply();
                Intent intent = new Intent(LogInActivity.this, DrawerNavigationActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this).toBundle());

            } else {
                Toast.makeText(LogInActivity.this, "something is wrong !", Toast.LENGTH_SHORT).show();
            }

        });
    }
}


//
