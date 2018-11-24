package com.example.poong.primarystudentmonitoringassistantsystem;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String token){
        Log.d(TAG, "Rehreshed token" + token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

    }
}
