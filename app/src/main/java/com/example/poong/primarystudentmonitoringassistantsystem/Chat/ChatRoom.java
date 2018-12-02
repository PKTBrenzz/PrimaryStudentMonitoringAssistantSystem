package com.example.poong.primarystudentmonitoringassistantsystem.Chat;

import java.util.List;

public class ChatRoom {
    private String roomId;
    private String name;
    public List<ChatMessage> messages;

    public ChatRoom(){

    }

    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
