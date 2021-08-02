package com.example.miniworld.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniworld.Models.StoryModel;
import com.example.miniworld.R;
import com.example.miniworld.StoryViewing;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StoryAdapters extends RecyclerView.Adapter<StoryAdapters.viewHolder>{
    ArrayList<StoryModel> storyList;
    Context context;
    private boolean isFragment;

    public StoryAdapters(ArrayList<StoryModel> storyList, Context context, Boolean isFragment   ) {
        this.storyList = storyList;
        this.context = context;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.story_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        StoryModel storyModel = storyList.get(position);
        holder.storyusername.setText(storyModel.getUsername());
        Picasso.get().load(storyModel.getProfilePhoto()).placeholder(R.drawable.ic_users).into(holder.storyProfilePhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryViewing.class  );
                intent.putExtra("username", storyModel.getUsername());
                intent.putExtra("profilePhoto", storyModel.getProfilePhoto());
                intent.putExtra("story", storyModel.getStory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
    ImageView storyProfilePhoto;
    TextView storyusername;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            storyProfilePhoto = itemView.findViewById(R.id.storyProfilePhoto);
            storyusername = itemView.findViewById(R.id.storyusername);
        }
    }

}
