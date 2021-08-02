package com.example.miniworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.miniworld.Models.Users;
import com.example.miniworld.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
ActivitySignUpBinding binding;
FirebaseAuth auth;
FirebaseDatabase database;
ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Signing in");
        progressDialog.setMessage("We're creating your account");


        binding.txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });


        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.passwordTxt.getText().toString().isEmpty()){
                    binding.passwordTxt.setError("Enter password");
                    return;
                }
                if(binding.emailTxt.getText().toString().isEmpty()){
                    binding.emailTxt.setError("Enter email");
                    return;
                }
               progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.emailTxt.getText().toString(),
                        binding.passwordTxt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.hide();
                            Users user = new Users(binding.usernameTxt.getText().toString(),
                                    binding.emailTxt.getText().toString(), binding.passwordTxt.getText().toString());
                            String uid = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(uid).setValue(user);
                            Toast.makeText(SignUp.this, "Account created Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (SignUp.this, ProfileSetting.class);
                            startActivity(intent);
                        }
                        else{
                            progressDialog.hide();
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(SignUp.this, ProfileSetting.class);
            startActivity(intent);
        }


    }
}