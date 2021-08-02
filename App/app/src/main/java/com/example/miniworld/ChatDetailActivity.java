package com.example.miniworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Toast;

import com.example.miniworld.Adapters.ChatAdpaters;
import com.example.miniworld.Models.MessageModels;
import com.example.miniworld.databinding.ActivityChatDetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatDetailActivity extends AppCompatActivity {
ActivityChatDetailBinding binding;
String senderRoom;
String receiverRoom;
String uid;
ArrayList<MessageModels> list ;
ChatAdpaters adapters;
//RecyclerView chatRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uid  = FirebaseAuth.getInstance().getUid();
        String username = getIntent().getStringExtra("name");
        String userid = getIntent().getStringExtra("id");
        String profilePhoto = getIntent().getStringExtra("profilePhoto");

        senderRoom = userid +  uid;
        receiverRoom = uid + userid;

        Picasso.get().load(profilePhoto).placeholder(R.drawable.ic_users).into(binding.profileChat);
        binding.nameChat.setText(username);
        //chatRecyclerView = findViewById(R.id.chatRecyclerView);

        list = new ArrayList<MessageModels>();
        adapters = new ChatAdpaters(ChatDetailActivity.this,list);
        LinearLayoutManager manager = new LinearLayoutManager(ChatDetailActivity.this);
        binding.chatRecyclerView.setAdapter(adapters);
        binding.chatRecyclerView.setLayoutManager(manager);
        binding.chatRecyclerView.setHasFixedSize(true);

        getSupportActionBar().hide();

        getData();
        adapters.notifyDataSetChanged();

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.messageText.getText().toString().isEmpty()){
                    String message = binding.messageText.getText().toString()  ;
                    uploadMessage(message);
                    binding.chatRecyclerView.smoothScrollToPosition(list.size());
                }
                else{
                    Toast.makeText(ChatDetailActivity.this, "Message can't be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        


    }

    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats").child(senderRoom);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    MessageModels model = snapshot1.getValue(MessageModels.class);
                    list.add(model);
                    binding.chatRecyclerView.smoothScrollToPosition(list.size());
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void uploadMessage(String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats").child(senderRoom);
        Long time = new Date().getTime();
        MessageModels model = new MessageModels(uid,message,time);
        reference.push().setValue(model);
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Chats").child(receiverRoom);
        reference1.push().setValue(model);
        binding.messageText.setText("");


    }
}