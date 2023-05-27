package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.L;
import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationProfileAdapter;
import com.example.medicalconsultingapplication.model.Consultation;
import com.example.medicalconsultingapplication.operationConsulting.AddConsultionActivity;
import com.example.medicalconsultingapplication.operationConsulting.ConsultingFragment;
import com.example.medicalconsultingapplication.operationConsulting.UpdateConsultionActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ProfileUserFragment extends Fragment implements ConsultationProfileAdapter.ItemClickListener {
    ConsultationProfileAdapter consultationProfileAdapter;
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    ImageView imageUserCurrent;
    ImageView imageNotification;
    TextView nameUserCurrent;
    Button viewDetailsConsulting, updateConsulting, deleteConsulting, consel;
    ArrayList<Consultation> items = new ArrayList<>();
    RecyclerView reDoctorConsultationsProfile;
    FloatingActionButton fAdd;
    String doctorName;
    String doctorImage;
    SwipeRefreshLayout refreshCon;
    String conId;
    Bundle data = new Bundle();
    // notification
    Button btnSendNotification;
    EditText edtTitle;
    EditText edtContent;
    String token;

    //track
    Calendar calendar = Calendar.getInstance();
    int houres = calendar.get(Calendar.HOUR);
    int minutes = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    String doctorCategory;
    String category;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        fAdd = view.findViewById(R.id.fAdd);
        reDoctorConsultationsProfile = view.findViewById(R.id.reDoctorConsultationsProfile);
        imageUserCurrent = view.findViewById(R.id.imageProfileUser);
        imageNotification = view.findViewById(R.id.imageNotification);
        nameUserCurrent = view.findViewById(R.id.txtProfileUserName);
        refreshCon = view.findViewById(R.id.refreshCon);


        assert getArguments() != null;
        int typeUser = getArguments().getInt("idAuthDoctor");
        String id_doctor_home = getArguments().getString("id_doctor_home");
        if(id_doctor_home != null)
        {
            Toast.makeText(requireContext(), id_doctor_home, Toast.LENGTH_SHORT).show();
            getConsultstionDataHomeDoctors();
        }
        Log.e("messageNada", String.valueOf(typeUser));
//        refreshCon.setOnRefreshListener(() -> {
//            if (refreshCon.isRefreshing()) {
//                refreshCon.setRefreshing(false);
//            }
//            items.clear();
//            getConsultstionData();
//        });
         ref.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                if (id_doctor_home != null)
                {
                    Log.e("nada" , snapshot.child("idUserAuth").getValue().toString());
                    Log.e("nadai" ,id_doctor_home);
                    if(Objects.requireNonNull(snapshot.child("idUserAuth").getValue()).toString().equals(id_doctor_home) )
                    {
                        Log.e("nadatrr" , "jjj") ;
                        doctorName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                        doctorImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                        Picasso.get().load(doctorImage).into(imageUserCurrent);
                        nameUserCurrent.setText(doctorName);
                    }
                }else{
                    if (Objects.requireNonNull(
     snapshot.child("idUserAuth").getValue()).toString().equals(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())) {
                         doctorName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                        doctorImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                        Picasso.get().load(doctorImage).into(imageUserCurrent);
                        nameUserCurrent.setText(doctorName);
                        doctorName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                        doctorImage = Objects.requireNonNull(snapshot.child("userImage").getValue()).toString();
                        Picasso.get().load(doctorImage).into(imageUserCurrent);
                        nameUserCurrent.setText(doctorName);
                        Log.e("nada44" , doctorName) ;

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
        if (typeUser == 1) {
            Log.e("tesr 1", "jih");
            imageNotification.setVisibility(View.VISIBLE);
            getConsultstionData();
        } else {
            imageNotification.setVisibility(View.GONE);
        }

        assert getArguments() != null;
        int idAuthDoctor = getArguments().getInt("idAuthDoctor");
        String doctorId = getArguments().getString("doctorId");
//        Log.e("test" , doctorId);
        String doctorAuth = getArguments().getString("doctorAuth");
        doctorCategory = getArguments().getString("doctorCategory");
        if (Objects.equals(doctorCategory, "القلب")) {
            category = "heart";
        } else if (Objects.equals(doctorCategory, "الكلى")) {
            category = "kidneys";
        } else if (Objects.equals(doctorCategory, "الرئة")) {
            category = "lung";
        } else if (Objects.equals(doctorCategory, "المعدة")) {
            category = "stomach";
        } else if (Objects.equals(doctorCategory, "السرطان")) {
            category = "cancer";
        }
        Log.e("TAG", "onCreateView: " + doctorCategory);
        if (idAuthDoctor == 1) {
            doctorName = getArguments().getString("userName");
            doctorImage = getArguments().getString("userImage");
        }
        Log.e("messageNada", String.valueOf(idAuthDoctor));
        Log.e("messageNada", String.valueOf(doctorId));
 
        if (idAuthDoctor != 1 || id_doctor_home != null  ) {
             fAdd.setVisibility(View.GONE);
        }

        consultationProfileAdapter = new ConsultationProfileAdapter(getContext(), items, this);
        reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);
        fAdd.setOnClickListener(v ->
        {
            Intent intent = new Intent(getContext(), AddConsultionActivity.class);
            intent.putExtra("doctorId", doctorId);
            intent.putExtra("doctorAuth", doctorAuth);
            intent.putExtra("doctorCategory", doctorCategory);
            intent.putExtra("userName", doctorName);
            intent.putExtra("userImage", doctorImage);
            startActivity(intent);
        });
 
        // notification
        imageNotification.setOnClickListener(v -> {
            Log.e("notification", "onCreateView: ");
            db.collection("Notification").whereEqualTo("category", doctorCategory).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<String> targetTokens = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                token = documentSnapshot.getString("token");
                                if (token != null && !token.isEmpty()) {
                                    targetTokens.add(token);
                                }
                            }
                            Dialog dialog = new Dialog(getActivity());
                            dialog.setContentView(R.layout.dialog_notif);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.setCancelable(false);
                            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.setCanceledOnTouchOutside(true);
                            edtTitle = dialog.findViewById(R.id.notTitle);
                            edtContent = dialog.findViewById(R.id.notContent);
                            btnSendNotification = dialog.findViewById(R.id.btnSendNotif);

                            btnSendNotification.setOnClickListener(v1 -> {
                                String title = edtTitle.getText().toString();
                                String content = edtContent.getText().toString();
                                sendNotificationToMultipleDevice(title, content);
                                Log.e("Tokens", "onCreateView: " + targetTokens.size());
                                dialog.dismiss();
                            });
                            dialog.show();
                        }
                    });
        });
        return view;

    }

    private void sendNotificationToMultipleDevice(String title, String body) {

        Thread thread = new Thread(() -> {
            try {
                JSONObject notification = new JSONObject();
                JSONObject notificationBody = new JSONObject();
                notificationBody.put("title", title);
                notificationBody.put("body", body);

                notification.put("to", "/topics/" + category);
                notification.put("data", notificationBody);

                String FCM_API = "https://fcm.googleapis.com/fcm/send";

                Log.e("TAG", "sendNotificationToMultipleDevice: " + FCM_API);
                URL url = new URL(FCM_API);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Authorization", "key=AAAAZAZQN1E:APA91bEuG7kAZUDLLzHbLYMb8lG_Gz7HXVuo3bIHhFqArIePRESsXBsST0h69Zp8AlWXRnuu-1RSVqiEY5RTPiQkbteh2ZnA7Lt6BDt7Z5sLfotLJRgKUD_DrnB_mpWdVSB1q7qFmm5I");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(notification.toString().getBytes());
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.e("TAG", "sendNotificationToMultipleDevices: " + responseCode);
                    Toast.makeText(requireContext(), "Send Notification Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("TAG", "sendNotificationToMultipleDevice: error");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }

 private void getConsultstionDataHomeDoctors()
    {
          String id_doctor_home = getArguments().getString("id_doctor_home");
         Log.e("test_id_doctor" , id_doctor_home);
        db.collection("Consultion").whereEqualTo("doctorAuth", Objects.requireNonNull(id_doctor_home)
                ).get().addOnSuccessListener(queryDocumentSnapshots -> {

            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            if (!list.isEmpty()) {
                for (DocumentSnapshot d : list) {
                    conId = d.getId();
                    Log.e("TAG", "getConsultstionDataAyat: " + conId);
                    Consultation result = new Consultation(d.getId(), d.getString("conLogo"),
                            d.getString("title")
                    );
                    items.add(result);
                    consultationProfileAdapter =
                            new ConsultationProfileAdapter(getContext(), items, ProfileUserFragment.this);
                    reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);


                }

            } else {
                Log.e("ttttt", "empty");

            }


         }).addOnFailureListener(e -> Log.e("ttttttttttttttt", "FAILD"));



    }












    @Override
    public void onItemClickList(int position, String id) {
        String id_doctor_home = getArguments().getString("id_doctor_home") ;
        if (id_doctor_home == null) {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_crud);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(true);

            viewDetailsConsulting = dialog.findViewById(R.id.view_details);
            updateConsulting = dialog.findViewById(R.id.update_consulting);
            deleteConsulting = dialog.findViewById(R.id.delete_consulting);
            consel = dialog.findViewById(R.id.consel);
            viewDetailsConsulting.setOnClickListener(v -> {
                ConsultingFragment consultingFragment = new ConsultingFragment();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                data.putString("conId", conId); // con Document id
                Log.e("TAG", "onItemClickList: " + conId);
                consultingFragment.setArguments(data);
                fragmentTransaction.replace(R.id.mainContainer,
                        consultingFragment).addToBackStack("").commit();
                dialog.dismiss();
            });
            updateConsulting.setOnClickListener(v -> {
                Intent intent1 = new Intent(getContext(), UpdateConsultionActivity.class);
                intent1.putExtra("idClickUpdateItemConsulting", id);
                Log.e("ttttt", id);

                startActivity(intent1);


            });
            deleteConsulting.setOnClickListener(v -> {
                Log.e("idPosition", id);

                db.collection("Consultion").document(id)
                        .delete()
                        .addOnSuccessListener(unused -> {
                            dialog.dismiss();
                            items.remove(position); // updating source
                            consultationProfileAdapter.notifyItemRemoved(position);
                            Log.e("nada", "success delete");
                        }).addOnFailureListener(e -> Log.e("nada", "Failure delete"));
            });
            consel.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        }
    }


    public void getConsultstionData() {
        // mAuth.getCurrentUser().getUid()
        db.collection("Consultion").whereEqualTo("doctorAuth", Objects.requireNonNull(mAuth.getCurrentUser())
                .getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            if (!list.isEmpty()) {
                for (DocumentSnapshot d : list) {
                    conId = d.getId();
                    Log.e("TAG", "getConsultstionDataAyat: " + conId);
                    Consultation result = new Consultation(d.getId(), d.getString("conLogo"),
                            d.getString("title")
                    );
                    items.add(result);
                    consultationProfileAdapter =
                            new ConsultationProfileAdapter(getContext(), items, ProfileUserFragment.this);
                    reDoctorConsultationsProfile.setAdapter(consultationProfileAdapter);
                }
            } else {
                Log.e("ttttt", "empty");
            }
        }).addOnFailureListener(e -> Log.e("ttttttttttttttt", "FAILD"));
    }


    @Override
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
        Traffic.put("screen_name", "Profile");


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TrackUsers")
                .add(Traffic)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e("TAG", "Data added successfully to database");
                    }
                                      }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Failed to add database");


                    }
                });
        super.onPause();
    }

}