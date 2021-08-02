package com.example.miniworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.miniworld.databinding.ActivityStoryViewingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class StoryViewing extends AppCompatActivity {
ActivityStoryViewingBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoryViewingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        String profilePhoto = getIntent().getStringExtra("profilePhoto");
        String username = getIntent().getStringExtra("username");
        String story = getIntent().getStringExtra("story");

        Picasso.get().load(profilePhoto).placeholder(R.drawable.ic_worldwide__1_).into(binding.profileImage);
        Picasso.get().load(story).placeholder(R.drawable.ic_worldwide__1_).into(binding.storyImage);
        binding.usernameText.setText(username);

    }
}