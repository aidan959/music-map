package com.aidand.musicmap.receivers;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import com.aidand.musicmap.database.AppDatabase;
import com.aidand.musicmap.database.models.Album;
import com.aidand.musicmap.database.models.Listen;
import com.aidand.musicmap.database.models.Song;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.time.Instant;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class SpotifyReceiver extends BroadcastReceiver {
    private static final String TAG = "SpotifyReceiver";
    Context context;
    private class LastLocationContainer{
        public Location location =null;
    }
    final LastLocationContainer locationContainer = new LastLocationContainer();
    @Override
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        this.context = context;
        dumpIntent(bundle);
        Log.d(TAG, "Action received was: " + action);

        // check to make sure we actually got something
        if (action == null) {
            Log.w(TAG, "Got null action");
            return;
        }

        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        Task<Location> task = null;
        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "got last location");
            task = fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        // Got last known location. In some rare situations this can be null.
                        locationContainer.location = location;
                    });
        }
        Log.d("dk what this is", action);
        parseIntent(bundle, task);
    }
    public static void dumpIntent(Bundle bundle){
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.d(TAG,"Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.d(TAG,"[" + key + "=" + bundle.get(key)+"]");
            }
            Log.d(TAG,"Dumping Intent end");
        }
    }

    protected void parseIntent( Bundle bundle, Task task) {

        if (bundle.containsKey("track")) {
            new Thread(() -> {

                AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "listen-db").allowMainThreadQueries().build();
                String artist_name = bundle.getString("artist");
                String album_name = bundle.getString("album");
                String song_name = bundle.getString("track");
                Instant time = Instant.now();
                Album album;
                Song song;

                long duration = bundle.getLong("duration");
                if (duration != 0) {
                    duration = (int) (long) duration / 1000;
                }
                if (db.albumDao().isRowExist(album_name, artist_name)) {
                    album = db.albumDao().get(album_name, artist_name);
                    Log.d(TAG, "Got album " + album_name + " from database with id: " + album.getID());
                } else {
                    long newId = db.albumDao().insert(new Album(album_name, artist_name, null));
                    album = db.albumDao().get(newId);
                    Log.d(TAG, "Added album " + album_name + " to database with id: " + newId);

                }
                if (db.songDao().isRowExist(song_name, album.getID())) {
                    song = db.songDao().get(song_name, album.getID());
                    Log.d(TAG, "Got song " + song_name + " from database with id: " +song.getId() );

                } else {
                    long newId = db.songDao().insert(new Song(song_name, album.getID()));
                    song = db.songDao().get(newId);
                    Log.d(TAG, "Added song " + song_name + " to database with id: " + newId);

                }
                if (task != null) {
                    try {
                        Tasks.await(task);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                db.listenDao().insert(new Listen(song.getId(), locationContainer.location, time, (byte) 0));
                db.close();
            }).start();
        }
    }

}
