package com.example.medicalconsultingapplication.operationConsulting;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medicalconsultingapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class AddConsultionActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    ImageView imglogo;
    EditText edtTitle;
    EditText edtContent;
    VideoView videoView;
    Button btnAddInfo;
    Button btnAdd; //Firebase
    ProgressBar progressBarlogo;
    ProgressBar progressBarVideo;
    Uri imageUriLogo;
    Uri fileLogoURI;
    Uri imageUriInfo;
    Uri fileInfoURI;
    Uri videoUri;
    Uri fileVideoURI;
    public String logoPath;
    public String infoPath;
    public String videoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consultion);
        imglogo = findViewById(R.id.imgUpdateLogo);
        edtTitle = findViewById(R.id.edtTitleUpdate);
        edtContent = findViewById(R.id.edtContentUpdate);
        videoView = findViewById(R.id.videoViewUpdate);
        btnAddInfo = findViewById(R.id.btnAddInfoUpdate);
        btnAdd = findViewById(R.id.btnAdd);
        progressBarlogo = findViewById(R.id.progressBarLogo);
        progressBarVideo = findViewById(R.id.progressBarVideo);
        progressBarlogo.setVisibility(View.GONE);
        progressBarVideo.setVisibility(View.GONE);

        imglogo.setOnClickListener(v -> {
            // selectLogoImage
            selectLogoImage();
        });

        btnAddInfo.setOnClickListener(v -> {
            // selectInfoImage
            selectInfoImage();
            btnAddInfo.setText("تم إضافة صورة المخطط");
        });
        videoView.setOnClickListener(v -> {
            // select Video
            selectVideo();
        });
        btnAdd.setOnClickListener(v -> {
            storageReference = firebaseStorage.getReference();
            uploadImgInfo();
        });

    }

    // select ImgLogo
    public void selectLogoImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    // upload ImgLogo
    public void uploadImgLogo()
    {
        progressBarlogo.setVisibility(View.VISIBLE);
        StorageReference imgRefLogo = storageReference.child("Logos");
        Bitmap bitmapLogo = ((BitmapDrawable) imglogo.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapLogo.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] dataLogo = baos.toByteArray();
        StorageReference childRefLogo = imgRefLogo.child(System.currentTimeMillis() + "_LogoImage");
        UploadTask uploadTask = childRefLogo.putBytes(dataLogo);
        uploadTask.addOnFailureListener(exception -> Log.e("TAG", exception.getMessage())).addOnSuccessListener(taskSnapshot -> {
//            Log.e("TAG", "Success");
            Toast.makeText(AddConsultionActivity.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
            progressBarlogo.setVisibility(View.GONE);
            childRefLogo.getDownloadUrl().addOnSuccessListener(uri -> {
//                Log.e("TAG", uri.toString());
                fileLogoURI = uri;
            });
        });
    }

    // select ImgInfo
    public void selectInfoImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 111);
    }

    // upload ImgInfo
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
                Toast.makeText(AddConsultionActivity.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                btnAddInfo.setText("تم رفع المخطط");
                childRefInfo.getDownloadUrl().addOnSuccessListener(uri -> {
//                    Log.e("TAG", uri.toString());
                    fileInfoURI = uri;
                    uploadVideo();
                    uploadImgLogo();
                });
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // select Video
    public void selectVideo()
    {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    //upload Video
    public void uploadVideo()
    {
        if (videoUri != null) {
            progressBarVideo.setVisibility(View.VISIBLE);
            String conTitle = edtTitle.getText().toString();
            String conContent = edtContent.getText().toString();
            Intent intent = getIntent();
            String doctorId = intent.getStringExtra("doctorId");
            Log.e("test" , doctorId);

            String doctorAuth = intent.getStringExtra("doctorAuth");
            String doctorCategory = intent.getStringExtra("doctorCategory");
            String doctorName = intent.getStringExtra("userName");
            String doctorImage = intent.getStringExtra("userImage");
            StorageReference videoRef = storageReference.child("Videos");
            StorageReference childRefVideo = videoRef.child(System.currentTimeMillis() + "_Videos");
            UploadTask uploadTask = childRefVideo.putFile(videoUri);
            uploadTask.addOnFailureListener(exception -> Log.e("TAG", exception.getMessage())).addOnSuccessListener(taskSnapshot -> {
//                Log.e("TAG", "SuccessVideo");
                Toast.makeText(AddConsultionActivity.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                progressBarVideo.setVisibility(View.GONE);
                childRefVideo.getDownloadUrl().addOnSuccessListener(uri -> {
//                    Log.e("TAG", uri.toString());
                    fileVideoURI = uri;
                    addConsultion(conTitle, conContent, doctorId, doctorAuth, doctorCategory, doctorName, doctorImage, fileLogoURI, fileInfoURI, fileVideoURI);
                });
            });

        }
    }

    //add Consultion
    public void addConsultion(String conTitle
            , String conContent, String doctorId, String doctorAuth, String doctorCategory,
                              String doctorName, String doctorImage, Uri imgLogoUri, Uri imgInfoUri, Uri videoUri)
    {
        Map<String, Object> consultion = new HashMap<>();
        consultion.put("title", conTitle);
        consultion.put("content", conContent);
        consultion.put("doctorId", doctorId);
        consultion.put("doctorAuth", doctorAuth);
         consultion.put("doctorCategory", doctorCategory);
        consultion.put("doctorName", doctorName);
        consultion.put("doctorImage", doctorImage);
        consultion.put("conLogo", imgLogoUri);
        consultion.put("conInfo", imgInfoUri);
        consultion.put("conVideo", videoUri);
        db.collection("Consultion").add(consultion).addOnSuccessListener(documentReference -> {
//            Log.e("tag", "Added successfully with id:" + documentReference.getId());
//            Toast.makeText(AddConsultionActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
            edtTitle.setText("");
            edtContent.setText("");
        }).addOnFailureListener(e -> {
//            Log.e("tag", e.getMessage());
//            Toast.makeText(this, "Added Failed", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUriLogo = data.getData();
            imglogo.setImageURI(imageUriLogo);
        } else if (requestCode == 111 && data != null && data.getData() != null) {
            imageUriInfo = data.getData();
        } else if (requestCode == 1 && data != null && data.getData() != null) {
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
        }
    }
}