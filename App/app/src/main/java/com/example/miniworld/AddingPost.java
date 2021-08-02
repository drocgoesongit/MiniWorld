package com.example.miniworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.miniworld.Models.Users;
import com.example.miniworld.databinding.ActivityAddingPostBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.HashMap;

public class AddingPost extends AppCompatActivity {
ActivityAddingPostBinding binding;
FirebaseAuth auth;
FirebaseDatabase database;
FirebaseStorage storage;
private Uri postUri;
String postURL;
String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddingPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();

        binding.editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddingPost.this)
                        .crop(5f, 4f )	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        //.maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(13);
            }
        });

        binding.addPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = (int) (Math.random() * 10000);
                key = String.valueOf(value);
                uploadPost();
                Intent intent = new Intent(AddingPost.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 13){
            postUri = data.getData();
            binding.postImage.setImageURI(postUri);
        } else{
            Toast.makeText(this, "Try adding image again.", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadPost() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading your post");
        pd.show();

        if(postUri != null){

            StorageReference reference = FirebaseStorage.getInstance().getReference("Posts").child(FirebaseAuth.getInstance().getUid()).child(String.valueOf(System.currentTimeMillis()));

            StorageTask uploadTask = reference.putFile(postUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }else{
                        return reference.getDownloadUrl();
                    }
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri uri = task.getResult();
                    postURL = uri.toString();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
                    reference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot){
                            Users user  = snapshot.getValue(Users.class );
                            String username = user.getUsername();
                            String profilePhoto = user.getProfilePhoto();
                            String caption = binding.captionText.getText().toString();
                            String location = binding.locationText.getText().toString();


                            DatabaseReference ref = database.getInstance().getReference("Posts").child(FirebaseAuth.getInstance().getUid());
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Posts").child("AllPosts");
                            HashMap<String , Object> postMap = new HashMap<>();
                            postMap.put("username",username);
                            postMap.put("profilePhoto", profilePhoto);
                            postMap.put("post",postURL);
                            postMap.put("caption", caption);
                            postMap.put("location",location);
                           // postMap.put("likes",likes);

                            ref.push().updateChildren(postMap);
                            ref2.push().updateChildren(postMap);


                            pd.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            pd.dismiss();
                            Toast.makeText(AddingPost.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(AddingPost.this, "Post not uploaded.", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(this, "Image not inserted successfully.", Toast.LENGTH_SHORT).show();
        }

    }


}