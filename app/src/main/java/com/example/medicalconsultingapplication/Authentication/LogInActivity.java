package com.example.medicalconsultingapplication.Authentication;

import android.app.ActivityOptions;
import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity {

    EditText textEditEmail;
    EditText textEditPassword;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button LoginBtn;
    TextView signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        textEditEmail = findViewById(R.id.email);
        textEditPassword = findViewById(R.id.password);
        LoginBtn = findViewById(R.id.log);
        signup = findViewById(R.id.signup);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        LoginBtn.setOnClickListener(view -> {
            String textEmail = textEditEmail.getText().toString();
            String textPassword = textEditPassword.getText().toString();
            Log.e("email", textEmail);
            if (TextUtils.isEmpty(textEmail)) {
                Toast.makeText(LogInActivity.this, "please enter your email   ", Toast.LENGTH_SHORT).show();
                textEditEmail.setError(" email is required  ");
                textEditEmail.requestFocus();
            }
//                else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
//                    Toast.makeText(LogInActivity.this, "please enter your email true    ", Toast.LENGTH_SHORT).show();
//                    textEditEmail.setError(" email is vaild true   ");
//                    textEditEmail.requestFocus();
//                }
            else if (TextUtils.isEmpty(textPassword)) {
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
//                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                Intent intent = new Intent(LogInActivity.this, DrawerNavigationActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this).toBundle());


            } else {
                Toast.makeText(LogInActivity.this, "something is wrong !", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
//      db.collection("Users").whereEqualTo("idUserAuth" , firebaseUser.getUid())
//              .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//@Override
//public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//        if(!queryDocumentSnapshots.isEmpty())
//        {
//        List<DocumentSnapshot> list  = queryDocumentSnapshots.getDocuments() ;
//        for (DocumentSnapshot d : list) {
//        Log.e("typeUser" , String.valueOf(d.get("typeUser"))) ;
//        if( String.valueOf(d.get("typeUser")) == "دكتور")
//        {
//
//        }else{
//
//        }
//
//        Intent intent = new Intent(LogInActivity.this  , HomeActivity.class);
//
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LogInActivity.this).toBundle());
//
//
//        }
//        }else{
//        Log.e("AuthIDUSER" , "empty") ;
//
//        }
//        }
//        }).addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Log.e("AuthIDUSER" , "FAILD") ;
//        }
//        });
//
//
