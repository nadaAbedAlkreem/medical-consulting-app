package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;

public class IllnessListFragment extends Fragment implements ConsultationAdapter.ItemClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Consultation> items = new ArrayList<>();
    ConsultationAdapter consultationAdapter;
    RecyclerView rvIllnessesList;
    SwipeRefreshLayout refreshList;
    TextView txtIllnessName;
    TextView txtConsultation;
    String category;
    String conId;
    Bundle data = new Bundle();

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
        setHasOptionsMenu(true);
        assert getArguments() != null;
        category = getArguments().getString("doctorCategory");
        Log.e("doctorCategory", category);
        txtIllnessName.setText(category);
        getConsultation();
        refreshList.setOnRefreshListener(() -> {
            if (refreshList.isRefreshing()) {
                refreshList.setRefreshing(false);
            }
            items.clear();
            getConsultation();
        });

        return view;
     }
    @Override
    public void onItemClickList(int position, String id) {
        ConsultingFragment consultingFragment = new ConsultingFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        data.putString("conId", conId); // con Document id
        Log.e("TAG", "onItemClickList: "+conId );
        consultingFragment.setArguments(data);
        fragmentTransaction.replace(R.id.mainContainer,
                consultingFragment).addToBackStack("").commit();
    }

    //notification
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navNotification) {
            Log.e("Ayat", "notification");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notification_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void getConsultation() {
        db.collection("Consultion").whereEqualTo("doctorCategory", category).get().addOnSuccessListener(queryDocumentSnapshots -> {
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

}