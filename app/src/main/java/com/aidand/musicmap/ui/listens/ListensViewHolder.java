package com.aidand.musicmap.ui.listens;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.aidand.musicmap.MainActivity;
import com.aidand.musicmap.R;
import com.aidand.musicmap.ui.map.MapFragment;

public class ListensViewHolder extends RecyclerView.ViewHolder {
    private final TextView songNameView, songArtistView, songAlbumView, locationStampView, timeStampView;

    private final ImageView songAlbumArtView;

    public ListensViewHolder(View view, MainActivity main) {
        super(view);
        songNameView = (TextView) view.findViewById(R.id.songName);
        songArtistView = (TextView) view.findViewById(R.id.artistName);
        songAlbumView = (TextView) view.findViewById(R.id.albumName);
        songAlbumArtView = (ImageView) view.findViewById(R.id.albumArt);
        locationStampView = (TextView) view.findViewById(R.id.locationStamp);
        timeStampView = (TextView) view.findViewById(R.id.timeStamp);


    }
    public TextView getSongNameView() {
        return songNameView;
    }
    public TextView getSongArtistView() {
        return songArtistView;
    }
    public TextView getSongAlbumView() {
        return songAlbumView;
    }
    public TextView getSongLocationStampView() {
        return locationStampView;
    }
    public TextView getSongTimeStampView() {
        return timeStampView;
    }
    public ImageView getSongAlbumArtView() {
        return songAlbumArtView;
    }

}
