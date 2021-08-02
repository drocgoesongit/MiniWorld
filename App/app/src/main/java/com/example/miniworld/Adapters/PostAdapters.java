package com.example.miniworld.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniworld.Models.PostModels;
import com.example.miniworld.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapters extends RecyclerView.Adapter<PostAdapters.viewHolder> {
    ArrayList<PostModels> postList ;
    Context context;
    private boolean isFragment;

    public PostAdapters(ArrayList<PostModels> postList, Context context, boolean isFragment) {
        this.postList = postList;
        this.context = context;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_layout,parent,false);
        return new PostAdapters.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PostModels model = postList.get(position);
        holder.username.setText(model.getUsername());
        holder.caption.setText(model.getCaption());
        Picasso.get().load(model.getProfilePhoto()).placeholder(R.drawable.ic_users).into(holder.userProfilePhoto);
        Picasso.get().load(model.getPost()).placeholder(R.drawable.ic_users).into(holder.post );

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private CircleImageView userProfilePhoto;
        private ImageView post;
        private TextView caption;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.postUsername);
            post = itemView.findViewById(R.id.postuserHomeScreen);
            userProfilePhoto = itemView.findViewById(R.id.postProfilePhoto);
            caption = itemView.findViewById(R.id.captionPost);

        }
    }
}
