package com.example.medicalconsultingapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicalconsultingapplication.adapter.MessageAdapter;
import com.example.medicalconsultingapplication.model.Messages;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivty extends AppCompatActivity {
    private String messageRecieverId,getMessageRecievername,messagereceiverimage,messageSenderId   , send;
    private TextView username,userlastseen;
    private ImageView userprofile;
    private Toolbar chattoolbar;
    private ImageButton sendMessageButton ;
    private EditText messagesentinput;
    private FirebaseAuth mauth;
    private DatabaseReference RootRef;
    private final List<Messages> messagesList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView usermessagerecyclerview;


    private String savecurrentTime,savecurrentDate;
    private String checker="",myUrl="";
    private StorageTask uploadTask;
    private Uri fileuri;
    private ProgressDialog loadingBar;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activty);
        loadingBar = new ProgressDialog(this);
        mauth = FirebaseAuth.getInstance();
        messageSenderId = mauth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        messageRecieverId = getIntent().getExtras().get("idReciver").toString();
         send = getIntent().getExtras().get("idSender").toString();
          getMessageRecievername = getIntent().getExtras().get("imageFriends").toString();
          messagereceiverimage = getIntent().getExtras().get("nameFriends").toString();
        Log.e("nadaIDfmlmc", messageRecieverId);
        Log.e("imageFriends", messagereceiverimage);
        Log.e("nameFriends", getMessageRecievername);






        username=findViewById(R.id.custom_profile_name);
        userlastseen=findViewById(R.id.custom_user_last_seen);
        userprofile=findViewById(R.id.custom_profile_image);
        sendMessageButton=findViewById(R.id.send_message_btn);
      //  sendFileButton=findViewById(R.id.send_files_btn);

        messagesentinput=findViewById(R.id.input_messages);

        messageAdapter=new MessageAdapter(messagesList);
        usermessagerecyclerview=findViewById(R.id.private_message_list_of_users);
        linearLayoutManager=new LinearLayoutManager(this);
        usermessagerecyclerview.setLayoutManager(linearLayoutManager);
        usermessagerecyclerview.setAdapter(messageAdapter);

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("dd/MM/yyyy");
        savecurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
        savecurrentTime=currentTime.format(calendar.getTime());


