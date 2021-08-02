package com.example.miniworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.miniworld.Adapters.PostAdapters;
import com.example.miniworld.Adapters.ProfilePostAdapters;
import com.example.miniworld.Models.PostModels;
import com.example.miniworld.databinding.ActivityUsersPostsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersPosts extends AppCompatActivity {
ActivityUsersPostsBinding binding;
List<PostModels> postList ;
ProfilePostAdapters adapters;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        postList = new ArrayList<PostModels>();
        binding.usersPostsRecyclerView.setHasFixedSize(true);

        adapters = new ProfilePostAdapters(postList,this,false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.usersPostsRecyclerView.setLayoutManager(manager);
        binding.usersPostsRecyclerView.setAdapter(adapters);

        uploadPost();

    }

    private void uploadPost() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Posts").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    PostModels model = snapshot1.getValue(PostModels.class);
                    postList.add(model);
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}