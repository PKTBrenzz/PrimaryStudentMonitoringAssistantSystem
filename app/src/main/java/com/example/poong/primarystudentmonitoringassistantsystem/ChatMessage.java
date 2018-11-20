package com.example.poong.primarystudentmonitoringassistantsystem;

import java.util.Date;

public class ChatMessage {
    public String content;
    public String sender;
    public String date;

    public ChatMessage(){}

    public ChatMessage(String content, String sender, String date){
        this.content = content;
        this.sender = sender;
        this.date = date;
    }
}
