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

import java.util.List;

public class ProfilePostAdapters extends RecyclerView.Adapter<ProfilePostAdapters.viewHolder>{
    List<PostModels> postList ;
    Context context ;
    private boolean isFragment;

    public ProfilePostAdapters(List<PostModels> postList, Context context, boolean isFragment) {
        this.postList = postList;
        this.context = context;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_post_layout,parent,false);
        return new ProfilePostAdapters.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PostModels model = postList.get(position);
        holder.caption.setText(model.getCaption());
        Picasso.get().load(model.getPost()).placeholder(R.drawable.ic_users).into(holder.postuser);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView  postuser;
        TextView caption;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            postuser = itemView.findViewById(R.id.postuser);
            caption = itemView.findViewById(R.id.caption);
        }
    }
}
