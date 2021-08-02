package com.example.miniworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.net.LinkAddress;
import android.os.Bundle;
import android.widget.Toast;

import com.example.miniworld.Adapters.PostAdapters;
import com.example.miniworld.Models.PostModels;
import com.example.miniworld.databinding.ActivityForPostBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ActivityForPost extends AppCompatActivity {
ActivityForPostBinding binding;
ArrayList<PostModels> postList ;
FirebaseAuth auth;
FirebaseDatabase database;
FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        PostAdapters adapters = new PostAdapters(postList, this, false);
        binding.postRecycler.setAdapter(adapters);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.postRecycler.setLayoutManager(manager);

        upload();
        adapters.notifyDataSetChanged();


    }

    private void upload() {
        DatabaseReference referenceForPostModel = FirebaseDatabase.getInstance().getReference("Post");
        referenceForPostModel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PostModels model = dataSnapshot.getValue(PostModels.class   );
//                    model.setId(dataSnapshot.getKey());
                    postList.add(model);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityForPost.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}