package com.aidand.musicmap.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

@Entity(tableName = "camera_location")
public class CameraLocation {
    public CameraLocation(){

    }
    public CameraLocation(int uid,CameraPosition cameraPosition){
        this.uid = uid;
        populateFromCameraPosition(cameraPosition);

    }
    public void populateFromCameraPosition(CameraPosition cameraPosition){
        latitude = cameraPosition.target.latitude;
        longitude = cameraPosition.target.longitude;
        zoom = cameraPosition.zoom;
        bearing = cameraPosition.bearing;
        tilt = cameraPosition.tilt;
    }

    public CameraPosition createCamera(){
        return new CameraPosition.Builder()
                   .target(new LatLng(latitude, longitude))
                   .zoom(zoom)
                   .bearing(bearing)
                   .tilt(tilt)
                   .build();

    }
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="latitude")
    public double latitude;

    @ColumnInfo(name="longitude")
    public double longitude;

    @ColumnInfo(name="zoom")
    public float zoom;

    @ColumnInfo(name="bearing")
    public float bearing;

    @ColumnInfo(name="tilt")
    public float tilt;
}
