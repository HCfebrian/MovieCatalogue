package com.example.moviecataloguefordicoding.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.moviecataloguefordicoding.MainActivity;
import com.example.moviecataloguefordicoding.R;
import com.example.moviecataloguefordicoding.adapter.Callback.OnGetShowCallback;
import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.example.moviecataloguefordicoding.network.MoviesRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class NotificationReceiver extends BroadcastReceiver {

    private MoviesRepository moviesRepository = MoviesRepository.getInstance();
    public static final String TYPE_REPEATING = "repeating alarm";
    public static final String TYPE_TODAY = "daily alarm";
    public static final String EXTRA_TYPE = "type";


    private final int ID_TODAY = 100;
    private final int ID_REPEATING = 101;

    public NotificationReceiver() {

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        Log.d(TAG, "onReceive: ");

        if(TYPE_REPEATING.equalsIgnoreCase(type)){
            sendDailyRemainder(context);
        }else{
            getTodayMovie(context);
        }
    }


    public void setDailyNotification(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, (type.equalsIgnoreCase(TYPE_REPEATING))?7:8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, type.equalsIgnoreCase(TYPE_REPEATING) ? ID_REPEATING: ID_TODAY, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, R.string.remainder_on, Toast.LENGTH_SHORT).show();
    }

    private void sendDailyRemainder(Context context){
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Daily Reminder channel";

        Log.d(TAG, "sendDailyRemainder: ");
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,intent,0);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_movie_filter_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_filter_black_24dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.content_text))
                .setSubText(context.getResources().getString(R.string.subtext))
                .setAutoCancel(true);

        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void sendReleaseToday(Context context, String title, int id) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "Today release channel";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_filter_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_filter_black_48dp))
                .setContentTitle("Pick The Show")
                .setContentText(title + " Is Released")
                .setSubText("Check The Show In The App Now")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = mBuilder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(id, notification);
        }
    }

    private void getTodayMovie(final Context context){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);

        moviesRepository.getTodayRelease(now, new OnGetShowCallback<ModelFilm>() {
            @Override
            public void onSuccess(List<ModelFilm> shows) {
                int id=2;
                for (ModelFilm show:shows){
                    Log.d(TAG, "onSuccess: "+show.getTitle());
                    sendReleaseToday(context,show.getTitle(),id);
                    id++;
                }
            }

            @Override
            public void onError() {

            }
        });

    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_REPEATING) ? ID_REPEATING: ID_TODAY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.remainder_off, Toast.LENGTH_SHORT).show();
    }




}