//        Displaylastseen();
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });

        RootRef.child("Messages").child(messageSenderId).child(messageRecieverId).addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 Messages messages=dataSnapshot.getValue(Messages.class);
                messagesList.add(messages);
                messageAdapter.notifyDataSetChanged();
                usermessagerecyclerview.smoothScrollToPosition(usermessagerecyclerview.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==555 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            loadingBar.setTitle("Sending File");
            loadingBar.setMessage("please wait, we are sending that file...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            fileuri=data.getData();
            if(!checker.equals("image"))
            {
                StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Document Files");

                final String messageSenderRef="Messages/"+messageSenderId+"/"+messageRecieverId;
                final String messageReceiverRef="Messages/"+messageRecieverId+"/"+messageSenderId;

                DatabaseReference Usermessagekeyref=RootRef.child("Messages").child(messageSenderId).child(messageRecieverId).push();
                final String messagePushID=Usermessagekeyref.getKey();

                final StorageReference filepath=storageReference.child(messagePushID+"."+checker);

                filepath.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();

                                Map messageDocsBody = new HashMap();
                                messageDocsBody.put("message",downloadUrl);
                                messageDocsBody.put("name",fileuri.getLastPathSegment());
                                messageDocsBody.put("type",checker);
                                messageDocsBody.put("from",messageSenderId);
                                messageDocsBody.put("to", messageRecieverId);
                                messageDocsBody.put("messageID", messagePushID);
                                messageDocsBody.put("time", savecurrentTime);
                                messageDocsBody.put("date", savecurrentDate);


                                Map messageBodyDDetail = new HashMap();
                                messageBodyDDetail.put(messageSenderRef + "/" + messagePushID, messageDocsBody);
                                messageBodyDDetail.put(messageReceiverRef + "/" + messagePushID, messageDocsBody);

                                RootRef.updateChildren(messageBodyDDetail);
                                loadingBar.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingBar.dismiss();
                                Toast.makeText(ChatActivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double p=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        loadingBar.setMessage((int) p+" % Uploading...");
                    }
                });
            }
            else if(checker.equals("image"))
            {
                StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Image Files");

                final String messageSenderRef="Messages/"+messageSenderId+"/"+messageRecieverId;
                final String messageReceiverRef="Messages/"+messageRecieverId+"/"+messageSenderId;

                DatabaseReference Usermessagekeyref=RootRef.child("Messages").child(messageSenderId).child(messageRecieverId).push();
                final String messagePushID=Usermessagekeyref.getKey();

                final StorageReference filepath=storageReference.child(messagePushID+"."+"jpg");
                uploadTask =filepath.putFile(fileuri);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            Uri downloadUrl=task.getResult();
                            myUrl=downloadUrl.toString();

                            Map messageTextBody=new HashMap();
                            messageTextBody.put("message",myUrl);
                            messageTextBody.put("name",fileuri.getLastPathSegment());
                            messageTextBody.put("type",checker);
                            messageTextBody.put("from",messageSenderId);
                            messageTextBody.put("to",messageRecieverId);
                            messageTextBody.put("messageID",messagePushID);
                            messageTextBody.put("time",savecurrentTime);
                            messageTextBody.put("date",savecurrentDate);

                            Map messageBodyDetails =new HashMap();
                            messageBodyDetails.put(messageSenderRef+"/"+messagePushID,messageTextBody);
                            messageBodyDetails.put(messageReceiverRef+"/"+messagePushID,messageTextBody);

                            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful())
                                    {
                                        loadingBar.dismiss();
                                        //Toast.makeText(ChatActivity.this,"Message sent Successfully...",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(ChatActivty.this,"Error:",Toast.LENGTH_SHORT).show();
                                    }
                                    messagesentinput.setText("");
                                }
                            });
                        }
                    }
                });

            }
            else
            {
                loadingBar.dismiss();
                Toast.makeText(this,"please select file",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void SendMessage() {
        String messagetext=messagesentinput.getText().toString();
        if(TextUtils.isEmpty(messagetext))
        {
            Toast.makeText(this,"Please enter message first..",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String messageSenderRef="Messages/"+messageSenderId+"/"+messageRecieverId;
            String messageReceiverRef="Messages/"+messageRecieverId+"/"+messageSenderId;
            Log.e("nada45" , messageSenderRef) ;
            Log.e("nada45" , messageReceiverRef) ;
            DatabaseReference Usermessagekeyref=RootRef.child("Messages").child(messageSenderId).child(messageRecieverId).push();
            String messagePushID=Usermessagekeyref.getKey();
            Map messageTextBody=new HashMap();
            messageTextBody.put("message",messagetext);
            messageTextBody.put("type","text");
            messageTextBody.put("from",messageSenderId);
            messageTextBody.put("to",messageRecieverId);
            messageTextBody.put("messageID",messagePushID);
            messageTextBody.put("time",savecurrentTime);
            messageTextBody.put("date",savecurrentDate);

            Map messageBodyDetails =new HashMap();
            Log.e("pus" , messagePushID) ;
            messageBodyDetails.put(messageSenderRef+"/"+messagePushID,messageTextBody);
            messageBodyDetails.put(messageReceiverRef+"/"+messagePushID,messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful())
                    {
                      Toast.makeText(ChatActivty.this,"Message sent Successfully...",Toast.LENGTH_SHORT).show();
                      Log.e("succeee" , "dwl") ;
                    }
                    else
                    {
                        Toast.makeText(ChatActivty.this,"Error:",Toast.LENGTH_SHORT).show();
                        Log.e("succeee" , "eee") ;

                    }
                    messagesentinput.setText("");
                }
            });
        }
    }
}
