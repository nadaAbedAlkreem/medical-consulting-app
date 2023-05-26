package com.example.medicalconsultingapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.adapter.ConsultationAdapter;
import com.example.medicalconsultingapplication.adapter.SearchAdapter;
import com.example.medicalconsultingapplication.model.Consultation;
import com.example.medicalconsultingapplication.operationConsulting.ConsultingFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class IllnessListFragment extends Fragment implements ConsultationAdapter.ItemClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Consultation> items = new ArrayList<>();
    ConsultationAdapter consultationAdapter;
    SearchAdapter searchAdapter;
    RecyclerView rvIllnessesList;
    RecyclerView searchRecycler;
    SwipeRefreshLayout refreshList;
    ArrayList<Consultation> itemsearch = new ArrayList<>();
    TextView txtIllnessName;
    TextView txtConsultation;
    String category;
    String conId;
    EditText search;
    ImageView searchIcon;
    FragmentTransaction fragmentTransaction;
    Bundle data = new Bundle();
    Calendar calendar = Calendar.getInstance();
    int houres = calendar.get(Calendar.HOUR);
    int minutes = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);
    private FirebaseAnalytics mfirebaseAnalystic;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_illness_list, container, false);
        consultationAdapter = new ConsultationAdapter(getContext(), items, this);
        rvIllnessesList = view.findViewById(R.id.rvIllnessesList);
        txtConsultation = view.findViewById(R.id.txtConsultation);
        searchRecycler=view.findViewById(R.id.searchRecycler);
        txtIllnessName = view.findViewById(R.id.txtIllnessName);
        refreshList = view.findViewById(R.id.refreshList);
        search = view.findViewById(R.id.search);
        searchIcon=view.findViewById(R.id.searchIcon);
        mfirebaseAnalystic = FirebaseAnalytics.getInstance(requireActivity());
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
            screenTrack("IllnessListFragment");
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, searchFragment).addToBackStack("").commit();

            }
        });

        return view;
     }
    @Override
    public void onItemClickList(int position, String id) {
        ConsultingFragment consultingFragment = new ConsultingFragment();
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        data.putString("conId", id); // con Document id
        Log.e("TAG", "onItemClickList: "+conId );
        consultingFragment.setArguments(data);
        fragmentTransaction.replace(R.id.mainContainer,
                consultingFragment).addToBackStack("").commit();
        btnEvent("id","IllnessListFragment","tarnsfer consultingFragment");
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
    public void screenTrack(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, name);
        mfirebaseAnalystic.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
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
    public void btnEvent(String id,String name,String contentType){
        Bundle bundle=new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,id);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,contentType);
        mfirebaseAnalystic.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);
    }


}