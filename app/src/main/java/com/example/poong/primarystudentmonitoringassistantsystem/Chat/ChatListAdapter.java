package com.example.poong.primarystudentmonitoringassistantsystem.Chat;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{

    private List<ChatRoom> chatRoomList = new ArrayList<>();

    public ChatListAdapter(List<ChatRoom> items) {
        chatRoomList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mItem = chatRoomList.get(position);
        holder.chatName.setText(chatRoomList.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatMessageActivity.class);
                intent.putExtra("NAME", chatRoomList.get(position).getRoomId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatRoomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView chatName;
        public ChatRoom mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            chatName = view.findViewById(R.id.chatname);
        }
    }
}
