package com.aidand.musicmap.database;

import com.aidand.musicmap.database.models.Listen;

import java.util.List;

public class ListenerInitializer {
    public static List<Listen> initLocationDataset(AppDatabase db) {
        return db.listenDao().getAll();
    }

}
