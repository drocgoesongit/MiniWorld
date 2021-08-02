package com.example.miniworld.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.miniworld.Adapters.UserAdapters;
import com.example.miniworld.Adapters.UserAdapters2;
import com.example.miniworld.Models.Users;
import com.example.miniworld.R;
import com.example.miniworld.databinding.FragmentMessageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MessageFragment extends Fragment {
FragmentMessageBinding binding;
UserAdapters2 adapters;
ArrayList<Users> list;


    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        list = new ArrayList<Users>();
        binding = FragmentMessageBinding.inflate(inflater,container,false);
        adapters = new UserAdapters2(getContext(), list,true);
        binding.messageRecyclerView.setHasFixedSize(true);
        binding.messageRecyclerView.setAdapter(adapters);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.messageRecyclerView.setLayoutManager(manager);

        getData();




        return binding.getRoot();
    }

    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list != null){
                    list.clear();
                }
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Users user = snapshot1.getValue(Users.class );
                    user.setUserid(snapshot1.getKey());
                    if(!user.getUserid().equals(FirebaseAuth.getInstance().getUid()) ){
                        list.add(user);
                    }
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}