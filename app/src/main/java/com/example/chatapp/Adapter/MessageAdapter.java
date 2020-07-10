package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.MessageActivity;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


    public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
        public static final int MSG_LEFT=0;
        public static final int MSG_RIGHT=1;

        private Context context;
        private String imageurl;
        private List<Chat> chatList;

        FirebaseUser firebaseUser;

        public MessageAdapter(Context context, List<Chat> chatList, String imageurl){
            this.context = context;
            this.chatList = chatList;
            this.imageurl = imageurl;
        }

        @NonNull
        @Override
        public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == MSG_RIGHT){
                View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
                return new MessageAdapter.ViewHolder(view);
            } else {
                View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
                return new MessageAdapter.ViewHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
                Chat chat = chatList.get(position);
                holder.show_message.setText(chat.getMessage());
                if (imageurl.equals("default")){
                    holder.profile_img.setImageResource(R.mipmap.ic_launcher_round);
                }else {
                    Glide.with(context).load(imageurl).into(holder.profile_img);
                }
        }

        @Override
        public int getItemCount() {
            return chatList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView show_message;
            public ImageView profile_img;

            public ViewHolder(View itemView){
                super(itemView);

                show_message = itemView.findViewById(R.id.show_msg);
                profile_img = itemView.findViewById(R.id.profile_img);
            }
        }

        @Override
        public int getItemViewType(int position) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (chatList.get(position).getSender().equals(firebaseUser.getUid())){
                return MSG_RIGHT;
            } else {
                return MSG_LEFT;
            }
        }
    }


