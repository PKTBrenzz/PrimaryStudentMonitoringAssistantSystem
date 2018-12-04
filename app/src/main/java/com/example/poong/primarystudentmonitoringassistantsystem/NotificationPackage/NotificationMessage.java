package com.example.poong.primarystudentmonitoringassistantsystem.NotificationPackage;

import java.util.Date;
import java.util.Map;

public class NotificationMessage {
    public String title;
    public String body;
    public Map<String, String> data;
    public long date = new Date().getTime();

    public NotificationMessage(){}

    public NotificationMessage(String title, String body, long date){
        this.title = title;
        this.body = body;
        this.date = date;
    }
}
