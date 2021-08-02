package com.example.miniworld;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miniworld.Models.Users;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.example.miniworld.databinding.ActivityProfileSettingBinding;

import java.net.URI;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;

public class ProfileSetting extends AppCompatActivity {
    private Uri imageUri;
    private  String Url;
    ActivityProfileSettingBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Updating profile");
        dialog.setMessage("Setting your profile up");


        getSupportActionBar().hide();


      // database.getReference("Users").children

        binding.profileDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if(binding.profileName.getText().toString().isEmpty()){
                    binding.profileName.setError("Enter your name");
                    return;
                }if(binding.profileBio.getText().toString().isEmpty()){
                    binding.profileBio.setError("Enter your name");
                    return;
                }
                if(imageUri == null){
                    Toast.makeText(ProfileSetting.this, "Set a profile photo.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = binding.profileName.getText().toString();
                String bio = binding.profileBio.getText().toString();

                // run the program to upload the picture to firebase storage. :D
                upload();

                // uploading the bio and name in to the firebase data base. :D
                HashMap<String, Object> obj = new HashMap<>();
                obj.put("fullname", name);
                obj.put("bio", bio);
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                                Toast.makeText(ProfileSetting.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Intent intent = new Intent(ProfileSetting.this,MainActivity.class);
                intent.putExtra("profilephoto",imageUri);
                startActivity(intent);

            }
        });



        binding.editPhotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileSetting.this)
                        .crop(1f, 1f )	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        //.maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);
            }
        });


    }



    private void upload() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if(imageUri != null){
            StorageReference filepth  = FirebaseStorage.getInstance().getReference("ProfilePhotos").child(FirebaseAuth.getInstance().getUid());

            StorageTask uploadTask = filepth.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful() ){
                   throw task.getException();
                   }
                   return filepth.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    Url = downloadUri.toString();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
                    HashMap<String ,Object> hm = new HashMap<>();
                    hm.put("profilePhoto", Url);
                    hm.put("profilePhoto", Url);
                    ref.updateChildren(hm);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(ProfileSetting.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null)
        {
            imageUri = data.getData();
            binding.profilePhoto.setImageURI(imageUri);

            final  String urli = imageUri.toString();
            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Uri").setValue(urli);

        }


    }
}