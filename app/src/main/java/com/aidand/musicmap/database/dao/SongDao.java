package com.aidand.musicmap.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.lang.String;

import com.aidand.musicmap.database.models.Album;
import com.aidand.musicmap.database.models.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song")
    List<Song> getAll();

    @Query("SELECT * FROM song WHERE id=:id")
    Song get(int id);

    @Query("SELECT * FROM song WHERE id=:id")
    Song get(long id);

    @Query("SELECT * FROM song WHERE name=:name and albumId=:albumId")
    Song get(String name, int albumId);

    @Query("SELECT * FROM song WHERE id IN (:ids)")
    List<Song> querySongs(int[] ids);

    @Query("SELECT * FROM song WHERE id IN (:Song)")
    List<Song> loadAllByIds(int[] Song);

    @Query("SELECT EXISTS(SELECT * FROM song WHERE id = :uid)")
    boolean isRowIsExist(int uid);
    @Query("SELECT EXISTS(SELECT * FROM song where name=:name and albumId=:albumId)")
    boolean isRowExist(String name, int albumId);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Song song);

    @Delete
    void delete(Song song);

    @Update
    void update(Song song);
}
