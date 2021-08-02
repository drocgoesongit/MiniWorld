package com.example.miniworld.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miniworld.AddStory;
import com.example.miniworld.AddingPost;
import com.example.miniworld.databinding.FragmentAddBinding;


public class AddFragment extends Fragment {
FragmentAddBinding binding;

    public AddFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       binding = FragmentAddBinding.inflate(inflater, container, false);
       binding.addAStory.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent  = new Intent (getActivity(), AddStory.class);
               startActivity(intent);
           }
       });


       binding.addAPost.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), AddingPost.class);
               startActivity(intent);
           }
       });






       return binding.getRoot();
    }
}