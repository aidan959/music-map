package com.aidand.musicmap.database.models;

import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="song",
        foreignKeys = @ForeignKey(entity = Album.class,
        parentColumns = "id",
        childColumns = "albumId",
        onDelete = CASCADE))
public class Song implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String _name = "Unknown Name";
    @ColumnInfo(name="albumId")
    public int albumId;
    @Ignore
    private Album _album;

    public static final Creator CREATOR = new Creator() {
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    public Song(Parcel in){
        this.id = in.readInt();
        this._name = in.readString();
        this._album = in.readParcelable(Album.class.getClassLoader());
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this._name);
        dest.writeParcelable(this._album, 0);

    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Ignore
    public Song(){   }
    @Ignore
    public Song(int id, String name, Album album){
        this.id = id;
        this._name = name;
        this._album= album;
    }
    @Ignore
    public Song(int id, String name, int albumId){
        this.id = id;
        this._name = name;
        this.albumId= albumId;
    }
    public Song(String name, int albumId){
        this._name = name;
        this.albumId= albumId;
    }
    @Ignore
    public Song(String name, Album album){
        this._name = name;
        this._album= album;
    }
    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public Album getAlbum() {
        return _album;
    }

    public void setAlbum(Album _album) {
        this._album = _album;
    }

}