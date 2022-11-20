package com.aidand.musicmap.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aidand.musicmap.database.models.CameraLocation;

import java.util.List;

@Dao
public interface CameraDao {
    @Query("SELECT * FROM camera_location")
    List<CameraLocation> getAll();

    @Query("SELECT * FROM camera_location WHERE uid=0")
    CameraLocation get();

    @Query("SELECT * FROM camera_location WHERE uid IN (:cameraLocation)")
    List<CameraLocation> loadAllByIds(int[] cameraLocation);

    @Query("SELECT EXISTS(SELECT * FROM camera_location WHERE uid = :uid)")
    boolean isRowIsExist(int uid);

    @Insert
    void insertAll(CameraLocation... cameraLocations);

    @Delete
    void delete(CameraLocation cameraLocation);

    @Update
    void update(CameraLocation cameraLocation);
}
