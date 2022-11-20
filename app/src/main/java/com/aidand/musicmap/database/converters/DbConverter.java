package com.aidand.musicmap.database.converters;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.room.TypeConverter;

import com.aidand.musicmap.database.models.Song;

import java.time.Instant;

public class DbConverter {
    @TypeConverter
    public static int getAlbumKey(Song value) {
        return value != null ? value.getAlbum().getID() : null;
    }
    @SuppressLint("Range")
    @TypeConverter
    public static Location toLocation(String locationString){
        if(locationString == null){
            return null;
        } else {
            String[] strings = locationString.split(" ");
            Location location = new Location("new location");
            location.setLatitude(Location.convert(strings[0]));
            location.setLongitude(Location.convert(strings[1]));
            return location;
        }
    }
    @TypeConverter
    public static String toLocationString(final Location location) {
        return Location.convert(location.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
    }
    @TypeConverter
    public static Instant toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return Instant.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(Instant date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
