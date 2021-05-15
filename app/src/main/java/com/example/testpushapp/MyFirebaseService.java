package com.example.testpushapp;

import androidx.annotation.NonNull;

import com.example.adpusher.AdNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;

public class MyFirebaseService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);

        AdNotification.showNotification(this, remoteMessage.getData());
    }
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        AdNotification.registerToken(s);
    }
}
