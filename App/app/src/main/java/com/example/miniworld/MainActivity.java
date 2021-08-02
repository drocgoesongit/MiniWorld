package com.example.miniworld;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.miniworld.Fragments.AddFragment;
import com.example.miniworld.Fragments.HomeFragment;
import com.example.miniworld.Fragments.MessageFragment;
import com.example.miniworld.Fragments.ProfileFragment;
import com.example.miniworld.Fragments.SearchFragment;
import com.example.miniworld.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    Uri profilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();

        profilePhoto = getIntent().getData();


        binding.bottomActivity.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


            switch (i){

                case 0:
                    transaction.replace(R.id.container, new HomeFragment());
                    break;

                case 1:
                    transaction.replace(R.id.container, new SearchFragment());
                    break;

                case 2:
                    transaction.replace(R.id.container, new AddFragment());
                    break;

                case 3:
                    transaction.replace(R.id.container, new MessageFragment());
                    break;


                case 4:
                    transaction.replace(R.id.container, new ProfileFragment());
                    break;

                default:
                    transaction.replace(R.id.container, new HomeFragment());
                    break;
                }
                transaction.commit();
            }
        });



    }
}