package com.example.miniworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miniworld.Models.Users;
import com.example.miniworld.databinding.ActivityAddStoryBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class AddStory extends AppCompatActivity {

ActivityAddStoryBinding binding;
FirebaseAuth auth;
FirebaseStorage storage;
FirebaseDatabase database;
private Uri storyImageUri;
private String storyImageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();


        binding.editStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddStory.this)
                        .crop(9f, 16f )	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        //.maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(11);
                
            }
        });


        binding.addStoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
                Intent intent = new Intent (AddStory.this , MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void upload() {
        ProgressDialog pd = new ProgressDialog(AddStory.this);
        pd.setMessage("Uploading story");
        pd.show();

        if(storyImageUri != null) {
            StorageReference filePath = storage.getReference("Story Pictures").child(FirebaseAuth.getInstance().getUid());
            StorageTask uploadTask = filePath.putFile(storyImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }else{
                        return filePath.getDownloadUrl();
                    }
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    Uri uri = task.getResult();
                    storyImageURL = uri.toString();
                    DatabaseReference refe = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
                    refe.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users user = snapshot.getValue(Users.class);
                            String username = user.getUsername();
                            String profilePhotoOfUser = user.getProfilePhoto();

                            DatabaseReference ref = database.getReference("Story").child(FirebaseAuth.getInstance().getUid());
                            HashMap<String,Object> hash = new HashMap<>();
                            hash.put("Story",storyImageURL);
                            hash.put("username",username);
                            hash.put("profilePhoto",profilePhotoOfUser);

                            ref.updateChildren(hash);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddStory.this, "Story not uploaded.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11){
            storyImageUri = data.getData();
            binding.storyImage.setImageURI(storyImageUri);

        }else{
            Toast.makeText(this, "Photo not inserted try again.", Toast.LENGTH_SHORT).show();
        }
    }
}