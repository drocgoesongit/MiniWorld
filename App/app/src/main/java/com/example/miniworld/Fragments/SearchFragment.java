package com.example.miniworld.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miniworld.Adapters.UserAdapters;
import com.example.miniworld.Models.Users;
import com.example.miniworld.R;
import com.example.miniworld.databinding.FragmentSearchBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
FragmentSearchBinding binding;
List<Users> userList;
UserAdapters adapter;


    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        binding.userRecyclerView.setHasFixedSize(true);
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = new ArrayList<Users>();

        adapter = new UserAdapters(getContext(),userList,true);
        binding.userRecyclerView.setAdapter(adapter);

        readUsers();

        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        


        return binding.getRoot();
    }

    private void readUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(TextUtils.isEmpty(binding.searchBar.getText().toString())){
                                                    userList.clear();
                                                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                                        Users user = snapshot1.getValue(Users.class);
                                                        user.setUserid(snapshot1.getKey());
                                                        if(!user.getUserid().equals(FirebaseAuth.getInstance().getUid())){
                                                            userList.add(user);
                                                        }


                                                    }
                                                    adapter.notifyDataSetChanged();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        }
        );
    }

    private void searchUser(String s) {
       Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").startAt(s).endAt(s + "\uf8ff");

       query.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               userList.clear();
               for(DataSnapshot snap: snapshot.getChildren()){
                   Users user = snap.getValue(Users.class);
                   userList.add(user);
               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }
}