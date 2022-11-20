package com.aidand.musicmap.ui.listens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aidand.musicmap.MainActivity;
import com.aidand.musicmap.R;
import com.aidand.musicmap.database.models.Listen;
import com.aidand.musicmap.ui.map.MapFragment;
import org.ocpsoft.prettytime.PrettyTime;
import java.util.List;

public class ListensAdapter extends RecyclerView.Adapter<ListensViewHolder> {

    private List<Listen> listenDataSet;
    PrettyTime prettyTime = new PrettyTime();
    private MainActivity mainActivity;
    public ListensAdapter(List<Listen> dataSet, MainActivity main) {

        listenDataSet = dataSet;
        mainActivity = main;
    }

    @Override
    public ListensViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listen_view, viewGroup, false);
        return new ListensViewHolder(view, mainActivity);
    }
    @Override
    public void onBindViewHolder(@NonNull ListensViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        Listen listen = listenDataSet.get(viewHolder.getBindingAdapterPosition());
        viewHolder.getSongNameView()
                .setText(
                        listen.getSong().getName());
        viewHolder.getSongArtistView()
                .setText(
                        listen.getSong().getAlbum().getArtist());
        viewHolder.getSongAlbumView()
                .setText(
                        listen.getSong().getAlbum().getName());
        viewHolder.getSongTimeStampView()
                .setText(
                        prettyTime.format(listen.getTimeRecorded()));
        viewHolder.getSongLocationStampView()
                .setText("Location: "+listen.getLocation().getLatitude() + ", " + listen.getLocation().getLongitude());


        viewHolder.getSongAlbumArtView()
                .setImageBitmap(
                        listen.getSong().getAlbum().getAlbumArtBM());
        // allows song name text to marquee scroll if too long
        viewHolder.getSongNameView().setSelected(true);
        viewHolder.getSongAlbumArtView().setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MapFragment mapFrag = new MapFragment();
                Bundle listenBundle = new Bundle();
                listenBundle.putInt("listen_id", position);
                mapFrag.setArguments(listenBundle);
                mainActivity.navController.navigate(R.id.navigation_map, listenBundle);
            }

        });

    }

    @Override
    public int getItemCount() {
        return listenDataSet.size();
    }
}
