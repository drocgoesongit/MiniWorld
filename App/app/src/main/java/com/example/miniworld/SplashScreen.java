package com.example.miniworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.miniworld.databinding.ActivityMainBinding;
import com.example.miniworld.databinding.ActivitySplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;


public class SplashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser()!= null){
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent inte = new Intent(SplashScreen.this, SignUp.class);
                    startActivity(inte);
                }

            }
        },4000);


    }
}