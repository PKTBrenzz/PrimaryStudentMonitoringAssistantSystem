package com.example.poong.primarystudentmonitoringassistantsystem;

import java.util.Date;

public class ChatMessage {
    public String content;
    public String sender;
    public long date;

    public ChatMessage(){}

    public ChatMessage(String content, String sender, long date){
        this.content = content;
        this.sender = sender;
        this.date = date;
    }
}
