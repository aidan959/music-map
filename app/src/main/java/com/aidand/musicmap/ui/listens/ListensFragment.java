package com.aidand.musicmap.ui.listens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aidand.musicmap.MainActivity;
import com.aidand.musicmap.R;
import com.aidand.musicmap.database.AppDatabase;
import com.aidand.musicmap.database.models.Album;
import com.aidand.musicmap.databinding.FragmentListensBinding;
import com.aidand.musicmap.database.models.Listen;
import com.aidand.musicmap.database.models.Song;
import java.util.List;

public class ListensFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListensViewModel listensViewModel;
    private FragmentListensBinding binding;
    protected RecyclerView mRecyclerView;
    protected ListensAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Listen> mDataset;
    ListenAdapterContainer listenAdapterContainer = new ListenAdapterContainer();
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public void onRefresh() {
        initDataset();
        swipeRefreshLayout.setRefreshing(false);
    }
    public class ListenAdapterContainer{
        public ListensAdapter listensAdapter;
        public AppDatabase database;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private AppDatabase db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listensViewModel =
                new ViewModelProvider(this).get(ListensViewModel.class);
        db = ((MainActivity) getActivity()).globalDb;
        binding = FragmentListensBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.listen_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.listenerRecView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter( mAdapter );

        initDataset();

        listenAdapterContainer.listensAdapter = mAdapter;
        listenAdapterContainer.database = db;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void initDataset() {
        if(mDataset != null)
            if(mDataset.get(0).id == db.listenDao().getRecent().id)
                return;

        List<Listen> listens = db.listenDao().getAllRecent();
        int[] songIds = new int[listens.size()];
        for(int i = 0; i< listens.size(); i++){
            songIds[i] = listens.get(i).songId;
        }
        List<Song> songs = db.songDao().querySongs(songIds);

        int[] albumIds = new int[songs.size()];
        for(int i = 0; i< songs.size(); i++){
            albumIds[i] = songs.get(i).albumId;
        }
        List<Album> albums = db.albumDao().queryAlbums(albumIds);

        for(Album album : albums){
            album.listenAdapterContainer=listenAdapterContainer;

        }
        for(Song song: songs){
            song.setAlbum(albums.get(song.albumId-1));
        }
        for(Listen listen: listens){
            listen.setSong(songs.get(listen.songId-1));
        }
        mDataset = listens;
        mAdapter = new ListensAdapter(mDataset, (MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }
}