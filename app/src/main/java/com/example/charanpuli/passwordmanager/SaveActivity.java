package com.example.charanpuli.passwordmanager;



import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class SaveActivity extends AppCompatActivity {
    private String downloadimageurl;
    private Button updateBtn;
    private EditText appName,appPassword;
    private ImageView appImage;
    private TextView changeImage;
    private static final int gallerypick=1;
    private Uri imageuri;
    private String name,password,savecurrentdate,savecurrenttime,productrandomkey,encryptPass;
    private StorageReference PasswordImageRef;
    private DatabaseReference passwordref;
    ProgressDialog Loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);


        PasswordImageRef=FirebaseStorage.getInstance().getReference().child("App Images");


        updateBtn=(Button)findViewById(R.id.update_btn);
        appName=(EditText)findViewById(R.id.app_name);
        appPassword=(EditText)findViewById(R.id.app_password);
        appImage=(ImageView)findViewById(R.id.app_profile_image);
        changeImage=(TextView)findViewById(R.id.app_image);
        passwordref=FirebaseDatabase.getInstance().getReference().child("Passwords");
        Loadingbar=new ProgressDialog(this);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePasswordData();
            }
        });


    }


    private void openGallery() {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,gallerypick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gallerypick && resultCode==RESULT_OK && data!=null)
        {
            imageuri=data.getData();
            appImage.setImageURI(imageuri);
        }
    }
    private void ValidatePasswordData() {
        name=appName.getText().toString();
        password=appPassword.getText().toString();
        if(imageuri==null){
            Toast.makeText(this, "Image is Mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Provide app name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Provide app password..", Toast.LENGTH_SHORT).show();
        }

        else
        {
            AES aes=new AES();
            try {
                encryptPass = aes.encrypt(Paper.book().read("master").toString().getBytes("UTF-16LE"), (password).getBytes("UTF-16LE"));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            StorePasswordInformation(encryptPass);
        }



    }

    private void StorePasswordInformation(String encryptPass) {
        Loadingbar.setTitle("Adding encrypted Password");
        Loadingbar.setMessage("Please wait,while we are adding encrypted password");
        Loadingbar.setCanceledOnTouchOutside(false);
        Loadingbar.show();
        Calendar calender=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calender.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currenttime.format(calender.getTime());
        productrandomkey=savecurrentdate+savecurrenttime;

        final StorageReference filepath;
        ///////////////////////////////////////////////////
        filepath = PasswordImageRef.child(imageuri.getLastPathSegment() + productrandomkey + ".jpg");

        final UploadTask uploadTask=filepath.putFile(imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg=e.toString();
                Toast.makeText(SaveActivity.this, "Error : "+msg, Toast.LENGTH_SHORT).show();
                Loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SaveActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urltask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadimageurl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {   downloadimageurl=task.getResult().toString();
                            Toast.makeText(SaveActivity.this, "Got Image URL successfully...", Toast.LENGTH_SHORT).show();
                            savepasswordInfotoDatabase(encryptPass);
                        }

                    }
                });

            }

        });

    }

    private void savepasswordInfotoDatabase(String encryptPass) {
        HashMap<String,Object> productmap=new HashMap<>();
        productmap.put("pid",productrandomkey);
        productmap.put("date",savecurrentdate);
        productmap.put("time",savecurrenttime);
        productmap.put("image",downloadimageurl);

        productmap.put("password",encryptPass);
        productmap.put("pname",name);
        passwordref.child(productrandomkey).updateChildren(productmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {    Intent intent=new Intent(SaveActivity.this,PasswordActivity.class);
                            startActivity(intent);
                            Loadingbar.dismiss();
                            Toast.makeText(SaveActivity.this, "Password is added successfully...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {    Loadingbar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(SaveActivity.this, "Error : "+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }
}




