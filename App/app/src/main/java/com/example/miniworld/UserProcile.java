package com.example.miniworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.miniworld.Models.Users;
import com.example.miniworld.databinding.ActivityUserProcileBinding;
import com.example.miniworld.databinding.ActivityUsersPostsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserProcile extends AppCompatActivity {
ActivityUserProcileBinding binding;
ArrayList<String> list;
ArrayList<String> followingList;
ArrayList<String> followerList;
String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProcileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        String username = getIntent().getStringExtra("username");
        String profilePhoto = getIntent().getStringExtra("profilePhoto");
        id = getIntent().getStringExtra("id");
        String bio = getIntent().getStringExtra("bio");

        list = new ArrayList<>() ;
        followingList = new ArrayList<>();
        getCount();
        check();

        binding.followersNumber.setText(String.valueOf(list.size()));
        binding.followingNumber.setText(String.valueOf(followingList.size()));

        binding.profileUsername.setText(username);
        Picasso.get().load(profilePhoto).placeholder(R.drawable.blur).into(binding.profileUserPhoto);
        binding.bioText.setText(bio);
        ProgressDialog dialog = new ProgressDialog(UserProcile.this);
        dialog.setMessage("Wait a minute");
        binding.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                if(binding.followButton.getText().equals("follow")){

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Following").child(FirebaseAuth.getInstance().getUid()).child(id);
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("name",username);
                    hashMap.put("profilePhoto",profilePhoto);
                    reference.updateChildren(hashMap);


                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
                    reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users user = snapshot.getValue(Users.class);
                            String userselfname = user.getUsername();
                            String userProfilePhoto = user.getProfilePhoto();
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Followers").child(id).child(FirebaseAuth.getInstance().getUid());
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("name",userselfname);
                            map.put("profilePhoto",userProfilePhoto);
                            reference1.updateChildren(map);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                    binding.followButton.setText("unfollow");

                    dialog.dismiss();

                }else if(binding.followButton.getText().equals("unfollow")){
                    dialog.show();


                }
            }
        });

        binding.messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProcile.this, ChatDetailActivity.class);
                intent.putExtra("name", username);
                intent.putExtra("id",id);
                intent.putExtra("profilePhoto",profilePhoto);
                startActivity(intent);
            }
        });

    }

    private void check() {
    }

    private void getCount() {
//        for followers count
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Followers").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Following").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followingList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    followingList.add(snapshot1.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}