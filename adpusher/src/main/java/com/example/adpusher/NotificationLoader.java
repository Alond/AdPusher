package com.example.adpusher;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NotificationLoader extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private String posterUrl;
    private String actionUrl;
    private String title;
    private String info;

    public NotificationLoader(
            Context context,
            String posterUrl,
            String actionUrl,
            String title,
            String info
    ) {
        super();
        this.context = context;
        this.posterUrl = posterUrl;
        this.actionUrl = actionUrl;
        this.title = title;
        this.info = info;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        InputStream in;
//        message = params[0] + params[1];
        try {

            URL url = new URL(this.posterUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            in = connection.getInputStream();
            return BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);
        try {
            NotificationManagerCompat managerCompat=NotificationManagerCompat.from(context);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ad_notification);

            Bitmap proxy = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(proxy);
            c.drawBitmap(bitmap, new Matrix(), null);
            remoteViews.setImageViewBitmap(R.id.poster, proxy);

            remoteViews.setTextViewText(R.id.title, this.title);
            remoteViews.setTextViewText(R.id.info, this.info);

            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            int iconId = ai.icon;

            Intent notificationIntent = new Intent(Intent.ACTION_VIEW);

            notificationIntent.setData(Uri.parse(this.actionUrl));
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);


            Notification notification=new NotificationCompat.Builder(context, null)
                    .setSmallIcon(iconId)
                    .setCustomBigContentView(remoteViews)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .build();
            managerCompat.notify(1,notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}