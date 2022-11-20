package com.aidand.musicmap.database.models;

import com.aidand.musicmap.ui.listens.ListensAdapter;
import com.aidand.musicmap.ui.listens.ListensFragment;


public class AlbumImpl extends  Album{
    public AlbumImpl(int id, String name, String artist, byte[] albumArt, ListensFragment.ListenAdapterContainer listenAdapterContainer){
        this.id = id;
        this._name = name;
        this._artist = artist;
        // TODO add code to handle null - provide some central place of memory to read from to
        //  prevent unnecessary memory duplication
        this.listenAdapterContainer  = listenAdapterContainer;
        if(albumArt == null) this._albumArt = getDefaultAlbumArt();
        else this._albumArt = albumArt;
    }
    public AlbumImpl(String name, String artist, byte[] albumArt, ListensFragment.ListenAdapterContainer listenAdapterContainer){
        this._name = name;
        this._artist = artist;
        // TODO add code to handle null - provide some central place of memory to read from to
        //  prevent unnecessary memory duplication
        this.listenAdapterContainer  = listenAdapterContainer;
        if(albumArt == null) this._albumArt = getDefaultAlbumArt();
        else this._albumArt = albumArt;
    }
}
