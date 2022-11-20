package com.aidand.musicmap.ui.listens;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListensViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListensViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}