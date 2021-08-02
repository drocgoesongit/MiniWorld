package com.example.miniworld.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.miniworld.ActivityForPost;
import com.example.miniworld.Adapters.PostAdapters;
import com.example.miniworld.Adapters.StoryAdapters;
import com.example.miniworld.AddStory;
import com.example.miniworld.MainActivity;
import com.example.miniworld.Models.PostModels;
import com.example.miniworld.Models.StoryModel;
import com.example.miniworld.Models.Users;
import com.example.miniworld.Options;
import com.example.miniworld.ProfileSetting;
import com.example.miniworld.R;
import com.example.miniworld.SignUp;
import com.example.miniworld.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class HomeFragment extends Fragment {
ArrayList<StoryModel> storyList = new ArrayList<>();
ArrayList<PostModels> postList = new ArrayList<>();
ArrayList<String> follow;
FragmentHomeBinding binding;
PostAdapters adapterPost;
StoryAdapters adapters;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Story Part. :D
        adapters = new StoryAdapters(storyList, getActivity(), true);
        binding.storyRecyclerView.setAdapter(adapters);
        binding.storyRecyclerView.setHasFixedSize(true);
        follow = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);

        binding.storyRecyclerView.setLayoutManager(layoutManager);

        binding.addStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddStory.class);
                startActivity(intent);
            }
        });

        // Post Part. :D
        adapterPost = new PostAdapters(postList, getActivity(),true    );
        binding.postRecyclerView.setAdapter(adapterPost);
        binding.postRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManagerPost = new LinearLayoutManager(getContext());
        binding.postRecyclerView.setLayoutManager(layoutManagerPost);

        upload();
        uploadPost();


        adapters.notifyDataSetChanged();

        binding.greetingsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityForPost.class);
                startActivity(intent);
            }
        });
        binding.menuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Options.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    private void uploadPost() {
        DatabaseReference  ref = FirebaseDatabase.getInstance().getReference().child("Posts").child("AllPosts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    PostModels model = snapshot1.getValue(PostModels.class);
                    postList.add(model);
                }
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Some error with the posts.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void upload() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("wait a minute");
        //dialog.show();
        DatabaseReference refernceforStory = FirebaseDatabase.getInstance().getReference("Story"    );

        refernceforStory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storyList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StoryModel modelStory = dataSnapshot.getValue(StoryModel.class);
                    storyList.add(modelStory);
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class  );
                if(user.getProfilePhoto() != null) {
                    binding.greetingUsername.setText(user.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "This also failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}