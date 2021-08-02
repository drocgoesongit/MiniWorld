package com.example.miniworld.Adapters;

import android.content.Context;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniworld.Models.MessageModels;
import com.example.miniworld.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdpaters extends  RecyclerView.Adapter {
Context context;
ArrayList<MessageModels> list;

int SENDER = 1;
int RECEIVER = 2;

    public ChatAdpaters(Context context, ArrayList<MessageModels> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER;
        }
        else{
            return RECEIVER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER){
            View view = LayoutInflater.from(context).inflate(R.layout.send_bubble,parent,false);
            return new ChatAdpaters.SenderView(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_bubble,parent,false);
            return new ChatAdpaters.ReceiverView(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModels model = list.get(position);
        if(holder.getClass() == SenderView.class){
            ((SenderView)holder).senderMessage.setText(model.getMessage());
        }else{
            ((ReceiverView)holder).receiverMessage.setText(model.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReceiverView extends RecyclerView.ViewHolder{
        private TextView receiverMessage;
        public ReceiverView(@NonNull View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.receivertext   );
        }
    }
    public class SenderView extends RecyclerView.ViewHolder{
        private TextView senderMessage;
        public SenderView(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.sendertext);
        }
    }

}
