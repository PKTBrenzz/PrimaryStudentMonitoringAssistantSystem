package com.example.poong.primarystudentmonitoringassistantsystem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageList extends AppCompatActivity {

    private List<ChatMessage> messageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatRoomAdapter chatRoomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        messageList.add(new ChatMessage("Hello, how are you?!", "2", "NOV"));
        messageList.add(new ChatMessage("I'm fine.", "1", "NOV"));

        recyclerView = findViewById(R.id.reyclerview_message_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatRoomAdapter = new ChatRoomAdapter(messageList);
        recyclerView.setAdapter(chatRoomAdapter);
    }
}
