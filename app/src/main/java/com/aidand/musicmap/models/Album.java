/*****************************************************************************************
 *
 *     Class to store a class entity
 *     This class wasn't required for the lab, but it;s a better class
 *     design to have a class for your data entity so that you have getters/ setters to retrieve values etc
 *     Oct 2022
 *
 *******************************************************************************************/
package com.aidand.musicmap.models;

import android.os.Parcel;
import android.os.Parcelable;

import jpegkit.Jpeg;

public class Album implements Parcelable
{
    private int _id;
    private String _name = "Untitled Album";
    private String _artist = "Unknown Artist";
    private Jpeg _albumArt;

    public static final Creator CREATOR = new Creator() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
    public Album(Parcel in){
        this._id = in.readInt();
        this._name = in.readString();
        this._artist = in.readString();
        int artByteLength = in.readInt();
        if(artByteLength >= 0){
            byte[] artBytes = new byte[artByteLength];
            in.readByteArray(artBytes);
            this._albumArt = new Jpeg(artBytes);
        }
        else{
            this._albumArt = getDefaultAlbumArt();
        }
    }
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this._name);
        dest.writeString(this._artist);
        byte[] bytes = _albumArt.getJpegBytes();
        dest.writeInt(bytes.length);
        dest.writeByteArray(bytes);
    }


    public Album(){
        this._albumArt = getDefaultAlbumArt();
    }

    public Album(int id, String name, String artist, Jpeg albumArt){
        this._id = id;
        this._name = name;
        this._artist = artist;
        // TODO add code to handle null - provide some central place of memory to read from to
        //  prevent unnecessary memory duplication
        if(albumArt == null) this._albumArt = getDefaultAlbumArt();
        else this._albumArt = albumArt;
    }
  

    public int getID(){  
        return this._id;  
    }
    public void setID(int id){  
        this._id = id;  
    }

    public String getArtist() { return this._artist; }
    public void setArtist(String artist){ this._artist = artist; }

    public String getName(){ return this._name; }
    public void setName(String name){
        this._name = name;
    }

    public Jpeg getAlbumArt(){ if(this._albumArt == null) this._albumArt = getDefaultAlbumArt(); return this._albumArt; }
    public void setAlbumArt(Jpeg albumArt){
        this._albumArt = albumArt;
    }
    public void setAlbumArt(byte[] albumArt){
        this._albumArt = new Jpeg(albumArt);
    }

    public Jpeg getDefaultAlbumArt(){return null;}

    }