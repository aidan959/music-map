package com.aidand.musicmap.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.aidand.musicmap.database.converters.DbConverter;
import com.aidand.musicmap.database.dao.AlbumDao;
import com.aidand.musicmap.database.dao.CameraDao;
import com.aidand.musicmap.database.dao.ListenDao;
import com.aidand.musicmap.database.dao.SongDao;
import com.aidand.musicmap.database.models.Album;
import com.aidand.musicmap.database.models.CameraLocation;
import com.aidand.musicmap.database.models.Listen;
import com.aidand.musicmap.database.models.Song;

@Database(entities = {CameraLocation.class, Album.class, Song.class, Listen.class}, version = 2)
@TypeConverters(DbConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CameraDao cameraDao();
    public abstract AlbumDao albumDao();
    public abstract SongDao songDao();
    public abstract ListenDao listenDao();

}
