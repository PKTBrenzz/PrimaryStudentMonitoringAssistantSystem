package com.example.poong.primarystudentmonitoringassistantsystem;

import java.util.List;

public class ChatRoom {
    private String roomId;
    public List<User> senders;
    public List<ChatMessage> messages;

    public ChatRoom(){

    }

    public ChatRoom(String roomId){
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
