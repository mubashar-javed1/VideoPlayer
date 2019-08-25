package com.mobi.videoplayer.repository;

import com.mobi.videoplayer.data.Video;
import com.mobi.videoplayer.networkcall.ApiCall;

import java.util.List;

import io.reactivex.Observable;

public class Repository {
    private ApiCall apiCall;

    public Repository(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    public Observable<List<Video>> getVideos() {
        return apiCall.getVideos();
    }
}