package com.aidand.musicmap.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable
{

    private int _id;
    private String _name = "Unknown Name";
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
        this._id = in.readInt();
        this._name = in.readString();
        this._album = in.readParcelable(Album.class.getClassLoader());
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this._name);
        dest.writeParcelable(this._album, 0);

    }
    @Override
    public int describeContents() {
        return 0;
    }

    public Song(){   }

    public Song(int id, String name, Album album){
        this._id = id;
        this._name = name;
        this._album= album;
    }

}