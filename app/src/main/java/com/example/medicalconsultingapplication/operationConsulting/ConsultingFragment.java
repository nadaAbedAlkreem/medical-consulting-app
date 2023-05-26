package com.example.medicalconsultingapplication.operationConsulting;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.medicalconsultingapplication.R;
import com.example.medicalconsultingapplication.model.Consultation;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class ConsultingFragment extends Fragment {
    TextView txtNameCons;
    TextView txtNameDoc;
    ImageView imageConsu;
    ImageView imageDoc;
    ImageView imageInfo;
    TextView txtContent;
    FirebaseFirestore db;
    String conId;

    PlayerView playerView;
    ExoPlayer player;
    int currentWindow=0;
    boolean playWhenReady=true;
    long playBackPosition=0;
    private FirebaseAnalytics mfirebaseAnalystic;

    Calendar calendar = Calendar.getInstance() ;
    int houres  = calendar.get(Calendar.HOUR) ;
    int minutes  = calendar.get(Calendar.MINUTE) ;
    int second  = calendar.get(Calendar.SECOND) ;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulting, container, false);
        db = FirebaseFirestore.getInstance();
        txtNameCons = view.findViewById(R.id.namecons);
        txtContent = view.findViewById(R.id.textcons);
        txtNameDoc = view.findViewById(R.id.namedoc);
        imageConsu = view.findViewById(R.id.imageconsu);
        imageDoc = view.findViewById(R.id.imagedoc);
        imageInfo = view.findViewById(R.id.imageinfo);
         playerView=view.findViewById(R.id.video_view1);
        mfirebaseAnalystic = FirebaseAnalytics.getInstance(requireActivity());
        screenTrack("ConsultingFragment");
        assert getArguments() != null;
        conId = getArguments().getString("conId");
      //  getconsultation();
 
        assert getArguments() != null;
        conId = getArguments().getString("conId");
        Log.e("nada789" , conId) ;
        getconsultation();

         return view;
    }
    public void getconsultation() {
        db.collection("Consultion").document(conId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String namecons = documentSnapshot.getString("title");
                        String namedoc = documentSnapshot.getString("doctorName");
                        String textcons = documentSnapshot.getString("content");
                        String imageconsu = documentSnapshot.getString("conLogo");
                        String imagedoc = documentSnapshot.getString("doctorImage");
                        String imageinfo = documentSnapshot.getString("conInfo");
                        String video = documentSnapshot.getString("conVideo");
                        player= new SimpleExoPlayer.Builder(requireContext()).build();
                        playerView.setPlayer(player);
                        Uri uri=Uri.parse(video);
                        DataSource.Factory databaseSourse= new DefaultDataSourceFactory(requireContext(),"video");
                        MediaSource mediaSource= new ProgressiveMediaSource.Factory(databaseSourse).createMediaSource(MediaItem.fromUri(uri));
                        player.setPlayWhenReady(playWhenReady);
                        player.seekTo(currentWindow,playBackPosition);
                        player.prepare(mediaSource,false,false);
                        txtNameCons.setText(namecons);
                        txtNameDoc.setText(namedoc);
                        txtContent.setText(textcons);
                        Picasso.get().load(imageconsu).fit().centerInside().into(imageConsu);
                        Picasso.get().load(imageinfo).fit().centerInside().into(imageInfo);
                        Picasso.get().load(imagedoc).fit().centerInside().into(imageDoc);

                    } else {
                        Log.d("drn", "onSuccess: LIST EMPTY");
                    }
                })
                .addOnFailureListener(e -> Log.e("testcons", "get failed with "));

    }

    public void screenTrack(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, name);
        mfirebaseAnalystic.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }
    public void onPause() {
        Calendar calendar = Calendar.getInstance() ;
        int houres2  = calendar.get(Calendar.HOUR) ;
        int minutes2  = calendar.get(Calendar.MINUTE) ;
        int second2  = calendar.get(Calendar.SECOND) ;
        int h = houres2 - houres  ;
        int m = minutes2   - minutes  ;
        int s = second2 - second ;
        HashMap<String , Object > Traffic  = new HashMap<>() ;
        Traffic.put("time" ,   h +":"+m+":" +s ) ;
        Traffic.put("screen_name" , "Consulting" ) ;
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
        releaseVideo();
        super.onPause();
    }
    @Override
    public void onStart() {
        super.onStart();
      //  initvideo();
        getconsultation();
    }
    public void releaseVideo(){
        if (player !=null){
            playWhenReady=player.getPlayWhenReady();
            playBackPosition=player.getContentPosition();
            currentWindow=player.getCurrentWindowIndex();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseVideo();
    }


}