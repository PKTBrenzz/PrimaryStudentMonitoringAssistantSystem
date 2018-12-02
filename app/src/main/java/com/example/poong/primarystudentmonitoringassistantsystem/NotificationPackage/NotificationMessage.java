package com.example.poong.primarystudentmonitoringassistantsystem.NotificationPackage;

import java.util.Date;

public class NotificationMessage {
    public String title;
    public String body;
    public long date = new Date().getTime();

    public NotificationMessage(){}

    public NotificationMessage(String title, String body, long date){
        this.title = title;
        this.body = body;
        this.date = date;
    }
}
