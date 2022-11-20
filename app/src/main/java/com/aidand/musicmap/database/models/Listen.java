/*****************************************************************************************
*
*     Class to store a class entity
*     This class wasn't required for the lab, but it;s a better class
*     design to have a class for your data entity so that you have getters/ setters to retrieve values etc
*     Oct 2022
*
*******************************************************************************************/
package com.aidand.musicmap.database.models;

import static androidx.room.ForeignKey.CASCADE;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.Instant;

@Entity(tableName="listen",
        foreignKeys = @ForeignKey(entity = Song.class,
                parentColumns = "id",
                childColumns = "songId",
                onDelete = CASCADE))
public class Listen implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="songId")
    public int songId;
    @Ignore
    public Song _song;
    @ColumnInfo(name="location")
    public Location _location;
    @ColumnInfo(name="time_recorded")
    public Instant _timeRecorded;
    @ColumnInfo(name="rating")
    byte _rating;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Listen createFromParcel(Parcel in) {
            return new Listen(in);
        }

        public Listen[] newArray(int size) {
            return new Listen[size];
        }
    };
    public Listen(Parcel in){
        this.id = in.readInt();
        this._song = in.readParcelable(Song.class.getClassLoader());
        this._location = in.readParcelable(Location.class.getClassLoader());
        this._timeRecorded = (Instant) in.readSerializable();
        this._rating =  in.readByte();
    }
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(_song,0);
        dest.writeParcelable(_location,0);
        dest.writeSerializable(_timeRecorded);
        dest.writeByte(_rating);
    }
    @Ignore
    public Listen(int _id, Song _song, Location _location, Instant _timeRecorded, byte _rating) {
        this.id = _id;
        this._song = _song;
        this._location = _location;
        this._timeRecorded = _timeRecorded;
        this._rating = _rating;
    }
    @Ignore
    public Listen(int _id, int songId, Location _location, Instant _timeRecorded, byte _rating) {
        this.id = _id;
        this._location = _location;
        this._timeRecorded = _timeRecorded;
        this._rating = _rating;
        this.songId = songId;
    }
    public Listen( int songId, Location _location, Instant _timeRecorded, byte _rating) {
        if (_location != null)
            this._location = _location;
        else {
            this._location = new Location("");
            this._location.setLongitude(0);
            this._location.setLatitude(0);
        }
        this._timeRecorded = _timeRecorded;
        this._rating = _rating;
        this.songId = songId;
    }
    @Ignore
    public Listen(Song _song, Location _location, Instant _timeRecorded, byte _rating) {
        this._song = _song;
        if (_location != null)
            this._location = _location;
        else {
            this._location = new Location("");
            this._location.setLongitude(0);
            this._location.setLatitude(0);
        }
        this._timeRecorded = _timeRecorded;
        this._rating = _rating;
    }
    @Ignore
    public Listen(){   }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public Song getSong() {
        return _song;
    }

    public void setSong(Song _song) {
        this._song = _song;
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location _location) {
        this._location = _location;
    }

    public Instant getTimeRecorded() {
        return _timeRecorded;
    }

    public void setTimeRecorded(Instant  _timeRecorded) { this._timeRecorded = _timeRecorded; }

    public byte getRating() { return _rating; }

    public void setRating(byte _rating) { this._rating = _rating; }
}