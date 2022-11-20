/*****************************************************************************************
 *
 *     Class to store a class entity
 *     This class wasn't required for the lab, but it;s a better class
 *     design to have a class for your data entity so that you have getters/ setters to retrieve values etc
 *     Oct 2022
 *
 *******************************************************************************************/
package com.aidand.musicmap.database.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.aidand.musicmap.ui.listens.ListensAdapter;
import com.aidand.musicmap.ui.listens.ListensFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

@Entity(tableName = "album")
public class Album implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="name")
    public String _name = "Untitled Album";
    @ColumnInfo(name="artist")
    public String _artist = "Unknown Artist";
    @ColumnInfo(name="album_art", typeAffinity = ColumnInfo.BLOB)
    public byte[] _albumArt;

    @Ignore
    public ListensFragment.ListenAdapterContainer listenAdapterContainer;
    @Ignore
    private byte[] __tmpAlbmArt = null;
    @Ignore
    private byte[] bytes = null;
    public static final Creator CREATOR = new Creator() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
    public Album(Parcel in){
        this.id = in.readInt();
        this._name = in.readString();
        this._artist = in.readString();
        int artByteLength = in.readInt();
        if(artByteLength >= 0){
            byte[] artBytes = new byte[artByteLength];
            in.readByteArray(artBytes);
            this._albumArt = artBytes;
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
        dest.writeInt(this.id);
        dest.writeString(this._name);
        dest.writeString(this._artist);
        byte[] bytes = _albumArt;
        dest.writeInt(bytes.length);
        dest.writeByteArray(bytes);
    }

    @Ignore
    public Album(){
        this._albumArt = getDefaultAlbumArt();
    }
    @Ignore
    public Album(int id, String name, String artist, byte[] albumArt){
        this.id = id;
        this._name = name;
        this._artist = artist;
        // TODO add code to handle null - provide some central place of memory to read from to
        //  prevent unnecessary memory duplication
        if(albumArt == null) this._albumArt = getDefaultAlbumArt();
        else this._albumArt = albumArt;
    }

    public Album(String name, String artist, byte[] albumArt){
        this._name = name;
        this._artist = artist;
        // TODO add code to handle null - provide some central place of memory to read from to
        //  prevent unnecessary memory duplication
        if(albumArt == null) this._albumArt = getDefaultAlbumArt();
        else this._albumArt = albumArt;
    }


    public int getID(){  
        return this.id;
    }
    public void setID(int id){  
        this.id = id;
    }

    public String getArtist() { return this._artist; }
    public void setArtist(String artist){ this._artist = artist; }

    public String getName(){ return this._name; }
    public void setName(String name){
        this._name = name;
    }
    public Bitmap getAlbumArtBM(){
        if(_albumArt != null)
            return BitmapFactory.decodeByteArray(_albumArt,0, (int)_albumArt.length);
        else return  null;
    }

    public byte[] getAlbumArt(){ if(this._albumArt == null) this._albumArt = getDefaultAlbumArt(); return this._albumArt; }
    public void setAlbumArt(byte[] albumArt){
        this._albumArt = albumArt;
    }
    public byte[] requestAlbum(){
        final String key = "XIMvuTwQzWwQhayOvEva";
        final String secret = "aVLgrpwhbcXWNdIZUVzdpNzigaoLKrDP";
        final URL baseUrl;
        try {
            baseUrl = new URL("https://api.discogs.com/database/search?q="+_name+" "+_artist+"&key=" + key + "&secret="+secret);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            // Create connection
            HttpsURLConnection myConnection;
            try {
                myConnection = (HttpsURLConnection) baseUrl.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            myConnection.setRequestProperty("User-Agent", "music-map-app-v0.1");

            try {
                if (myConnection.getResponseCode() == 200) {
                    // Success
                    // Further processing here
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    boolean finished = false;
                    jsonReader.beginObject(); // Start processing the JSON object
                    while (jsonReader.hasNext()) { // Loop through all keys
                        String jsonKey = jsonReader.nextName(); // Fetch the next key
                        if (jsonKey.equals("results")) {
                            JsonToken peek = jsonReader.peek();
                            if(peek == JsonToken.NULL) return;
                            jsonReader.beginArray();
                            while(jsonReader.hasNext()){
                                jsonReader.beginObject();
                                while(jsonReader.hasNext()) {
                                    String name = jsonReader.nextName();
                                    if (name.equals("cover_image")) {
                                        URL albumURL = new URL(jsonReader.nextString());

                                        try {
                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                            try (InputStream is = albumURL.openStream()) {
                                                byte[] byteChunk = new byte[4096];
                                                int n;

                                                while ((n = is.read(byteChunk)) > 0) {
                                                    baos.write(byteChunk, 0, n);
                                                }
                                                bytes = baos.toByteArray();
                                                finished = true;
                                                break;
                                            } catch (IOException e) {
                                                System.err.printf("Failed while reading bytes from %s: %s", albumURL.toExternalForm(), e.getMessage());
                                                e.printStackTrace();

                                            }
                                        } catch (Exception e) {
                                            Log.e("Error", e.getMessage());
                                            e.printStackTrace();
                                        }
                                        if (bytes != null) {
                                            __tmpAlbmArt = bytes;
                                        }
                                        if(finished) break;
                                    } else {
                                        jsonReader.skipValue();
                                    }
                                }
                                if(finished) break;
                                jsonReader.endObject();
                            }
                            if(finished) break;
                            jsonReader.endArray();

                            break; // Break out of the loop
                        } else {
                            jsonReader.skipValue(); // Skip values of other keys
                        }
                        if(finished) break;
                    }

                    jsonReader.close();

                } else {
                    return;
                    // Error handling code goes here
                }

                handler.post(() -> {
                    updateAlbum(bytes);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return(__tmpAlbmArt);
    }
    private void updateAlbum(byte[] jpeg){
        if(jpeg == null){
            // TODO set album art to default image if nothing was found or there was a bug
        } else{
            _albumArt = jpeg;
            if(listenAdapterContainer != null){
                if(listenAdapterContainer.database!= null && listenAdapterContainer.listensAdapter!= null) {
                    listenAdapterContainer.database.albumDao().update(this);
                    listenAdapterContainer.listensAdapter.notifyItemChanged(id);
                }
            }

        }
    }
    public byte[] getDefaultAlbumArt(){
        return requestAlbum();
    }

}