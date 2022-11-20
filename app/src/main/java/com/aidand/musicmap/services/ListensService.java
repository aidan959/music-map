


package com.aidand.musicmap.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import com.aidand.musicmap.receivers.SpotifyReceiver;

public class ListensService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();

        filter.addAction("com.android.music.metachanged");
        filter.addAction("com.htc.music.metachanged");
        filter.addAction("fm.last.android.metachanged");
        filter.addAction("com.sec.android.app.music.metachanged");
        filter.addAction("com.nullsoft.winamp.metachanged");
        filter.addAction("com.amazon.mp3.metachanged");
        filter.addAction("com.miui.player.metachanged");
        filter.addAction("com.real.IMP.metachanged");
        filter.addAction("com.sonyericsson.music.metachanged");
        filter.addAction("com.rdio.android.metachanged");
        filter.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        filter.addAction("com.andrew.apollo.metachanged");
        registerReceiver(mReceiver, filter);
    }
    /*private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            Log.v("tag ", action + " / " + cmd);
            String artist = intent.getStringExtra("artist");
            String album = intent.getStringExtra("album");
            String track = intent.getStringExtra("track");
            Log.v("tag", artist + ":" + album + ":" + track);
            Toast.makeText(ListensService.this, track, Toast.LENGTH_SHORT).show();
        }
    };*/
    private BroadcastReceiver mReceiver = new SpotifyReceiver();
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG,"**********  onNotificationPosted");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new  Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationPosted :" + sbn.getPackageName() + "\n");
        sendBroadcast(i);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG,"********** onNotificationRemoved");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
        Intent i = new  Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "\n");

        sendBroadcast(i);
    }

    class NLServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("clearall")){
                ListensService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                int i=1;
                for (StatusBarNotification sbn : ListensService.this.getActiveNotifications()) {
                    Log.i(TAG,sbn.getNotification().category);

                }
            }

        }
    }

}