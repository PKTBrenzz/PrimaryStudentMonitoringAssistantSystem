package com.example.poong.primarystudentmonitoringassistantsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context ctx;
    private List<ChatMessage> messageList;

    public ChatRoomAdapter(List<ChatMessage> mL){
        messageList = mL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;


        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sent_message, parent, false);
            return new SentMessageViewHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_received_message, parent, false);
            return new ReceivedMessageViewHolder(view);
        }

        ctx = view.getContext();
        return null;
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ChatMessage message = (ChatMessage) messageList.get(position);

        if (message.sender.equals(SharedPrefManager.getInstance(ctx).getUserID())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = (ChatMessage) messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageViewHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private class SentMessageViewHolder extends RecyclerView.ViewHolder{

        TextView messageText, timeText;

        public SentMessageViewHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        @SuppressLint("SimpleDateFormat")
        public void bind(ChatMessage message) {
            messageText.setText(message.content);
            timeText.setText(new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(message.date));
        }
    }

    private class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
        TextView messageText, timeText;
        public ReceivedMessageViewHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        @SuppressLint("SimpleDateFormat")
        public void bind(ChatMessage message) {
            messageText.setText(message.content);
            timeText.setText(new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(message.date));
        }
    }
}
