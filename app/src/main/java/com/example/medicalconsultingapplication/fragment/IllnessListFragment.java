package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationAdapter;
import com.example.medicalconsultingapplication.model.Consultation;
import com.example.medicalconsultingapplication.operationConsulting.ConsultingFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class IllnessListFragment extends Fragment implements ConsultationAdapter.ItemClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Consultation> items = new ArrayList<>();
    ConsultationAdapter consultationAdapter;
    RecyclerView rvIllnessesList;
    SwipeRefreshLayout refreshList;
    TextView txtIllnessName;
    TextView txtConsultation;
    String category, doctorCategory;
    String conId;
    int idAuthDoctor;
    Bundle data = new Bundle();
    Calendar calendar = Calendar.getInstance();
    int houres = calendar.get(Calendar.HOUR);
    int minutes = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    // notification
    String token;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_illness_list, container, false);
        consultationAdapter = new ConsultationAdapter(getContext(), items, this);
        rvIllnessesList = view.findViewById(R.id.rvIllnessesList);
        txtConsultation = view.findViewById(R.id.txtConsultation);
        txtIllnessName = view.findViewById(R.id.txtIllnessName);
        refreshList = view.findViewById(R.id.refreshList);

//        database = FirebaseDatabase.getInstance();
//        ref = database.getReference("Notification");
        // Request the device token in a background task


        setHasOptionsMenu(true);
        assert getArguments() != null;
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

        idAuthDoctor = getArguments().getInt("idAuthDoctor");
        Log.e("doctorCategory", "" + idAuthDoctor);
        txtIllnessName.setText(doctorCategory);

        refreshList.setOnRefreshListener(() -> {
            if (refreshList.isRefreshing()) {
                refreshList.setRefreshing(false);
            }
            items.clear();
            getConsultation();
        });
        getConsultation();
        return view;
    }
    @Override
    public void onItemClickList(int position, String id) {
        ConsultingFragment consultingFragment = new ConsultingFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        data.putString("conId", id); // con Document id
        Log.e("TAG", "onItemClickList: " + id);
        consultingFragment.setArguments(data);
        fragmentTransaction.replace(R.id.mainContainer,
                consultingFragment).addToBackStack("").commit();
    }

    //notification
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseMessaging.getInstance().subscribeToTopic(category)
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed";
                    if (!task.isSuccessful()) {
                        msg = "Subscribe failed";
                    }
                    Log.d("TAG", msg);
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
                });

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (idAuthDoctor == 0) {
            inflater.inflate(R.menu.notification_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getConsultation() {
        db.collection("Consultion").whereEqualTo("doctorCategory", doctorCategory).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.isEmpty()) {
                Log.e("Ayat", "onSuccess: LIST EMPTY");
            } else {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    if (documentSnapshot.exists()) {
                        conId = documentSnapshot.getId();
                        Log.e("TAG", "getConsultation: " + conId);
                        String doctorName = documentSnapshot.getString("doctorName");
                        String doctorImage = documentSnapshot.getString("doctorImage");
                        String title = documentSnapshot.getString("title");
                        Consultation e_consultation = new Consultation(conId, doctorName, title, doctorImage);
                        items.add(e_consultation);
                        consultationAdapter = new ConsultationAdapter(getContext(), items, this);
                        rvIllnessesList.setHasFixedSize(true);
                        rvIllnessesList.setAdapter(consultationAdapter);
                        consultationAdapter.notifyDataSetChanged();
                    }
                }
            }
        }).addOnFailureListener(e -> {

        });
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
        Traffic.put("screen_name", "IlnessList");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TrackUsers")
                .add(Traffic)
                .addOnSuccessListener(documentReference -> Log.e("TAG", "Data added successfully to database"))
                .addOnFailureListener(e -> Log.e("TAG", "Failed to add database"));
        super.onPause();
    }


}