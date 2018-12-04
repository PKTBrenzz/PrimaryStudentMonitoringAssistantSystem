package com.example.poong.primarystudentmonitoringassistantsystem.Chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.poong.primarystudentmonitoringassistantsystem.NotificationPackage.NotificationMessage;
import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.SharedPrefManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatMessageActivity extends AppCompatActivity {

    private List<ChatMessage> messageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatRoomAdapter chatRoomAdapter;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private String currentUser = SharedPrefManager.getInstance(this).getUserID();
    private String receiver;
    private DatabaseReference ref1;
    private DatabaseReference ref2;

    private Button sendButton;
    private EditText textbox;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Intent intent = getIntent();
        receiver = intent.getStringExtra("NAME");
        final Context context = this;
        ref1 = mDatabase.getReference().child("chat_room").child(currentUser + "_" + receiver);
        ref2 = mDatabase.getReference().child("chat_room").child(receiver + "_" + currentUser);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle(intent.getStringExtra("ROOM"));
        setSupportActionBar(toolbar);

        textbox = findViewById(R.id.edittext_chatbox);

        sendButton = findViewById(R.id.button_chatbox_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textbox.getText().toString().equals("")){
                    ref1.push().setValue(new ChatMessage(textbox.getText().toString(), currentUser, new Date().getTime()));
                    ref2.push().setValue(new ChatMessage(textbox.getText().toString(), currentUser, new Date().getTime()));
                    NotificationMessage sendMessage = new NotificationMessage(SharedPrefManager.getInstance(view.getContext()).getUsername() + " has sent message", textbox.getText().toString(), new Date().getTime());
                    Map<String, String> data = new HashMap<>();
                    data.put("tag", "chat");
                    data.put("receiver", currentUser);
                    data.put("name", SharedPrefManager.getInstance(view.getContext()).getUsername());
                    sendMessage.data = data;
                    mDatabase.getReference().child("notification").child(receiver).push().setValue(sendMessage);
                    textbox.setText("");
                }
            }
        });

        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                messageList.add(message);
                chatRoomAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatRoomAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.reyclerview_message_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatRoomAdapter = new ChatRoomAdapter(messageList);
        recyclerView.setAdapter(chatRoomAdapter);
    }
}
