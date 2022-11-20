package com.aidand.musicmap.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aidand.musicmap.database.models.Album;


import java.util.List;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM album")
    List<Album> getAll();

    @Query("SELECT * FROM album WHERE id=(:id)")
    Album get(int id);

    @Query("SELECT * FROM album WHERE id=(:id)")
    Album get(long id);

    @Query("SELECT * FROM album where name=:name AND artist=:artist LIMIT 1")
    Album get(String name, String artist);

    @Query("SELECT * FROM album WHERE id IN (:ids)")
    List<Album> queryAlbums(int[] ids);

    @Query("SELECT * FROM album WHERE id IN (:album)")
    List<Album> loadAllByIds(int[] album);

    @Query("SELECT EXISTS(SELECT * FROM album WHERE id = :uid)")
    boolean isRowIsExist(int uid);

    @Query("SELECT EXISTS(SELECT * FROM album where name=:name AND artist=:artist)")
    boolean isRowExist(String name, String artist);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Album album);

    @Delete
    void delete(Album album);

    @Query("DELETE FROM album WHERE id=(:id)")
    void delete(int id);

    @Update
    void update(Album album);

}
