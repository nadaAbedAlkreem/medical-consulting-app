package com.example.medicalconsultingapplication.operationConsulting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.medicalconsultingapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UpdateConsultionActivity extends AppCompatActivity {
         ImageView updateImageViewCons  ;
         Button updateInfographCons ;
         VideoView updateVideoViewCons;
         EditText  updateTitleCons  ;
         EditText  updateContentCons ;
         FirebaseFirestore db;
         FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
         StorageReference storageReference;
    Uri imageUriLogo;
    Uri fileLogoURI;
    Uri imageUriInfo;
    Uri fileInfoURI;
    Uri videoUri;
    Uri fileVideoURI;
    public String logoPath;
    public String infoPath;
    public String videoPath;
     Button btnUpdate; //Firebase
    ProgressBar progressBarlogo;
    ProgressBar progressBarVideo;
    boolean logo = false ;
    boolean video = false ;
    boolean info = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_consultion);

        db = FirebaseFirestore.getInstance();
        updateImageViewCons  = findViewById(R.id.imgUpdateLogo);
        updateInfographCons = findViewById(R.id.btnAddInfoUpdate) ;
        updateVideoViewCons = findViewById(R.id.videoViewUpdate) ;
        updateTitleCons = findViewById(R.id.edtTitleUpdate) ;
        updateContentCons = findViewById(R.id.edtContentUpdate) ;
        btnUpdate = findViewById(R.id.btnUpdate) ;
        storageReference = firebaseStorage.getReference();

        progressBarlogo = findViewById(R.id.progressBarLogo);
        progressBarVideo = findViewById(R.id.progressBarVideo);
        progressBarlogo.setVisibility(View.GONE);
        progressBarVideo.setVisibility(View.GONE);

        updateImageViewCons.setOnClickListener(v ->
        {
            // selectLogoImage
            selectLogoImage();

        });


        updateVideoViewCons.setOnClickListener(v -> {
            // select Video
            selectVideo();
         });
        btnUpdate.setOnClickListener(v -> {

                 uploadImgLogo();



        });
        updateInfographCons.setOnClickListener(v -> {
            // selectInfoImage
            selectInfoImage();

            updateInfographCons.setText("تم إضافة صورة المخطط");

        });

        getDataUpdate();


    }

    public void updateDataConsulting(String conTitle
            , String conContent ,
                             Uri imgLogoUri, Uri imgInfoUri, Uri videoUri)
     {
         Intent intent = getIntent();
         String idClickUpdateItemConsulting = intent.getStringExtra("idClickUpdateItemConsulting");
         Log.e("tttttvdjpoj", idClickUpdateItemConsulting);


        Map<String, Object> consultion = new HashMap<>();
        consultion.put("title", conTitle);
        consultion.put("content", conContent);
        consultion.put("conLogo", imgLogoUri);
        consultion.put("conInfo", imgInfoUri);
        consultion.put("conVideo", videoUri);
        db.collection("Consultion").document(idClickUpdateItemConsulting).update(consultion).addOnSuccessListener(documentReference -> {
             Toast.makeText( this, "update successfully", Toast.LENGTH_SHORT).show();

//            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, profileUserFragment).addToBackStack("").commit();;
        }).addOnFailureListener(e -> {
            Log.e("tag", e.getMessage());
            Toast.makeText(this, "Added Failed", Toast.LENGTH_SHORT).show();
        });


    }


    public  void getDataUpdate()
    {
        Intent intent = getIntent();
        String idClickUpdateItemConsulting = intent.getStringExtra("idClickUpdateItemConsulting");
        Log.e("ttttt", idClickUpdateItemConsulting);

        db.collection("Consultion").document(idClickUpdateItemConsulting)
                .get().addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                     if (task.isSuccessful()) {

                         Log.e("ttttt", String.valueOf(task.getResult().getString("content")));
                         updateContentCons.setText(task.getResult().getString("content"));
                         updateTitleCons.setText(task.getResult().getString("title"));
                         Picasso.get().load( task.getResult().getString("conLogo"))
                                 .into( updateImageViewCons  );
                         Uri uri = Uri.parse(task.getResult().getString("conVideo"));
                         updateVideoViewCons.setVideoURI(uri);
                         updateVideoViewCons.start();


                     }else{
                             Log.e("ttttt" , "empty") ;

                         }




                     }

        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ttttttttttttttt" , "FAILD") ;
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUriLogo = data.getData();
            updateImageViewCons.setImageURI(imageUriLogo);
        } else if (requestCode == 111 && data != null && data.getData() != null) {
            imageUriInfo = data.getData();
        } else if (requestCode == 1 && data != null && data.getData() != null) {
            videoUri = data.getData();
            updateVideoViewCons.setVideoURI(videoUri);
        }
    }
    public void selectVideo()
    {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
     }
    public void selectInfoImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 111);
     }
    public void selectLogoImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
     }



    //upload Video
    public void uploadVideo()
    {
        if (videoUri != null) {

             StorageReference videoRef = storageReference.child("Videos");
            StorageReference childRefVideo = videoRef.child(System.currentTimeMillis() + "_Videos");
            UploadTask uploadTask = childRefVideo.putFile(videoUri);
            uploadTask.addOnFailureListener(exception -> Log.e("TAG", exception.getMessage())).addOnSuccessListener(taskSnapshot -> {
//                Log.e("TAG", "SuccessVideo");
                Toast.makeText(UpdateConsultionActivity.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                progressBarVideo.setVisibility(View.GONE);
                childRefVideo.getDownloadUrl().addOnSuccessListener(uri -> {
//                    Log.e("TAG", uri.toString());
                    fileVideoURI = uri;
                    uploadImgInfo();

                    Toast.makeText(this , "video success" , Toast.LENGTH_SHORT).show(); ;

                });
            });

        }
    }
    public void uploadImgInfo()
    {
        StorageReference imgRefInfo = storageReference.child("Infographics");
        ContentResolver resolver = getContentResolver();
        try {
            @SuppressLint("Recycle") InputStream inputStream = resolver.openInputStream(imageUriInfo);
            Bitmap bitmapInfo = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapInfo.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] dataInfo = baos.toByteArray();
            StorageReference childRefInfo = imgRefInfo.child(System.currentTimeMillis() + "_InfoImage");
            UploadTask uploadTask = childRefInfo.putBytes(dataInfo);
            uploadTask.addOnFailureListener(exception -> Log.e("TAG", exception.getMessage())).addOnSuccessListener(taskSnapshot -> {
//                Log.e("TAG", "SuccessInfo");
                Toast.makeText(UpdateConsultionActivity.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                updateInfographCons.setText("تم رفع المخطط");
                childRefInfo.getDownloadUrl().addOnSuccessListener(uri -> {
//                    Log.e("TAG", uri.toString());
                    fileInfoURI = uri;
                    info = true ;
                    String conTitle = updateTitleCons.getText().toString();
                    String conContent = updateContentCons.getText().toString();
                    if( info ) {
                        progressBarlogo.setVisibility(View.GONE);
                        updateDataConsulting(conTitle, conContent, fileLogoURI, fileInfoURI, fileVideoURI);
                    }
 //                    uploadVideo();
//                    uploadImgLogo();
                     Toast.makeText(this , "info success" , Toast.LENGTH_SHORT).show(); ;

                });
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void uploadImgLogo()
    {
        progressBarlogo.setVisibility(View.VISIBLE);
        StorageReference imgRefLogo = storageReference.child("Logos");
        Bitmap bitmapLogo = ((BitmapDrawable) updateImageViewCons.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapLogo.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] dataLogo = baos.toByteArray();
        StorageReference childRefLogo = imgRefLogo.child(System.currentTimeMillis() + "_LogoImage");
        UploadTask uploadTask = childRefLogo.putBytes(dataLogo);
        uploadTask.addOnFailureListener(exception -> Log.e("TAG", exception.getMessage())).addOnSuccessListener(taskSnapshot -> {
//            Log.e("TAG", "Success");
            Toast.makeText(UpdateConsultionActivity.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
            childRefLogo.getDownloadUrl().addOnSuccessListener(uri -> {
//                Log.e("TAG", uri.toString());
                uploadVideo();

                fileLogoURI = uri;
                 Toast.makeText(this , "logo success" , Toast.LENGTH_SHORT).show(); ;

            });
        });
    }
//    public void UpdateConsultion(String conTitle
//            , String conContent, String doctorId, String doctorAuth, String doctorCategory,
//                              String doctorName, String doctorImage, Uri imgLogoUri, Uri imgInfoUri, Uri videoUri)
//    {
//        Map<String, Object> consultion = new HashMap<>();
//        consultion.put("title", conTitle);
//        consultion.put("content", conContent);
//        consultion.put("doctorId", doctorId);
//        consultion.put("doctorAuth", doctorAuth);
//        consultion.put("doctorCategory", doctorCategory);
//        consultion.put("doctorName", doctorName);
//        consultion.put("doctorImage", doctorImage);
//        consultion.put("conLogo", imgLogoUri);
//        consultion.put("conInfo", imgInfoUri);
//        consultion.put("conVideo", videoUri);
//        db.collection("Consultion").add(consultion).addOnSuccessListener(documentReference -> {
////            Log.e("tag", "Added successfully with id:" + documentReference.getId());
////            Toast.makeText(AddConsultionActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
//            edtTitle.setText("");
//            edtContent.setText("");
//        }).addOnFailureListener(e -> {
////            Log.e("tag", e.getMessage());
////            Toast.makeText(this, "Added Failed", Toast.LENGTH_SHORT).show();
//        });
//    }


}