package com.example.miniworld.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniworld.ChatDetailActivity;
import com.example.miniworld.Models.Users;
import com.example.miniworld.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapters2 extends RecyclerView.Adapter<UserAdapters2.ViewHolder>{
    private Context context;
    private ArrayList<Users> list;
    private Boolean isFragment;

    private FirebaseUser firebaseUser;

    public UserAdapters2(Context context, ArrayList<Users> list, Boolean isFragment) {
        this.context = context;
        this.list = list;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layout2, parent ,false);
        return new UserAdapters2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Users user = list.get(position);

        holder.username_search.setText(user.getUsername());
        Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.ic_users).into(holder.user_profile_photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra("id", user.getUserid());
                intent.putExtra("profilePhoto", user.getProfilePhoto());
                intent.putExtra("name", user.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView user_profile_photo;
        private TextView username_search;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_profile_photo = itemView.findViewById(R.id.profile);
            username_search = itemView.findViewById(R.id.name);
        }
    }
}
