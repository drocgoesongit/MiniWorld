package com.example.miniworld.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniworld.Models.Users;
import com.example.miniworld.R;
import com.example.miniworld.UserProcile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapters extends RecyclerView.Adapter<UserAdapters.ViewHolder>{
    private Context context;
    private List<Users> usersList;
    private Boolean isFragment;

    private FirebaseUser firebaseUser;

    public UserAdapters(Context context, List<Users> usersList, Boolean isFragment) {
        this.context = context;
        this.usersList = usersList;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_search, parent ,false);
        return new UserAdapters.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Users user = usersList.get(position);

        holder.username_search.setText(user.getUsername());
        holder.fullname_search.setText(user.getFullname());
        Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.ic_users).into(holder.user_profile_photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserProcile.class);
                intent.putExtra("username",user.getUsername());
                intent.putExtra("profilePhoto",user.getProfilePhoto());
                intent.putExtra("bio",user.getBio());
                intent.putExtra("id",user.getUserid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView user_profile_photo;
        private TextView username_search;
        private TextView fullname_search;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_profile_photo = itemView.findViewById(R.id.user_profile_photo);
            username_search = itemView.findViewById(R.id.username_search);
            fullname_search = itemView.findViewById(R.id.fullname_search);
        }
    }
}
