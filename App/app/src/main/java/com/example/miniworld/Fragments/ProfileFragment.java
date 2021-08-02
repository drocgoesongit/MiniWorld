package com.example.miniworld.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.miniworld.Adapters.PostAdapters;
import com.example.miniworld.Adapters.ProfilePostAdapters;
import com.example.miniworld.MainActivity;
import com.example.miniworld.Models.PostModels;
import com.example.miniworld.Models.ProfileModelPost;
import com.example.miniworld.Models.Users;
import com.example.miniworld.R;
import com.example.miniworld.UsersPosts;
import com.example.miniworld.databinding.FragmentProfileBinding;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FragmentProfileBinding binding;
    List<PostModels> list;
    ProfilePostAdapters adapters;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);


        list = new ArrayList<PostModels>();
        adapters = new ProfilePostAdapters(list,getContext(),true);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.postRecyclerView.setHasFixedSize(true);
        binding.postRecyclerView.setAdapter(adapters);
        binding.postRecyclerView.setLayoutManager(manager);



        uploadPost();
        getData();


        String postNumber = String.valueOf(list.size());
        binding.postsNumber.setText(postNumber);
        Toast.makeText(getContext(), postNumber, Toast.LENGTH_SHORT).show();
        return binding.getRoot();

    }

    private void getData() {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users user = snapshot.getValue(Users.class  );
                        Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.ic_users).into(binding.profilePhoto);
                        binding.bio.setText(user.getBio());
                        binding.username.setText(user.getUsername());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void uploadPost() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Posts").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    PostModels model = snapshot1.getValue(PostModels.class);
                    list.add(model);
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}