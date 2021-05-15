package com.example.adpusher;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class AdNotification {
    static public void showNotification(Context context, Map<String, String> data) {
        if (data.containsKey("type")) {
            String type = data.get("type");
            if ("adpusher".equalsIgnoreCase(type)) {
                String posterUrl = data.get("poster_url");
                String actionUrl = data.get("action_url");
                String title = data.get("title");
                String info = data.get("info");
                NotificationLoader loader = new NotificationLoader(
                        context,
                        posterUrl,
                        actionUrl,
                        title,
                        info
                );
                loader.execute();
            }
        }
    }

    static public void registerToken(String token) {
        HashMap<String, String> data = new HashMap<>();
        data.put("token", token);

        TokenPoster tokenPoster = new TokenPoster(data);
        tokenPoster.execute();
    }
}
