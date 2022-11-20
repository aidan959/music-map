package com.aidand.musicmap.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.aidand.musicmap.database.models.Listen;
import com.aidand.musicmap.database.models.Song;

import java.util.List;

@Dao
public interface ListenDao {
    @Query("SELECT * FROM listen")
    List<Listen> getAll();

    @Query("SELECT * FROM listen WHERE id=:id")
    Listen get(int id);
    @Query("SELECT * FROM listen ORDER BY id DESC LIMIT 1")
    Listen getRecent();

    @Query("SELECT * FROM listen WHERE id IN (:Listen)")
    List<Listen> loadAllByIds(int[] Listen);
    @Query("SELECT * FROM listen ORDER BY id DESC LIMIT 1000")
    List<Listen> getAllRecent();
    @Query("SELECT EXISTS(SELECT * FROM listen WHERE id = :uid)")
    boolean isRowIsExist(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Listen listen);

    @Delete
    void delete(Listen listen);

    @Update
    void update(Listen listen);


}
